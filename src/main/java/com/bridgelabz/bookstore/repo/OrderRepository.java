package com.bridgelabz.bookstore.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.model.MyOrderList;
@Repository
@Transactional
public interface OrderRepository extends JpaRepository<MyOrderList, Long> {
	
	@Query(value = "select * from myorderlist where user_id = :userId", nativeQuery = true)
	List<MyOrderList> getAll(Long userId);

}
