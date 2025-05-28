package capstone.SportyUp.SportyUp_Server.service.HomeService;


import capstone.SportyUp.SportyUp_Server.apiPayload.Exception.UserHandler;
import capstone.SportyUp.SportyUp_Server.apiPayload.code.status.ErrorStatus;
import capstone.SportyUp.SportyUp_Server.converter.HomeConverter;
import capstone.SportyUp.SportyUp_Server.domain.User;
import capstone.SportyUp.SportyUp_Server.domain.UserSports;
import capstone.SportyUp.SportyUp_Server.domain.enums.UserSportsLevel;
import capstone.SportyUp.SportyUp_Server.repository.UserRepository;
import capstone.SportyUp.SportyUp_Server.repository.UserSportsRepository;
import capstone.SportyUp.SportyUp_Server.web.DTO.HomeDTO.HomeResponseDTO;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeRecommandServiceImpl implements HomeRecommandService {
    private final UserRepository userRepository;
    private final UserSportsRepository userSportsRepository;
    private final RestTemplate restTemplate;

    @Value("${google.youtube.key}")
    private String YOUTUBE_API_KEY;
    private String YOUTUBE_VIDEO_URL_BASE = "https://www.youtube.com/watch?v=";

    @Override
    public HomeResponseDTO.RecommendedVideoListDTO getRecommendedVideos(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        UserSports userSports = userSportsRepository.findTop1ByUser(user).orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        String sports = userSports.getSports().getName();
        UserSportsLevel level = userSports.getLevel();
        String levelStr = "초급자";
        switch(level){
            case BEGINNER -> levelStr = "초급자";
            case INTERMEDIATE -> levelStr = "중급자";
            case ADVANCED -> levelStr = "고급자";
        }

        JsonFactory jsonFactory = new JacksonFactory();

        // YouTube 객체를 빌드하여 API에 접근할 수 있는 YouTube 클라이언트 생성
        YouTube youtube = new YouTube.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                jsonFactory,
                request -> {})
                .build();

        //YouTube API 요청
        String keyword = String.format("%s %s 강의 영상", sports, levelStr);
//        System.out.println(keyword);
//
//        String apiUrl = UriComponentsBuilder.fromHttpUrl("https://www.googleapis.com/youtube/v3/search")
//                .queryParam("part", "snippet")
//                .queryParam("q", keyword)
//                .queryParam("regionCode", "KR")
//                .queryParam("maxResults", 10)
//                .queryParam("type", "video")
//                .queryParam("key", YOUTUBE_API_KEY)
//                .toUriString();
//
//        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        //API 요청 내용 설정
        YouTube.Search.List search = null;
        try {
            search = youtube.search().list(Collections.singletonList("id,snippet"));
            search.setKey(YOUTUBE_API_KEY);
            search.setQ(keyword);
            search.setMaxResults(10L);
            search.setRegionCode("KR");
            search.setType(Collections.singletonList("video"));
            SearchListResponse searchResponse = search.execute();

            //검색 결과에서 동영상 목록
            List<SearchResult> searchResultList = searchResponse.getItems();


            //YouTube API 응답
            List<String> videoUrls = new ArrayList<>();
            List<String> thumbnailUrls = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            List<String> channelTitles = new ArrayList<>();

            if(searchResultList != null && searchResultList.size() > 0 ){
                for(SearchResult searchResult : searchResultList){
                    videoUrls.add(YOUTUBE_VIDEO_URL_BASE+searchResult.getId().getVideoId());
                    thumbnailUrls.add(searchResult.getSnippet().getThumbnails().getDefault().getUrl());
                    titles.add(searchResult.getSnippet().getTitle());
                    channelTitles.add(searchResult.getSnippet().getChannelTitle());
                }
            }

            return HomeConverter.toRecommendedVideoListDTO(videoUrls, thumbnailUrls, titles, channelTitles);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
