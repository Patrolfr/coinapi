package go.fraczek.experimental;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import go.fraczek.coinapi.instrument.Instrument;
import lombok.SneakyThrows;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

//public class TickerMessageDecoder implements Decoder.Text<Instrument> {
//
//    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//    @SneakyThrows
//    @Override
//    public Instrument decode(String s) throws DecodeException {
//        return objectMapper.readValue(s, Instrument.class);
//    }
//
//    @Override
//    public boolean willDecode(String s) {
//        return true;
//    }
//
//    @Override
//    public void init(EndpointConfig endpointConfig) {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
