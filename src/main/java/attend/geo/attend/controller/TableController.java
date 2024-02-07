package attend.geo.attend.controller;

import attend.geo.attend.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/table")
public class TableController {
    private final TableService tableService;
    @GetMapping(value = "/getUserTableExcel")
    public HttpEntity<?> getUserTableExcel() throws ParseException, IOException {
        return tableService.getUserTableExcel();
    }
    @GetMapping(value = "/getUserTable")
    public HttpEntity<?> getUserTable(){
        return tableService.getUsersTable();
    }
}
