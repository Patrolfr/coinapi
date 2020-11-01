package go.fraczek.coinapi.instrument

import go.fraczek.coinapi.WebSocketIntegrationTest
import org.awaitility.Awaitility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import java.time.Duration

import static go.fraczek.coinapi.instrument.CoinTestModels.getNotValidProductMessage
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
class InstrumentIntegrationTest extends WebSocketIntegrationTest {

    private static final INSTRUMENTS_API_URL = '/api/instruments'

    @Autowired
    InstrumentRegistry instrumentRegistry
    @Autowired
    MockMvc mockMvc

    def 'given non stubbed ticker messages should render response with errors'() {
        given: 'no stubs'
        when: 'no ticker message received'
        then: 'get instruments'
        def result = mockMvc.perform(MockMvcRequestBuilders.get(INSTRUMENTS_API_URL))
        expect: 'response with error messages'
        result
                .andExpect(jsonPath('$[0].error').value('No data available for given instrument.'))
                .andExpect(jsonPath('$[1].error').value('No data available for given instrument.'))
                .andExpect(jsonPath('$[2].error').value('No data available for given instrument.'))
                .andExpect(jsonPath('$[3].error').value('No data available for given instrument.'))
    }

    def 'given stubbed ticker error messages should render response with errors'() {
        given: 'stubbed not valid product messages'
        when: 'received from stub'
        stubSocket({ custom ->
            custom.sendMessage(getNotValidProductMessage(productError).toString())
        })

        then: 'wait for socket message and get instruments'
        Awaitility.await()
                .atMost(Duration.ofSeconds(1))
                .until({ it -> instrumentRegistry.getInstruments().get(productError).error != null })
        def result = mockMvc.perform(MockMvcRequestBuilders.get(INSTRUMENTS_API_URL))

        expect: 'response with error messages'
        result
                .andExpect(jsonPath('$[' + indexInResponse + '].error').value('No data available for given instrument.'))
        where:
        productError | indexInResponse
        'BTC-EUR'    | 0
        'BTC-USD'    | 1
        'ETH-EUR'    | 2
        'ETH-USD'    | 3
    }


    def 'given coin pro ticker message should render valid response'() {
        given: 'valid ticker message'
        def tickerMessage = CoinTestModels.getTickerProductMessage(receivedProduct)

        when: 'message received from stub'
        stubSocket { customSocket ->
            customSocket.sendMessage(tickerMessage.toString())
        }
        then: 'wait for socket message and get instruments'
        Awaitility.await()
                .atMost(Duration.ofSeconds(1))
                .until({ it -> instrumentRegistry.getInstruments().get(receivedProduct).bid != null })
        def result = mockMvc.perform(MockMvcRequestBuilders.get(INSTRUMENTS_API_URL))

        expect: 'response with valid instrument'
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath('$[' + indexInResponse + '].instrument').value(tickerMessage.getString('product_id')))
                .andExpect(jsonPath('$[' + indexInResponse + '].bid').value(tickerMessage.getDouble('best_bid')))
                .andExpect(jsonPath('$[' + indexInResponse + '].ask').value((tickerMessage.getDouble('best_ask'))))
                .andExpect(jsonPath('$[' + indexInResponse + '].last').value(tickerMessage.getDouble('price')))
        where:
        receivedProduct | indexInResponse
        'BTC-EUR'       | 0
        'BTC-USD'       | 1
        'ETH-EUR'       | 2
        'ETH-USD'       | 3
    }
}
