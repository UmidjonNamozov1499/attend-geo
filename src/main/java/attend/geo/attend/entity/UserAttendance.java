package attend.geo.attend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity()
public class UserAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String userName;
    private Timestamp startDate;
    private Timestamp endDate;
    private Long workingHours;
    private Boolean itIsWork;
    private Date date;
    private String deviceName;
    private Boolean isReason;
}
