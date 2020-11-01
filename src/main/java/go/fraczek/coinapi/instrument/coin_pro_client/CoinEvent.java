package go.fraczek.coinapi.instrument.coin_pro_client;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

@Value
@EqualsAndHashCode(callSuper = true)
class CoinEvent extends ApplicationEvent {

    String rawMessage;

    CoinEvent(Object source, String rawMessage) {
        super(source);
        this.rawMessage = rawMessage;
    }

    String getRawMessage() {
        return rawMessage;
    }
}
