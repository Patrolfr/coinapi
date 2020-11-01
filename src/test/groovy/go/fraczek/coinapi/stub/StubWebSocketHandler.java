package go.fraczek.coinapi.stub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * Test WebSocketHandler implementation giving programmatic access to web socket stub server.
 * To be used for testing purposes. Server messages are sent to last connected client.
 * NOT THREAD SAFE FOR NOW. Sufficient for single session scenarios.
 */
public class StubWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(StubWebSocketHandler.class);
    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.info("Stub server received message: {}", message);
    }

    public void sendMessage(String message) throws IOException {
        session.sendMessage(new TextMessage(message));
    }

}
