package go.fraczek.coinapi.instrument;

import com.fasterxml.jackson.databind.ObjectMapper;
import go.fraczek.coinapi.instrument.coin_pro_client.CoinClientEndpoint;
import go.fraczek.coinapi.instrument.coin_pro_client.CoinSocketProperties;
import go.fraczek.coinapi.instrument.coin_pro_client.ProductEventsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    @Autowired
    public CoinClientEndpoint coinClientEndpoint(ApplicationEventPublisher eventPublisher,
                                                 CoinSocketProperties properties) {
        return new CoinClientEndpoint(eventPublisher, properties.getServerUrl());
    }

    @Bean
    public InstrumentRegistry instrumentRegistry() {
        return new InMemoryInstrumentRegistry();
    }

    @Bean
    @Autowired
    public ProductEventsListener productEventsListener(InstrumentRegistry instrumentRegistry, ObjectMapper objectMapper) {
        return new ProductEventsListener(instrumentRegistry, objectMapper);
    }
}
