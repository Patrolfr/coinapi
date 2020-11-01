package go.fraczek.coinapi.instrument;

import java.util.Map;

public interface InstrumentRegistry {
    void registerInstrument(InstrumentDto tickerProductDto);

    Map<String, InstrumentDto> getInstruments();
}
