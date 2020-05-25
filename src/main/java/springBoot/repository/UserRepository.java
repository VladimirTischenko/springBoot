package springBoot.repository;

import org.springframework.data.repository.CrudRepository;
import springBoot.domain.User;

/**
 * Created by Vladimir on 21.04.2020.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
    User findById(int id);
}
