package hello.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.Member;

@Transactional
public interface MemberRepository extends JpaRepository<Member, String> {
	
	List<Member> findAll();
	@Query("SELECT m.username from Member m")
	List<String> findAllName();
	Optional<Member> findByPassword(String password); 
}
