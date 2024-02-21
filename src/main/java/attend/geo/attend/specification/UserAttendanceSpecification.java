package attend.geo.attend.specification;

import attend.geo.attend.entity.User;
import attend.geo.attend.entity.UserAttendance;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.Date;

public class UserAttendanceSpecification {
    public static Specification<UserAttendance> dateBetween(Date startDate, Date endDate) {
        return (Root<UserAttendance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.between(root.get("date"), startDate, endDate);
    }

    public static Specification<UserAttendance> findByStartDate(Date start) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("date"), start);
    }

    public static Specification<UserAttendance> findByEndDate(Date end) {
        return (root, query, criteriaBuilder) -> end != null ?
                criteriaBuilder.equal(root.get("date"), end) : query.getGroupRestriction();
    }
    public static Specification<User> betweenDates(Date startDate, Date endDate) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
            Predicate endPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate);
            return criteriaBuilder.and(startPredicate, endPredicate);
        };
    }

//    public static Specification<Long> findByNameAndLastName(Date date, String name, String lastName) {
//        return (Root<Long> root,CriteriaQuery<?> query,CriteriaBuilder criteriaBuilder) ->
//                criteriaBuilder.equal(root.get("workingHours"),date,name,lastName);
//    }
}
