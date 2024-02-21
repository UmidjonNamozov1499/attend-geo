package attend.geo.attend.service;

import attend.geo.attend.dto.PageRequests;
import attend.geo.attend.dto.PageResponse;
import attend.geo.attend.entity.User;
import attend.geo.attend.entity.UserAttendance;
import attend.geo.attend.payload.Payload;
import attend.geo.attend.repository.UserAttendanceRepository;
import attend.geo.attend.repository.UserRepository;
import attend.geo.attend.specification.UserAttendanceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Service
public class DavomatService {
    private final UserAttendanceRepository userAttendanceRepository;
    private final UserRepository userRepository;

    public HttpEntity<?> getAllUsersDavomat(Date start, Date endDate, PageRequests pageable) {
        if (start != null && endDate != null) {
//            Page<UserAttendance> byDateBetweenOrderByDateAsc = userAttendanceRepository.findAllByDateBetweenOrderByDateAsc(start, endDate,PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
//
//            List<UserAttendance> userAttendanceList = byDateBetweenOrderByDateAsc.stream().map(r->
//                    new UserAttendance(r.getId(),
//                            r.getUserName(),
//                            r.getStartDate(),
//                            r.getEndDate(),
//                            r.getWorkingHours(),
//                            r.getItIsWork(),
//                            r.getDate(),
//                            r.getDeviceName(),
//                            r.getIsReason()
//                    )).collect(Collectors.toList());
//            PageResponse pageResponse=new PageResponse(pageable.getPageSize(), pageable.getPageNumber());
//            return Payload.ok(userAttendanceList).response();
//            Specification<User> spec = UserAttendanceSpecification.betweenDates(start, endDate);
//            Pageable pageableRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
//            Page<UserAttendance> all = userAttendanceRepository.findAll(spec, pageableRequest);
//            return Payload.ok(all).response();
//        } else if (start != null && endDate == null) {
//            return Payload.ok(userAttendanceRepository.findByDate(start)).response();
//        } else if (start == null && endDate != null) {
//            return Payload.ok(userAttendanceRepository.findByDate(start)).response();
//        } else if (start == null && endDate == null) {
//            Page<UserAttendance> all = userAttendanceRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
//            return Payload.ok(all).response();
//        } else {
//            return ResponseEntity.ok().body("Date error");
//        }
        }
        return null;
    }

    public HttpEntity<?> getAllUsers(Date start, Date endDate,PageRequests pageableReq) {
        try {
            if (pageableReq != null && start != null & endDate != null) {
                Pageable pageable = PageRequest.of(pageableReq.getPageNumber(), pageableReq.getPageSize());
                Specification<User> spec = UserAttendanceSpecification.betweenDates(start, endDate);
                Page<User> all = userRepository.findAll(spec,pageable);
                List<UserAttendance> userAttendanceList = new ArrayList<>();
                for (User user : all.getContent()) {
                    Timestamp startDate = user.getStartDate();
                    Timestamp endDate1 = user.getEndDate();

                    long startTimeInMillis = startDate.getTime();
                    long endTimeInMillis = endDate1.getTime();

                    long timeDifferenceInMillis = endTimeInMillis - startTimeInMillis;

                    long seconds = timeDifferenceInMillis / 1000;
                    long minutes = seconds / 60;
                    long hours = minutes / 60;
                    // 12:00
                    Long workingHours = hours;
                    UserAttendance userAttendance = new UserAttendance();
                    Optional<User> byId = userRepository.findById(user.getId());
                    User user1 = byId.get();
                    user1.setWorkingHours(workingHours);
                    user1.setId(user.getId());
                    userAttendance.setId(user.getId());
                    userAttendance.setUserName(user.getUserName());
                    userAttendance.setWorkingHours(workingHours);
                    userAttendance.setStartDate(user.getStartDate());
                    userAttendance.setEndDate(user.getEndDate());
                    userAttendance.setDate(user.getDate());
                    userAttendanceList.add(userAttendance);
                    userRepository.save(user1);
                }
                return Payload.ok(userAttendanceList).response();
            }else {
                return Payload.conflict("Request error").response();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Payload.conflict(e.getMessage()).response();
        }
    }
}
