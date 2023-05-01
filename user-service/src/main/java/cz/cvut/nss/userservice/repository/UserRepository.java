package cz.cvut.nss.userservice.repository;

import cz.cvut.nss.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>  {
}
