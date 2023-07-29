package backendpage.proyectosismo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
public class BlackListingService {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Transactional
    public void blackListServiceJwt(String jwt){
        ValueOperations<String, String> valueOp= redisTemplate.opsForValue();
        valueOp.set(jwt,jwt, Duration.ofHours(2));
    }

    @Transactional
    public String findJwtBlackList(String jwt) {
        ValueOperations<String, String> valueOp= redisTemplate.opsForValue();
        String json2 = valueOp.get(jwt);
        if(json2==null) return null;
        return json2;
    }


}
