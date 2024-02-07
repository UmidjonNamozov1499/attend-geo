package attend.geo.attend.repository;

import attend.geo.attend.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Images,Long> {

    Optional<Images> findByToken(UUID token);

}
