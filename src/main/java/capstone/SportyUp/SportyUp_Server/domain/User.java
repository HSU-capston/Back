package capstone.SportyUp.SportyUp_Server.domain;

import capstone.SportyUp.SportyUp_Server.domain.common.BaseEntity;
import capstone.SportyUp.SportyUp_Server.domain.enums.Role;
import capstone.SportyUp.SportyUp_Server.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String phoneNum;

    private LocalDate birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;  //default = ACTIVE

    private LocalDateTime inactiveDate;

    private String profileImgUrl;

    private String backgroundImgUrl;

    @Enumerated(EnumType.STRING)
    private Role role;


    public void encodePassword(String password) {
        this.password = password;
    }
}
