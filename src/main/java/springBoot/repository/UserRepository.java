package springBoot.repository;

import org.springframework.data.repository.CrudRepository;
import springBoot.domain.User;

/**
 * Created by Vladimir on 21.04.2020.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
    User findByEmail(String email);
    User findById(int id);
    User findByActivationCode(String activationCode);
}
