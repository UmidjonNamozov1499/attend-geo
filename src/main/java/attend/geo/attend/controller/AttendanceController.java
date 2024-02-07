package attend.geo.attend.controller;

import attend.geo.attend.service.AttendanceService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.Date;

@ComponentScan("controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;
    @GetMapping(value = "/read-attendance")
    public HttpEntity<?> readAttendance(
            @RequestParam(value = "userId",required = false) Integer userId,
            @RequestParam(value = "start",required = false) @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd") Date start,
            @RequestParam(value = "end",required = false) @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd") Date end,
            Pageable pageable
    ){
        return attendanceService.readAllAttendance(pageable,start,end);
    }
}
