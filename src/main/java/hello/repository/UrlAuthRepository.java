package hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.domain.UrlAuth;
import hello.domain.compositePK.UrlAuthPK;

@Repository
public interface UrlAuthRepository extends JpaRepository<UrlAuth, UrlAuthPK> {
	List<UrlAuth> findByurlAuthPkFkRoleName(String role);
}
