package com.vitiwari.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class MessageConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(128 * 1024);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
    // This registers the /ws endpoint as the WebSocket endpoint for clients.
    // Clients will use this URL to connect to the WebSocket server.

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/chatroom","/user");
//        registry.setUserDestinationPrefix("/user");
    }


}

//enableSimpleBroker("/chatroom", "/user"):
//
//This enables a simple in-memory message broker that will handle messages sent to destinations
// that start with /chatroom and /user.
//Messages sent to these destinations will be broadcast by the broker to all clients
// subscribed to those topics.

//For example:
///chatroom could be used for broadcasting messages to a public chatroom.
///user could be used for sending private messages to individual users.