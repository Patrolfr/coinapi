package go.fraczek.coinapi.instrument.coin_pro_client;

import lombok.Value;

import java.util.Set;

@Value
class SubscribeMessage {

    String type = "subscribe";
    Set<ChannelDto> channels;

    static SubscribeMessage initMsg(){
        return new SubscribeMessage(Set.of(ChannelDto.tickerChannel()));
    }

}
