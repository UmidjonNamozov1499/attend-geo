package attend.geo.attend.service;

import attend.geo.attend.entity.UserAttendance;
import attend.geo.attend.repository.UserAttendanceRepository;
import attend.geo.attend.specification.UserAttendanceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Component
@Service
public class AttendanceService {

    private final UserAttendanceRepository userAttendanceRepository;
    public HttpEntity<?> readAllAttendance(Pageable pageable, Date start, Date end) {
        try {
            if (pageable != null && start != null && end != null) {
                Specification<UserAttendance> specification = UserAttendanceSpecification.dateBetween(start, end);
                Page<UserAttendance> all = userAttendanceRepository.findAll(specification, pageable);
                return ResponseEntity.ok(all);
            }else if (pageable != null && start != null){
                Specification<UserAttendance> specification = UserAttendanceSpecification.findByStartDate(start);
                Page<UserAttendance> all = userAttendanceRepository.findAll(specification, pageable);
                return ResponseEntity.ok(all);
            } else if (pageable != null && end != null) {
                Specification<UserAttendance> specification = UserAttendanceSpecification.findByEndDate(end);
                Page<UserAttendance> all = userAttendanceRepository.findAll(specification, pageable);
                return ResponseEntity.ok(all);
            }else {
                return ResponseEntity.ok().body("Request error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
