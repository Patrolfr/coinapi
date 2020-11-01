package go.fraczek.coinapi.instrument.coin_pro_client;

import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value
class ChannelDto {
    String name;
    Set<String> product_ids;

    static ChannelDto tickerChannel() {
        return new ChannelDto("ticker", ChannelType.streamNames().collect(Collectors.toSet()));
    }
}
