package go.fraczek.coinapi.instrument.coin_pro_client;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class SubscribeMsgJsonTextEncoder implements Encoder.Text<SubscribeMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String encode(SubscribeMessage object) {
        return objectMapper.writeValueAsString(object);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
//        nothing to initialize
    }

    @Override
    public void destroy() {
//        no resources to close
    }

}
