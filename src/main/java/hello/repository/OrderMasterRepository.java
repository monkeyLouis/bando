package hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.OrderMaster;

@Transactional
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String>, JpaSpecificationExecutor<OrderMaster> {
	
	List<OrderMaster> findAll();
	
	@Query("SELECT om.member.username from OrderMaster om")
	List<String> findAllOmName();
	
}
