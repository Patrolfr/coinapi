package go.fraczek.experimental;

import com.fasterxml.jackson.databind.ObjectMapper;
//import go.fraczek.coinapi.instrument.Instrument;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

//import go.fraczek.coinapi.instrument.coin_pro_client.CoinEvent;

@Component
@RequiredArgsConstructor
public class ProductMessageForwarder {

    private final ObjectMapper objectMapper;

    @Autowired
    ApplicationEventPublisher eventPublisher;

//    public void decodeAndForward(CoinEvent coinEvent) throws JsonProcessingException {
//        final String rawMessage = coinEvent.getRawMessage();
//
//        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {
//        };
//        Map<String, Object> msgMap = objectMapper.readValue(rawMessage, typeReference);
//        Object type = msgMap.get("type");
//
//        if (!(type instanceof String)) {
//            logUnknownMessageTypeWarning(rawMessage);
//            return;
//        }
//
//        switch ((String) type) {
//            case "error":
//                break;
//            case "subscriptions":
//                break;
//            case "ticker":
//                Instrument tickerProductDto = decodeTickerEvent(msgMap);
//                eventPublisher.publishEvent(new TickerProductEvent(this, tickerProductDto));
//                break;
//            default:
//                logUnknownMessageTypeWarning(rawMessage);
//        }
//    }

//    private Instrument decodeTickerEvent(Map<String, Object> msgMap) {
//        return new Instrument(
//                (String) msgMap.get("product_id"),
//                Double.parseDouble((String) msgMap.get("best_bid")),
//                Double.parseDouble((String) msgMap.get("best_ask")),
//                Double.parseDouble((String) msgMap.get("price")),
//                (String) msgMap.get("time"),
//                null
//        );
//    }

    private void logUnknownMessageTypeWarning(String message) {
        return;
    }
}
