package application.repository;

import application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);
}
