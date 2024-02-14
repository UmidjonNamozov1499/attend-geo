package attend.geo.attend.dto;

import attend.geo.attend.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String position;
    private Boolean isBlocked;
    private List<Images> images;
}
