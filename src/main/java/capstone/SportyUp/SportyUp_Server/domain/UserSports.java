package capstone.SportyUp.SportyUp_Server.domain;

import capstone.SportyUp.SportyUp_Server.domain.common.BaseEntity;
import capstone.SportyUp.SportyUp_Server.domain.enums.UserSportsGoal;
import capstone.SportyUp.SportyUp_Server.domain.enums.UserSportsLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSports extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserSportsLevel level;   //현재 실력

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserSportsGoal goal;    //본인 목표

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sports_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Sports sports;
}
