package go.fraczek.coinapi;

import go.fraczek.coinapi.instrument.coin_pro_client.CoinSocketProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {CoinSocketProperties.class})
public class CoinApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoinApiApplication.class, args);
    }

}
