package hello.service;

import java.util.List;
import java.util.Optional;

import hello.domain.Member;

public interface MemberService {
	List<Member> findAll();
	Member update(Member mem);
	Optional<Member> findOne(String id);
	
}
