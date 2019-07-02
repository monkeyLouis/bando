package hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.Shop;

@Transactional
public interface ShopRepository extends JpaRepository<Shop, String> {

	public List<Shop> findAll();
	
}
