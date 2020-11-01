package go.fraczek.coinapi

import go.fraczek.coinapi.stub.StubWebSocketHandler
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.util.function.Consumer

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT


/**
 * Abstract class to be extended in web socket integration tests. Class provides helper method
 * for stubbing web socket messages.
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = DEFINED_PORT)
abstract class WebSocketIntegrationTest extends Specification {

    @Autowired
    StubWebSocketHandler stubWebSocketHandler

    protected void stubSocket(Consumer<StubWebSocketHandler> custom) {
        custom.accept(stubWebSocketHandler)
    }

}
