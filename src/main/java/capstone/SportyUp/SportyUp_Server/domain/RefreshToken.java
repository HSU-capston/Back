package capstone.SportyUp.SportyUp_Server.domain;

import capstone.SportyUp.SportyUp_Server.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String token;

    private LocalDateTime expiredAt;
}
