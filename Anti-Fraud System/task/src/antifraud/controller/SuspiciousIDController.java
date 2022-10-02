package antifraud.controller;


import antifraud.model.SuspiciousID;
import antifraud.model.dto.AmountRequest;
import antifraud.model.dto.ResultResponse;
import antifraud.service.SuspiciousIDService;
import antifraud.validation.ValidIP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/antifraud/suspicious-ip")
@Validated
public class SuspiciousIDController {

    @Autowired
    SuspiciousIDService suspiciousIDService;

    @PreAuthorize("hasRole('SUPPORT')")
    @PostMapping
    SuspiciousID post(@Valid @RequestBody SuspiciousID suspiciousID) {
        return suspiciousIDService.addIp(suspiciousID).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }

    @PreAuthorize("hasRole('SUPPORT')")
    @GetMapping
    List<SuspiciousID> get() {
        return suspiciousIDService.getIp();
    }

    @PreAuthorize("hasRole('SUPPORT')")
    @DeleteMapping("/{ip}")
    Map<String, String> delete(@PathVariable @ValidIP String ip) {
        if(suspiciousIDService.delete(ip)) {
            return Map.of(
                    "status", "IP " +ip+ " successfully removed!"
            );
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
