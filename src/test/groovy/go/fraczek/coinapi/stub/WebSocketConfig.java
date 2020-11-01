package go.fraczek.coinapi.stub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * Test configuration class providing embedded web socket server stub.
 * Stub behaviour should be determined with use of StubWebSocketHandler bean.
 */
@Controller // required to handle http upgrade request
@Configuration
@EnableWebSocket
@Profile("test")
public class WebSocketConfig implements WebSocketConfigurer {

    private static final String COIN_WEB_SOCKET_STUB_URL = "/coinstub";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
        registry.addHandler(webSocketStubHandler(), COIN_WEB_SOCKET_STUB_URL)
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy))
                .setAllowedOrigins("*");
    }

    @Bean
    public StubWebSocketHandler webSocketStubHandler() {
        return new StubWebSocketHandler();
    }

}
