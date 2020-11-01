package go.fraczek.coinapi.instrument.coin_pro_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import go.fraczek.coinapi.common.TypeReferences;
import go.fraczek.coinapi.instrument.InstrumentCreator;
import go.fraczek.coinapi.instrument.InstrumentRegistry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import java.util.Map;

@RequiredArgsConstructor
public class ProductEventsListener {

    private static final Logger logger = LoggerFactory.getLogger(ProductEventsListener.class);
    private static final String MESSAGE_TYPE_TICKER = "ticker";
    private static final String MESSAGE_TYPE_KEY = "type";

    private final InstrumentRegistry instrumentRegistry;
    private final ObjectMapper objectMapper;


    @EventListener(value = CoinEvent.class)
    public void handleCoinEvent(CoinEvent coinEvent) throws JsonProcessingException {

        Map<String, Object> messageAsMap = objectMapper.readValue(coinEvent.getRawMessage(),
                TypeReferences.MAP_STRING_TO_OBJECT);

        if (shouldNotProcessMessage(messageAsMap.get(MESSAGE_TYPE_KEY))) {
            logger.info("Coin message has been filtered out from processing. Message: {}", messageAsMap);
            return;
        }

        instrumentRegistry.registerInstrument(InstrumentCreator.fromMessageMap(messageAsMap));
    }

    private boolean shouldNotProcessMessage(Object messageType) {
        return !shouldProcessMessage(messageType);
    }

    private boolean shouldProcessMessage(Object messageType) {
        return MESSAGE_TYPE_TICKER.equals(messageType);
    }
}
