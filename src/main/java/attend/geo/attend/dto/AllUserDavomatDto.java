package attend.geo.attend.dto;

import attend.geo.attend.entity.UserAttendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllUserDavomatDto {
    private List<UserAttendance> userAttendanceList;
    private PageResponse pageResponse;
}
