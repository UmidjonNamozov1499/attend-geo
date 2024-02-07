package attend.geo.attend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponse {
    private Integer pageSize;
    private Integer pageNumber;
}
