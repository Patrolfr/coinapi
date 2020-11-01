package go.fraczek.coinapi.instrument.coin_pro_client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum ChannelType {
    BTC_EUR("BTC-EUR"),
    BTC_USD("BTC-USD"),
    ETH_EUR("ETH-EUR"),
    ETH_USD("ETH-USD");

    @Getter
    private final String channelName;

    public static Stream<String> streamNames() {
        return Stream.of(ChannelType.values()).map(ChannelType::getChannelName);
    }
}
