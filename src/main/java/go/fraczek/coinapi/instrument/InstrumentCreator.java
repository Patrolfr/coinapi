package go.fraczek.coinapi.instrument;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class InstrumentCreator {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String NO_DATA_MESSAGE = "No data available for given instrument.";
    private static final String TICKER_KEY_PRODUCT_ID = "product_id";
    private static final String TICKER_KEY_BEST_BID = "best_bid";
    private static final String TICKER_KEY_BEST_ASK = "best_ask";
    private static final String TICKER_KEY_PRICE = "price";
    private static final String TICKER_KEY_TIME = "time";

    private InstrumentCreator() {
    }

    public static InstrumentDto fromMessageMap(Map<String, Object> messageAsMap) {
        return new InstrumentDto(
                (String) messageAsMap.get(TICKER_KEY_PRODUCT_ID),
                secureParseDouble((String) messageAsMap.get(TICKER_KEY_BEST_BID)),
                secureParseDouble((String) messageAsMap.get(TICKER_KEY_BEST_ASK)),
                secureParseDouble((String) messageAsMap.get(TICKER_KEY_PRICE)),
                secureParseTime((String) messageAsMap.get(TICKER_KEY_TIME)),
                null
        );
    }

    public static InstrumentDto withNoDataError(String instrumentName) {
        return InstrumentDto.builder()
                .instrument(instrumentName)
                .error(NO_DATA_MESSAGE)
                .build();
    }

    private static Double secureParseDouble(String doubleString) {
        try {
            return Double.parseDouble(doubleString);
        } catch (NumberFormatException | NullPointerException exc) {
            return null;
        }
    }

    private static String secureParseTime(String timeString) {
        try {
            return OffsetDateTime.parse(timeString).format(TIME_FORMATTER);
        } catch (DateTimeParseException | NullPointerException exc) {
            return null;
        }
    }

}
