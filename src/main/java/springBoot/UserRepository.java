package springBoot;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Vladimir on 21.04.2020.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
}
