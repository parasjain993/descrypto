package deshaw.dae.descrypto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedRate = 2000)
    public void SendOrderBookData(){
        simpMessagingTemplate.convertAndSend("/topic", "Fixed delay response");
    }

}
