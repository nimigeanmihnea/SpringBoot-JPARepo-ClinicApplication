package application.repository;

import application.entity.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Repository
public interface SecretaryRepository extends JpaRepository<Secretary, Long>{

    Secretary findByPnc(String PNC);
}
