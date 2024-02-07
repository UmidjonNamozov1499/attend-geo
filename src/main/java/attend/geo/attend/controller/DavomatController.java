package attend.geo.attend.controller;

import attend.geo.attend.dto.GetAllUsersPageRequest;
import attend.geo.attend.service.DavomatService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/davomat")
public class DavomatController {
    private final DavomatService davomatService;

    @GetMapping(value = "/getAllUserDavomat")
    public HttpEntity<?> getAllUsersDavomat(
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam(value = "page", required = false) GetAllUsersPageRequest pageable) {
        return davomatService.getAllUsersDavomat(start, end,pageable);
    }
}
