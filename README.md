## Simple coinbase pro API
![Test](https://github.com/Patrolfr/coinapi/workflows/Test/badge.svg)

Simple REST API that integrates with Coinbase Pro web socket API and returns Ticker channel products details.

Service subscribes to coinbase pro webb socket channel and register last message for each product.
Messages are available in form of Instrument list available at /api/instruments endpoint.

### Build
- Clone repo and navigate to project root directory.
- Generate gradle wrapper `gradle wrapper`
#### a) Run app from sources
- Run `./gradlew bootRun`

Sample usage:
`curl -GET http://localhost:8080/api/instruments`
Sample response:  
```
[{"instrument":"BTC-EUR","bid":11881.58,"ask":11881.6,"last":11881.6,"time":"18:10:39"},
{"instrument":"BTC-USD","bid":13831.3,"ask":13831.32,"last":13831.32,"time":"18:10:44"},
{"instrument":"ETH-EUR","error":"No data available for given instrument."},
{"instrument":"ETH-USD","error":"No data available for given instrument."}]
```


Exercise content:
 Napisać aplikację w języku java, która:
 1. Podłączy się za pomocą interfejsu websocket do api giełdy coinbase pro
 2. Będzie odbierać na bieżąco wiadomości z kanału ‘ticker’
 3. Udostępni możliwość podglądu ostatnich otrzymanych wartości dla każdego instrumentu poprzez protokół HTTP w formacie JSON.
 Szczegółowy opis zadania:
 ● Proszę nie korzystać z dostępnych bibliotek implementujących dostęp do API coinbase pro
 ● Dokumentacje api giełdy coinbase pro można znaleźć tutaj: https://docs.pro.coinbase.com/?r=1#websocket-feed
 ● Należy zasubskrybować się na następujące instrumenty: BTCUSD,BTCEUR,ETHUSD,ETHEUR na kanale ‘ticker’.
 ● Odbierając wiadomości należy zapamiętać ostatnią wiadomość dla każdego instrumentu.
 ● Aplikacja udostępnia ostatnie wiadomości po HTTP w formacie JSON. Przykład wyniku:
```
 [{"instrument":"BTCUSD", "bid": 9584.18, "ask":9584.19, “last”:9584.18, “time”:”12:32:02”},
 {"instrument":"BTCEUR", "bid": 8876.69, "ask": 8877.38, “last”:8877.38, “time”:”12:32:02”},
 {"instrument":"ETHUSD", "bid":211.33, "ask":211.38, “last”:211.33, “time”:”12:32:02”}, 
 {"instrument":"ETHEUR", "bid":195.01, "ask":195.15, “last”:195.15, “time”:”12:32:02”}]
```
 ● Aplikacja wyświetla jedynie json - nie ma potrzeby tworzenia strony html.
 ● Mapowania pól między wiadomością z websocket api -> a wynikowym JSONem
 "product_id" -> “instrument”
 "best_bid" -> “bid”
 "best_ask" -> “ask”
 "price" -> “last”
 “time” -> “time” 
 Przykład:
 Wiadomość websocket:
 {
 "type": "ticker",
 "sequence": 14165935228, "product_id": "BTC-USD",
 "price": "9584.18",
 "open_24h": "9740.00000000", 
 "volume_24h": "19882.16807137", 
 "low_24h": "9436.97000000", 
 "high_24h": "9957.25000000", 
 "volume_30d": "654772.33925563", 
 "best_bid": "9584.18",
 "best_ask": "9584.19",
 "side": "sell",
 "time": "2020-05-18T12:32:02.331872Z", "trade_id": 92323410,
 "last_size": "0.02252"
 }
 Wynikowy json:
 {"instrument": "BTCUSD", "bid": 9584.18, "ask": 9584.19, “last”: 9584.18, “time”:”12:32:02”}
