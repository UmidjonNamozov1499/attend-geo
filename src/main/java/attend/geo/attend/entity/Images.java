package attend.geo.attend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String actualName;
    private String contentType;
    private String resourceType;
    private String extension;
    private Long fileSize;
    @Column(nullable = false)
    private UUID token;

    @PrePersist
    private void generateToken() {
        if (token == null) {
            token = UUID.randomUUID();
        }
    }
}
