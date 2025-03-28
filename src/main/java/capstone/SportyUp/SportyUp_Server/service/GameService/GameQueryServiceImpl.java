package capstone.SportyUp.SportyUp_Server.service.GameService;

import capstone.SportyUp.SportyUp_Server.converter.GameConverter;
import capstone.SportyUp.SportyUp_Server.domain.Game;
import capstone.SportyUp.SportyUp_Server.domain.User;
import capstone.SportyUp.SportyUp_Server.repository.GameRepository;
import capstone.SportyUp.SportyUp_Server.repository.UserRepository;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameQueryServiceImpl implements GameQueryService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Override
    public GameResponseDTO.ChartDTO getChart(Long userId, Long sportsId) {

        User user = userRepository.findById(userId).orElse(null);   //Todo: 유저 확인
        List<Game> gameList = gameRepository.findByUserIdAndSportsId(userId,sportsId);
        List<Game> currentGameList = gameRepository.findTop6ByUserIdAndSportsIdOrderByPlayDateDesc(userId, sportsId);

        if (gameList.isEmpty()) {
            // 게임이 없는 경우, 0 또는 null로 처리
            return null;
        }

        // 평균, 최대, 최소 점수 계산
        double averageScore = gameList.stream()
                .mapToInt(Game::getScore)
                .average()
                .orElse(0.0);

        int highScore = gameList.stream()
                .mapToInt(Game::getScore)
                .max()
                .orElse(0);

        int lowScore = gameList.stream()
                .mapToInt(Game::getScore)
                .min()
                .orElse(0);

        long gameCount = gameList.size();

        return GameConverter.toChartDTO(gameCount, averageScore, highScore, lowScore, currentGameList);
    }

    @Override
    public GameResponseDTO.GameDateListDTO getGameDateListAllCategory(Long userId, Integer year, Integer month) {
        User user = userRepository.findById(userId).orElse(null);   //Todo: 유저 확인
        List<Game> gameList = gameRepository.findByUserIdAndYearAndMonth(userId,year,month);

        // playDate 기준으로 날짜(LocalDate) 중복 제거
        List<Game> distinctByDate = gameList.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                game -> game.getPlayDate().toLocalDate(), // 날짜만 key로 사용
                                game -> game,
                                (existing, replacement) -> existing // 중복 시 첫 번째 것 유지
                        ),
                        map -> new ArrayList<>(map.values())
                ));

        return GameConverter.toGameDateListDTO(distinctByDate);
    }

    @Override
    public GameResponseDTO.GameDateListDTO getGameDateListOneCategory(Long userId, Integer year, Integer month, Long sportsId) {
        User user = userRepository.findById(userId).orElse(null);   //Todo: 유저 확인
        List<Game> gameList = gameRepository.findByUserIdAndSportsIdAndYearAndMonth(userId,sportsId,year,month);

        // playDate 기준으로 날짜(LocalDate) 중복 제거
        List<Game> distinctByDate = gameList.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                game -> game.getPlayDate().toLocalDate(), // 날짜만 key로 사용
                                game -> game,
                                (existing, replacement) -> existing // 중복 시 첫 번째 것 유지
                        ),
                        map -> new ArrayList<>(map.values())
                ));

        return GameConverter.toGameDateListDTO(distinctByDate);
    }
}
