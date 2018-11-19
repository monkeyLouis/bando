package hello.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hello.domain.Food;


@Transactional
public interface FoodRepository extends JpaRepository<Food, String> {

	List<Food> findAll();
	
	@Query("SELECT f.f_name from Food f")
	List<String> findAllName();
	
}
