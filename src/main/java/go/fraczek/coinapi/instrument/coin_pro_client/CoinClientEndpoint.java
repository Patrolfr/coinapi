package go.fraczek.coinapi.instrument.coin_pro_client;

import go.fraczek.coinapi.instrument.coin_pro_client.exception.WebSocketRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import javax.annotation.PreDestroy;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint(encoders = {SubscribeMsgJsonTextEncoder.class})
public class CoinClientEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(CoinClientEndpoint.class);

    private final ApplicationEventPublisher eventPublisher;
    protected WebSocketContainer container;
    protected Session session;
    protected String serverUri;

    public CoinClientEndpoint(ApplicationEventPublisher eventPublisher, String serverUri) {
        this.container = ContainerProvider.getWebSocketContainer();
        this.eventPublisher = eventPublisher;
        this.serverUri = serverUri;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        this.session = connect();
        sendSubscriptionMassage();
    }

    @OnMessage
    public void onMessage(String message) {
        eventPublisher.publishEvent(new CoinEvent(this, message));
    }

    @PreDestroy
    public void preDestroy() {
        try {
            disconnect();
        } catch (IOException e) {
//        Do nothing. Instance will be destroyed anyway.
        }
    }

    public Session connect() {
        try {
            Session session = container.connectToServer(this, new URI(serverUri));
            logger.info(String.format("Successfully connected to server: %s, sessionId: %s", serverUri, session.getId()));
            return session;
        } catch (DeploymentException | URISyntaxException | IOException exception) {
            throw new WebSocketRuntimeException(
                    String.format("Could not connect to Coin Pro web socket. Server URI: %s", serverUri), exception
            );
        }
    }

    private void sendSubscriptionMassage() {
        SubscribeMessage message = SubscribeMessage.initMsg();
        try {
            session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException exception) {
            throw new WebSocketRuntimeException(
                    String.format("Could not sent subscription message to server. Message: %s", message.toString()),
                    exception
            );
        }
        logger.info(String.format("Subscription message has been sent: %s", message.toString()));
    }

    private void disconnect() throws IOException {
        session.close();
        logger.info(String.format("Successfully disconnected from server. Session %s has been closed.", session.getId()));
    }
}
