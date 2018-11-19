package hello.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.domain.Member;
import hello.repository.MemberRepository;
import hello.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	@Override
	public Member update(Member mem) {
		return memberRepository.save(mem);
	}

	@Override
	public Optional<Member> findOne(String id) {
		
		return memberRepository.findById(id);

	}

	/**
	 * It's a new Member class used for JSR-303
	 * (Deprecated)
	 */
//	@Override
//	public Member update(MemberAnnotation mem) {
//		Member member = new Member();
//		BeanCopierUtil.copyProperties(mem, member);
//		
//		return memberRepository.save(member);
//	}
	
}
