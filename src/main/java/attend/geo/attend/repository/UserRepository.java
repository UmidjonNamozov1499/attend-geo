package attend.geo.attend.repository;

import attend.geo.attend.entity.User;
import attend.geo.attend.entity.UserAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Page<User> findAll(Pageable pageable);

    Optional<User> findByUserName(String userName);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    List<User> findByUserNameAndDateBetweenOrderByDateAsc(String userName, Date startDate, Date endDate);
}
