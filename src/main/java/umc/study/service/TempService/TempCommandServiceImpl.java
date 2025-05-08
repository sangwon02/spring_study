import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.study.service.TempService.TempQueryService;

@Service
@RequiredArgsConstructor
public class TempCommandServiceImpl implements TempQueryService {

    @Override
    public void CheckFlag(Integer flag) {

    }
}