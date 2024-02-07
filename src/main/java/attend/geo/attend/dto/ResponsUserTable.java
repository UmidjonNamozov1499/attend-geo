package attend.geo.attend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponsUserTable {
    private String userName;
    private List<Long> workingHours;
}
