package attend.geo.attend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userName;
    private String position;
    private Boolean isBlocked;
    private List<String> tokens;
}
