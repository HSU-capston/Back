package capstone.SportyUp.SportyUp_Server.domain;

import capstone.SportyUp.SportyUp_Server.domain.common.BaseEntity;
import capstone.SportyUp.SportyUp_Server.domain.enums.PoseScore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalyzeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String videoUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PoseScore poseScore;

    @Column(nullable = false)
    private String recommendPose;   //추천자세

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

}
