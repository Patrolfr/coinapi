package go.fraczek.coinapi.instrument.coin_pro_client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "coinbase")
public class CoinSocketProperties {

    String serverUrl;

}
