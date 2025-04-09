package capstone.SportyUp.SportyUp_Server.aws.s3;

import capstone.SportyUp.SportyUp_Server.config.AmazonConfig;
import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    public String uploadFile(String keyName, MultipartFile file){
        return null;
    }
}
