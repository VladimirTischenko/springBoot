package springBoot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springBoot.domain.Message;

/**
 * Created by Vladimir on 18.05.2020.
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    Page<Message> findAll(Pageable pageable);
}
