package hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.domain.Member;
import hello.domain.RoleOfMember;
import hello.domain.compositePK.RoleOfMemberPK;

@Repository
public interface RoleOfMemberRepository extends JpaRepository<RoleOfMember, RoleOfMemberPK>{

	List<RoleOfMember> findByRoleOfMemberPkFkMember(Member mem);
	
}
