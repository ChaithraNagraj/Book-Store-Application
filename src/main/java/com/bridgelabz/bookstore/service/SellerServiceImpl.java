package com.bridgelabz.bookstore.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.BookAlreadyExistsException;
import com.bridgelabz.bookstore.exception.BookNotFoundException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.model.dto.BookDto;
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
	private RestHighLevelClient client;
	
	@Autowired
	private ObjectMapper objectMapper;
    
	@Autowired
	private AmazonS3 s3client;

	@Value("${amazonProperties.bucketName}")
	private String bucketName;

	private User authentication(String token) {

		Long userId = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		Long roleId = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("roleId"));
		return Optional.ofNullable(userRepository.findByUserIdAndRoleId(userId, roleId))
				.orElseThrow(() -> new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE));

	}

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
		book.setNoOfRejections(0);
		Map<String, Object> documentMapper = objectMapper.convertValue(book, Map.class);
		
			IndexRequest indexRequest = new IndexRequest(Constant.INDEX, Constant.TYPE, String.valueOf(book.getBookId()))
					.source(documentMapper);
			try {
				client.index(indexRequest, RequestOptions.DEFAULT);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		seller.getSellerBooks().add(book);
		userRepository.addUser(seller);
		return book;
	}

	@Override
	public Book updateBook(BookDto updatedBookInfo, long bookId, String token) {
		User seller = authentication(token);
		Book bookToBeUpdated = seller.getSellerBooks().stream().filter(book -> book.getBookId().equals(bookId))
				.findAny().orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		BeanUtils.copyProperties(updatedBookInfo, bookToBeUpdated);
		bookToBeUpdated.setApproved(false);
		bookToBeUpdated.setLastUpdatedDateAndTime(DateUtility.today());
		userRepository.addUser(seller);
		return bookToBeUpdated;
	}

	@Override
	public List<Book> getAllBooks(String token) {
		User seller = authentication(token);
		return seller.getSellerBooks();
	}

	@Override
	public boolean removeBook(long bookId, String token) {
		User seller = authentication(token);
		Book bookTobeDeleted = seller.getSellerBooks().stream().filter(book -> book.getBookId().equals(bookId))
				.findAny().orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		seller.getSellerBooks().remove(bookTobeDeleted);
		userRepository.addUser(seller);
		return true;
	}

	@Override
	public Book addQuantity(long bookId, String token, int quantity) {
		User seller = authentication(token);
		Book bookToAddQuantity = seller.getSellerBooks().stream().filter(book -> book.getBookId().equals(bookId))
				.findAny().orElseThrow(() -> new BookNotFoundException(Constant.BOOK_NOT_FOUND));
		quantity += bookToAddQuantity.getQuantity();
		bookToAddQuantity.setQuantity(quantity);
		bookToAddQuantity.setLastUpdatedDateAndTime(DateUtility.today());
		userRepository.addUser(seller);
		return bookToAddQuantity;
	}


	@Override
	public String uploadImage(MultipartFile image) {
		ObjectMetadata metadata = new ObjectMetadata();
		System.out.println(image.getOriginalFilename());
		metadata.setContentType(image.getContentType());
		metadata.setContentLength(image.getSize());
		try {
			s3client.putObject(
					new PutObjectRequest(bucketName, image.getOriginalFilename(), image.getInputStream(), metadata)
							.withCannedAcl(CannedAccessControlList.PublicRead));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return s3client.getUrl(bucketName, image.getOriginalFilename()).toString();
	}
	public List<Book> searchBook( String token,String input) throws IOException
	{
		User seller = authentication(token);
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();		
        QueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery("*"+input+"*").analyzeWildcard(true).field("authorName", 1.0f).field("bookName",1.0f));
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
                    .forEach(hit -> books
                            .add(objectMapper
                                    .convertValue(hit.getSourceAsMap(),
                                                    Book.class))
                    );
        }
        return books;
	}

}
