package attend.geo.attend.service;

import attend.geo.attend.dto.AllUserDavomatDto;
import attend.geo.attend.dto.GetAllUsersPageRequest;
import attend.geo.attend.dto.PageResponse;
import attend.geo.attend.entity.UserAttendance;
import attend.geo.attend.repository.UserAttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Service
public class DavomatService {
    private final UserAttendanceRepository userAttendanceRepository;
    public HttpEntity<?> getAllUsersDavomat(Date start, Date endDate, GetAllUsersPageRequest pageable) {
        if (start != null && endDate != null) {
            Page<UserAttendance> byDateBetweenOrderByDateAsc = userAttendanceRepository.findAllByDateBetweenOrderByDateAsc(start, endDate,PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

            List<UserAttendance> userAttendanceList = byDateBetweenOrderByDateAsc.stream().map(r->
                    new UserAttendance(r.getId(),
                            r.getUserName(),
                            r.getStartDate(),
                            r.getEndDate(),
                            r.getWorkingHours(),
                            r.getItIsWork(),
                            r.getDate(),
                            r.getDeviceName(),
                            r.getIsReason()
                    )).collect(Collectors.toList());
            PageResponse pageResponse=new PageResponse(pageable.getPageSize(), pageable.getPageNumber());
            return ResponseEntity.ok(new AllUserDavomatDto(userAttendanceList,pageResponse));
        } else {
            return ResponseEntity.ok().body("Date error");
        }
    }
}
