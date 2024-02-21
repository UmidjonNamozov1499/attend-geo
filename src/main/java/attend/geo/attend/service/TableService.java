package attend.geo.attend.service;

import attend.geo.attend.dto.ResponsUserTable;
import attend.geo.attend.entity.User;
import attend.geo.attend.entity.UserAttendance;
import attend.geo.attend.payload.Payload;
import attend.geo.attend.repository.UserAttendanceRepository;
import attend.geo.attend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@RequiredArgsConstructor
@Component
@Service
public class TableService {
    private final UserAttendanceRepository userAttendanceRepository;
    private final UserRepository userRepository;
    Calendar calendar = Calendar.getInstance();

    public HttpEntity<?> getUserTableExcel() throws IOException {
        String uploadExcel29 = "D:\\UzLITINEFTGAZ\\SpringBootDownloadGetAPIDtaInExcelFile-master\\attend\\uploadExcel\\employee29.xlsx";
        String uploadExcel30 = "D:\\UzLITINEFTGAZ\\SpringBootDownloadGetAPIDtaInExcelFile-master\\attend\\uploadExcel\\employee30.xlsx";
        String uploadExcel31 = "D:\\UzLITINEFTGAZ\\SpringBootDownloadGetAPIDtaInExcelFile-master\\attend\\uploadExcel\\employee31.xlsx";

        if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 29) {
            updateFile(uploadExcel29);
            return ResponseEntity.ok().body("Date 29");
        }
        if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 30) {
            updateFile(uploadExcel30);
            return ResponseEntity.ok().body("Date 30");
        }
        if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 31) {
            updateFile(uploadExcel31);
            return ResponseEntity.ok().body("Date 31");
        } else {
            return ResponseEntity.ok().body("Date error");
        }

    }

    public HttpEntity<?> getUsersTable(){
        try {
            List<User> allUsers = userRepository.findAll();
            Set<String> userTable = new TreeSet<>();
            List<ResponsUserTable> responsUserTables = new ArrayList<>();
            for (User allUser : allUsers) {
                String uniqueUser = allUser.getUserName();
                if (!userTable.contains(uniqueUser)) {
                    userTable.add(uniqueUser);
                }
            }
            for (String users : userTable) {
                ResponsUserTable responsUserTable = new ResponsUserTable();

                if (users != null) {
                    responsUserTable.setUserName(users);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    String startDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                    Date start = sdf.parse(startDate);
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    String endDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                    Date end = sdf.parse(endDate);
                    int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    List<User> byUserNameOrderByDateDesc = userRepository.findByUserNameAndDateBetweenOrderByDateAsc(users, start, end);
                    if (byUserNameOrderByDateDesc != null) {

                        List<Long> workingHours = new ArrayList<>();

                            for (User user : byUserNameOrderByDateDesc) {
                                for (int i = 1; i <= lastDayOfMonth; i++) {
                                Date date = user.getDate();
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);

                                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                                if (i == dayOfMonth){
                                    workingHours.add(user.getWorkingHours());
                                    responsUserTable.setWorkingHours(workingHours);
                                }else {
                                    workingHours.add(null);
                                    responsUserTable.setWorkingHours(workingHours);
                                }
                            }
//                            for (User user : byUserNameOrderByDateDesc) {
//                                if (user.getWorkingHours() != null) {
//                                    workingHours.add(user.getWorkingHours());
//                                    responsUserTable.setWorkingHours(workingHours);
//                                } else {
//                                    workingHours.add(null);
//                                    responsUserTable.setWorkingHours(workingHours);
//                                }
//                            }
                        }
                    }
                }
                responsUserTables.add(responsUserTable);
            }
           return Payload.ok(responsUserTables).response();
        } catch (Exception e) {
            e.printStackTrace();
            return Payload.conflict("Server error").response();
        }
    }


    public HttpEntity<?> updateFile(String uploadFile) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(uploadFile)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet("Users");

            int lastRowNum = sheet.getLastRowNum();
            for (int i = 10; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            try {
                List<UserAttendance> allUsers = userAttendanceRepository.findAll();
                Set<String> userTable = new TreeSet<>();
                List<String> responseUser = new ArrayList<>();
                List<Long> responseUserAttendanceDate = new ArrayList<>();

                for (UserAttendance allUser : allUsers) {
                    String uniqueUser = allUser.getUserName();
                    if (!userTable.contains(uniqueUser)) {
                        userTable.add(uniqueUser);
                    }
                }
                int rowNum = 9;
                int count = 1;
                for (String users : userTable) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(count++);
                    int cellNumber = 1;
                    if (users != null) {
                        row.createCell(cellNumber++).setCellValue(users);
                        responseUser.add(users);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                        Date start = sdf.parse(startDate);
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                        Date end = sdf.parse(endDate);
                        List<UserAttendance> byUserNameOrderByDateDesc = userAttendanceRepository.findByUserNameAndDateBetweenOrderByDateAsc(users, start, end);
                        for (UserAttendance userAttendance : byUserNameOrderByDateDesc) {
                            if (userAttendance.getWorkingHours() != null) {
                                row.createCell(cellNumber++).setCellValue(userAttendance.getWorkingHours());
                                responseUserAttendanceDate.add(userAttendance.getWorkingHours());
                            } else {
                                row.createCell(cellNumber++).setCellValue(0);
                            }
                        }
                        return ResponseEntity.ok().body("Cell update");
                    } else {
                        row.createCell(cellNumber).setCellValue(0);
                        return ResponseEntity.ok().body("Cell update");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok().body(e.getMessage());
            }
            try (FileOutputStream outputStream = new FileOutputStream(uploadFile)) {
                workbook.write(outputStream);
                return ResponseEntity.ok().body("Success file save");
            } catch (IOException e) {
                return ResponseEntity.ok().body(e.getMessage());
            }
        }
    }

}

