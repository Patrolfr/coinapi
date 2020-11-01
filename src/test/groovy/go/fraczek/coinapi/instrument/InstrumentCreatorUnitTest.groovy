package go.fraczek.coinapi.instrument


import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class InstrumentCreatorUnitTest extends Specification {

    static def PRODUCT_ID = 'SOME_ID'
    static def DOUBLE_STRING_VALID = '1234.56'
    static def DOUBLE_STRING_INVALID = '1234.56.00'
    static def EXPECTED_DOUBLE = Double.parseDouble(DOUBLE_STRING_VALID)
    static def TIME_STRING_VALID = '2020-10-28T17:28:35.090946Z'
    static def TIME_STRING_INVALID = 'should_not_be_parsed_as_time'
    static def EXPECTED_TIME_STING = '17:28:35'

    static def KEY_PRODUCT_ID = "product_id"
    static def KEY_BEST_BID = "best_bid"
    static def KEY_BEST_ASK = "best_ask"
    static def KEY_PRICE = "price"
    static def KEY_TIME = "time"

    def validMsg = Map.of(
            KEY_PRODUCT_ID, PRODUCT_ID,
            KEY_BEST_BID, DOUBLE_STRING_VALID,
            KEY_BEST_ASK, DOUBLE_STRING_VALID,
            KEY_PRICE, DOUBLE_STRING_VALID,
            KEY_TIME, TIME_STRING_VALID,
    )

    def invalidMsgWithoutDoubleValues = Map.of(
            KEY_PRODUCT_ID, PRODUCT_ID,
            KEY_TIME, TIME_STRING_VALID,
    )

    def invalidMsgWithWrongDoubleValues = Map.of(
            KEY_PRODUCT_ID, PRODUCT_ID,
            KEY_BEST_BID, DOUBLE_STRING_INVALID,
            KEY_BEST_ASK, DOUBLE_STRING_INVALID,
            KEY_PRICE, DOUBLE_STRING_INVALID,
    )

    def invalidMsgWithoutTimeValue = Map.of(
            KEY_PRODUCT_ID, PRODUCT_ID,
    )

    def invalidMsgWithWrongTimeFormat = Map.of(
            KEY_PRODUCT_ID, PRODUCT_ID,
            KEY_TIME, TIME_STRING_INVALID,
    )

    def 'given valid message should create InstrumentDto'() {
        given: 'map with valid entries'

        when: 'creating instrumentDto from map'
        def instrumentDto = InstrumentCreator.fromMessageMap(validMsg)

        then: 'should create valid instrument'
        assertThat(instrumentDto.instrument).isEqualTo(PRODUCT_ID)
        assertThat(instrumentDto.bid).isEqualTo(EXPECTED_DOUBLE)
        assertThat(instrumentDto.ask).isEqualTo(EXPECTED_DOUBLE)
        assertThat(instrumentDto.last).isEqualTo(EXPECTED_DOUBLE)
        assertThat(instrumentDto.time).isEqualTo(EXPECTED_TIME_STING)
    }

    def 'given message without best_bid/best_ask/price should create InstrumentDto'() {
        given: 'map without double values'

        when: 'creating instrumentDto from map'
        def instrumentDto = InstrumentCreator.fromMessageMap(invalidMsgWithoutDoubleValues)

        then: 'should create instrument with null double fields'
        assertThat(instrumentDto.instrument).isEqualTo(PRODUCT_ID)
        assertThat(instrumentDto.bid).isNull()
        assertThat(instrumentDto.ask).isNull()
        assertThat(instrumentDto.last).isNull()
    }

    def 'given message with wrong best_bid/best_ask/price formats should create InstrumentDto'() {
        given: 'map with wrong double values'

        when: 'creating instrumentDto from map'
        def instrumentDto = InstrumentCreator.fromMessageMap(invalidMsgWithWrongDoubleValues)

        then: 'should create instrument with null double fields'
        assertThat(instrumentDto.instrument).isEqualTo(PRODUCT_ID)
        assertThat(instrumentDto.bid).isNull()
        assertThat(instrumentDto.ask).isNull()
        assertThat(instrumentDto.last).isNull()
    }

    def 'given message without time value should create InstrumentDto'() {
        given: 'map without time'

        when: 'creating instrumentDto from map'
        def instrumentDto = InstrumentCreator.fromMessageMap(invalidMsgWithoutTimeValue)

        then: 'should create instrument with null time'
        assertThat(instrumentDto.instrument).isEqualTo(PRODUCT_ID)
        assertThat(instrumentDto.time).isNull()
    }

    def 'given message with wrong time format should create InstrumentDto'() {
        given: 'map with wrong time format'

        when: 'creating instrumentDto from map'
        def instrumentDto = InstrumentCreator.fromMessageMap(invalidMsgWithWrongTimeFormat)

        then: 'should create instrument with null time'
        assertThat(instrumentDto.instrument).isEqualTo(PRODUCT_ID)
        assertThat(instrumentDto.time).isNull()
    }

    def 'given productId should create instrument with errorMessage'() {
        given: 'instrumentName'

        when: 'creating instrumentDto with error message'
        def instrumentDto = InstrumentCreator.withNoDataError(PRODUCT_ID)

        then: 'should create instrument with error message and nulls'
        assertThat(instrumentDto.instrument).isEqualTo(PRODUCT_ID)
        assertThat(instrumentDto.error).isEqualTo('No data available for given instrument.')
        assertThat(instrumentDto.bid).isNull()
        assertThat(instrumentDto.ask).isNull()
        assertThat(instrumentDto.last).isNull()
    }

}
