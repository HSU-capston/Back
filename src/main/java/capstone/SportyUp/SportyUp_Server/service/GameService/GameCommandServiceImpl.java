package capstone.SportyUp.SportyUp_Server.service.GameService;

import capstone.SportyUp.SportyUp_Server.converter.GameConverter;
import capstone.SportyUp.SportyUp_Server.domain.Game;
import capstone.SportyUp.SportyUp_Server.domain.Sports;
import capstone.SportyUp.SportyUp_Server.domain.User;
import capstone.SportyUp.SportyUp_Server.repository.GameRepository;
import capstone.SportyUp.SportyUp_Server.repository.SportsRepository;
import capstone.SportyUp.SportyUp_Server.repository.UserRepository;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameRequestDTO;
import capstone.SportyUp.SportyUp_Server.web.DTO.GameDTO.GameResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GameCommandServiceImpl implements GameCommandService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final SportsRepository sportsRepository;

    @Override
    public GameResponseDTO.CreateResultDTO createGame(Long userId, GameRequestDTO.CreateDTO request) {

        User user = userRepository.findById(userId).orElse(null);
        Sports sports = sportsRepository.findById(request.getSportsId()).orElse(null);

        Game game = Game.builder()
                .user(user)
                .sports(sports)
                .playDate(LocalDateTime.now())
                .build();

        gameRepository.save(game);

        return GameConverter.toCreateResultDTO(game.getId());
    }
}
