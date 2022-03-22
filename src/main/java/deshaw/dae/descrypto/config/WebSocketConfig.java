package deshaw.dae.descrypto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry){
       stompEndpointRegistry.addEndpoint("/market")
               .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry){
        messageBrokerRegistry.enableSimpleBroker("/topic");
        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
    }
}
