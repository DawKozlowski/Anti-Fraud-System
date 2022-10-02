package antifraud.controller;


import antifraud.model.StolenCard;
import antifraud.model.SuspiciousID;
import antifraud.model.dto.AmountRequest;
import antifraud.model.dto.ResultResponse;
import antifraud.service.StolenCardService;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/antifraud/stolencard")
public class StolenCardController {

    @Autowired
    StolenCardService stolenCardService;

    @PreAuthorize("hasRole('SUPPORT')")
    @PostMapping
    StolenCard post(@Valid @RequestBody StolenCard stolenCard) {
        return stolenCardService.addStolenCard(stolenCard).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }

    @PreAuthorize("hasRole('SUPPORT')")
    @GetMapping
    List<StolenCard> get() {
        return stolenCardService.getStolenCards();
    }

    @PreAuthorize("hasRole('SUPPORT')")
    @DeleteMapping("/{number}")
    Map<String, String> delete(@PathVariable(required = false) @LuhnCheck String number) {
        if(stolenCardService.delete(number)) {
            return Map.of(
                    "status", "Card " +number+ " successfully removed!"
            );
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
