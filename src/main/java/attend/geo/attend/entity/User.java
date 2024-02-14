package attend.geo.attend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "user_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(nullable = false)
    private String userName;

    private Long randomCode;
    private String position;
    private Boolean connection;

    private String device;


    private Boolean isBlocked = true;

    @ManyToMany
    private List<Images> images;


}
