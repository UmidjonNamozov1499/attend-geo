package attend.geo.attend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingsDto {
    private String name;
    private String IP;
    private Boolean active;
}
