package hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
