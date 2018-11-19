package hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.OrderDetail;
import hello.domain.OrderDetailId;


@Transactional
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
	
	List<OrderDetail> findAll();
	
}
