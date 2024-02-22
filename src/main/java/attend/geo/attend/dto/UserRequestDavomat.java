package attend.geo.attend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDavomat {
private Timestamp startDate;
private Timestamp endDate;
private Date date;
}
