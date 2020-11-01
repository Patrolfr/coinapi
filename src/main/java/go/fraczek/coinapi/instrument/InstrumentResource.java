package go.fraczek.coinapi.instrument;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.TreeSet;

@RestController
@RequestMapping(value = "/api/instruments")
@RequiredArgsConstructor
public class InstrumentResource {

    private final InstrumentRegistry instrumentRegistry;

    @GetMapping
    public ResponseEntity<Set<InstrumentDto>> getAllInstruments() {
        return ResponseEntity.ok(new TreeSet<>(instrumentRegistry.getInstruments().values()));
    }

}
