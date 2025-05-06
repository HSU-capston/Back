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

    private Integer score;

    @Column(nullable = false, length = 255) //추천자세
    private String recommendPose;

    @Column(nullable = false, length = 255)   //잘한점
    private String goodPoint;

    @Column(nullable = false, length = 255)   //아쉬운점
    private String badPoint;

    private Integer shoulderAngleDiff;  //어깨각도

    private Integer movementDistance;   //평균 몸통 움직임

    private Integer wristMovementTotal; //총 팔 움직임

    private Integer ankleSwitchCount;   //스탭 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;



}
