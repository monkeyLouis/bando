package hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.Member;

@Transactional
public interface MemberRepository extends JpaRepository<Member, String> {
	
	List<Member> findAll();
	
	@Query("SELECT m.memName from Member m")
	List<String> findAllName();
}
