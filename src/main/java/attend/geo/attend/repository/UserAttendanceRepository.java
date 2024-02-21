package attend.geo.attend.repository;

import attend.geo.attend.dto.GetAllUsersPageRequest;
import attend.geo.attend.entity.UserAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserAttendanceRepository extends JpaRepository<UserAttendance, Long>,JpaSpecificationExecutor<UserAttendance>, PagingAndSortingRepository<UserAttendance,Long> {

    List<UserAttendance> findByUserNameOrderByDateDesc(String userName);
    List<UserAttendance> findByUserNameAndDateBetweenOrderByDateAsc(String userName, Date startDate, Date endDate);

    Page<UserAttendance> findAllByDateBetweenOrderByDateAsc(Date start, Date endDate, Pageable pageable);

    Page<UserAttendance> findAll(Specification<UserAttendance> spec, Pageable pageable);
    @Query("SELECT ua FROM UserAttendance ua WHERE ua.startDate <= :date OR ua.endDate >= :date")
    List<UserAttendance> findByDate(@Param("date") Date date);

//    List<UserAttendance> findByNameAndLastName(String name,String lastName);
//public class specification{
//    public static Specification<UserAttendance> findByUserName(String name){
//        return (root, query, criteriaBuilder) -> name !=null && !name.isEmpty()?
//                criteriaBuilder.like(criteriaBuilder.
//                        lower(root.get("userattendance").get("name")),"%"+name.toLowerCase()+"%"):
//                query.getGroupRestriction();
//    }
//    public static Specification<UserAttendance> findByStartDateAndEndDate(Date start, Date end){
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.between(root.get("date"),start,end);
//    }
//    public static Specification<UserAttendance> findByStartDate(Date start){
//        return (root, query, criteriaBuilder) ->start!=null?
//                criteriaBuilder.equal(root.get("date"),start):query.getGroupRestriction();
//    }
//    public static Specification<UserAttendance> findByEndDate(Date end){
//        return (root, query, criteriaBuilder) ->end!=null?
//                criteriaBuilder.equal(root.get("date"),end):query.getGroupRestriction();
//    }
//}
}
