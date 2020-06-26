package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookAlreadyExistsException;
import com.bridgelabz.bookstore.exception.BookNotFoundException;
import com.bridgelabz.bookstore.exception.BookQuantityException;
import com.bridgelabz.bookstore.exception.UserAuthorizationException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.BookDto;

import com.bridgelabz.bookstore.model.dto.UpdateBookDto;

import com.bridgelabz.bookstore.repo.BookRepo;
import com.bridgelabz.bookstore.repo.RoleRepository;
import com.bridgelabz.bookstore.repo.UserRepo;
import com.bridgelabz.bookstore.utils.DateUtility;
import com.bridgelabz.bookstore.utils.JwtValidate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

	

	@Autowired
	private UserRepo userRepository;

	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BookRepo bookRepository;

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Method to authenticate User
	 * 
	 * @param token
	 * @return User
	 * @throws - UserAuthorizationException => if User is not a Seller -
	 *           UserNotFoundException => if User is not registered and tries to
	 *           login as seller
	 */
	private User authentication(String token) {

		Long userId = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		Integer roleId = (Integer) JwtValidate.decodeJWT(token).get("roleId");
		Role sellerRole = Optional.ofNullable(roleRepository.getRoleById(roleId))
				.filter(role -> role.getRole().equalsIgnoreCase("SELLER"))
				.orElseThrow(() -> new UserAuthorizationException(Constant.UNAUTHORIZED_EXCEPTION_MESSAGE));
		return Optional.ofNullable(userRepository.findByUserIdAndRoleId(userId, sellerRole.getRoleId()))
				.orElseThrow(() -> new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE));

	}


	@SuppressWarnings("unchecked")

	/**
	 * Method to add a new book
	 * 
	 * @param newBook,token
	 * @return Book
	 * @throws - BookAlreadyExistsException => if book already exists with same name
	 *           and price
	 */

	@Override
	public Book addBook(BookDto newBook, String token) {
		User seller = authentication(token);
		seller.getSellerBooks().stream().filter(
				book -> book.getBookName().equals(newBook.getBookName()) && book.getPrice().equals(newBook.getPrice()))
				.findAny().ifPresent(action -> {
					throw new BookAlreadyExistsException("Book Already Exists In Your Inventory");
				});

		Book book = new Book();
		BeanUtils.copyProperties(newBook, book);
		book.setCreatedDateAndTime(DateUtility.today());
		book.setApprovalStatus(Constant.APPROVAL_STATUS_CREATED);
		book.setSeller(seller);
		bookRepository.save(book);
		Map<String, Object> documentMapper = objectMapper.convertValue(book, Map.class);

		IndexRequest indexRequest = new IndexRequest(Constant.INDEX, Constant.TYPE, String.valueOf(book.getBookId()))
				.source(documentMapper);
		try {
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return book;
	}

	/**
	 * Method to update book price and quantity
	 * 
	 * @param updateBookInfo,bookId,token
	 * @return Book
	 * @throws - BookNotFoundException => if bookId doesn't exists
	 */
	@Override
	public Book updateBook(UpdateBookDto updatedBookInfo, long bookId, String token) {
		authentication(token);
		Book bookToBeUpdated = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		int quantity = 0;
		if (updatedBookInfo.getQuantity() > 0)
			quantity = updatedBookInfo.getQuantity() + bookToBeUpdated.getQuantity();
		else {
			if (bookToBeUpdated.getQuantity() < (-(updatedBookInfo.getQuantity()))) {
				throw new BookQuantityException("Book Quantity is Lower than entered quantity to remove");
			}
			quantity = bookToBeUpdated.getQuantity() + updatedBookInfo.getQuantity();
		}
		bookToBeUpdated.setQuantity(quantity);
		bookToBeUpdated.setPrice(updatedBookInfo.getPrice());
		bookToBeUpdated.setApprovalStatus(Constant.APPROVAL_STATUS_WAITING);
		bookToBeUpdated.setLastUpdatedDateAndTime(DateUtility.today());
		bookRepository.save(bookToBeUpdated);

		Map<String, Object> bookMapper = objectMapper.convertValue(bookToBeUpdated, Map.class);
		UpdateRequest updateRequest = new UpdateRequest(Constant.INDEX, Constant.TYPE,
				String.valueOf(bookToBeUpdated.getBookId())).doc(bookMapper);
		try {
			client.update(updateRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bookToBeUpdated;
	}

	/**
	 * Method to get all books related to seller
	 * 
	 * @param token
	 * @return List<Book>
	 */
	@Override
	public List<Book> getAllBooks(String token) {
		User seller = authentication(token);
		return bookRepository.findBySellerId(seller.getId());
	}

	/**
	 * Method to delete book
	 * 
	 * @param bookId,token
	 * @return true
	 * @throws - BookNotFoundException => if bookId doesn't exists
	 */
	@Override
	public boolean removeBook(long bookId, String token) {
		authentication(token);
		Book bookTobeDeleted = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		bookRepository.deleteBook(bookTobeDeleted);
		DeleteRequest deleteRequest = new DeleteRequest(Constant.INDEX, Constant.TYPE, String.valueOf(bookId));
		try {
			client.delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method to add Quantity
	 * 
	 * @param bookId,token,quantity
	 * @return Book
	 * @throws - BookNotFoundException => if bookId doesn't exists
	 */
	@Override
	public Book addQuantity(long bookId, String token, int quantity) {
		authentication(token);
		Book bookToAddQuantity = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		quantity += bookToAddQuantity.getQuantity();
		bookToAddQuantity.setQuantity(quantity);
		bookToAddQuantity.setLastUpdatedDateAndTime(DateUtility.today());
		bookRepository.save(bookToAddQuantity);
		return bookToAddQuantity;
	}

	/**
	 * Method to search Book by either authorName or book name
	 * 
	 * @param token,input
	 * @return List<Book>
	 */
	public List<Book> searchBook(String token, String input) throws IOException {
		authentication(token);
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery("*" + input + "*")
				.analyzeWildcard(true).field("authorName", 1.0f).field("bookName", 1.0f));
		searchSourceBuilder.query(builder);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		return getSearchResult(searchResponse);

	}

	private List<Book> getSearchResult(SearchResponse response) {

		SearchHit[] searchHit = response.getHits().getHits();
		List<Book> books = new ArrayList<>();
		if (searchHit.length > 0) {
			Arrays.stream(searchHit)
					.forEach(hit -> books.add(objectMapper.convertValue(hit.getSourceAsMap(), Book.class)));
		}
		return books;
	}

	@Override
	public boolean sentForApproval(long bookId, String token) {
		authentication(token);
		Book bookSentForApproval = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		if (bookSentForApproval.getApprovalStatus().equalsIgnoreCase(Constant.APPROVAL_STATUS_REJECTED)
				|| bookSentForApproval.getApprovalStatus().equalsIgnoreCase(Constant.APPROVAL_STATUS_CREATED)) {
			bookSentForApproval.setApprovalStatus(Constant.APPROVAL_STATUS_WAITING);
			Map<String, Object> bookMapper = objectMapper.convertValue(bookSentForApproval, Map.class);
			UpdateRequest updateRequest = new UpdateRequest(Constant.INDEX, Constant.TYPE,
					String.valueOf(bookSentForApproval.getBookId())).doc(bookMapper);
			try {
				client.update(updateRequest, RequestOptions.DEFAULT);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

}
