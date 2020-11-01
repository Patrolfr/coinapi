package go.fraczek.coinapi.instrument;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@Builder
class InstrumentDto implements Comparable<InstrumentDto>{
    String instrument;
    @JsonInclude(value = NON_NULL)
    Double bid;
    @JsonInclude(value = NON_NULL)
    Double ask;
    @JsonInclude(value = NON_NULL)
    Double last;
    @JsonInclude(value = NON_NULL)
    String time;
    @JsonInclude(value = NON_NULL)
    String error;


    /**
     * Comparator bases on instrument type since only one Instrument of given type is stored at a time.
     * @param instrumentDto the instrument to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
     * or greater than the specified object.
     */
    @Override
    @JsonIgnore
    public int compareTo(InstrumentDto instrumentDto) {
        return this.instrument.compareTo(instrumentDto.instrument);
    }
}
