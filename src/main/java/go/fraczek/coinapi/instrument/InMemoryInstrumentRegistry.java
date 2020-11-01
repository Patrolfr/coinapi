package go.fraczek.coinapi.instrument;

import go.fraczek.coinapi.instrument.coin_pro_client.ChannelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class InMemoryInstrumentRegistry implements InstrumentRegistry {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryInstrumentRegistry.class);
    private final ConcurrentMap<String, InstrumentDto> instrumentsByItsNames = new ConcurrentHashMap<>();

    @Override
    public void registerInstrument(InstrumentDto instrumentDto) {
        logger.info("Registering instrument: {}", instrumentDto);
        instrumentsByItsNames.put(instrumentDto.getInstrument(), instrumentDto);
    }

    @Override
    public Map<String, InstrumentDto> getInstruments() {

        HashMap<String, InstrumentDto> instrumentsSnapshot = new HashMap<>(instrumentsByItsNames);

        ChannelType.streamNames().forEach(
                channel -> instrumentsSnapshot.computeIfAbsent(channel, it -> InstrumentCreator.withNoDataError(channel))
        );

        return instrumentsSnapshot;
    }
}
