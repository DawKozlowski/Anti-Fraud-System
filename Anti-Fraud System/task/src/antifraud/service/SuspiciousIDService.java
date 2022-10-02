package antifraud.service;


import antifraud.model.SuspiciousID;
import antifraud.model.User;
import antifraud.repository.SuspiciousIDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SuspiciousIDService {

    @Autowired
    SuspiciousIDRepository suspiciousIDRepository;


    @Transactional
    public Optional<SuspiciousID> addIp(SuspiciousID suspiciousID) {
        if (suspiciousIDRepository.existsByIp(suspiciousID.getIp())) {
            return Optional.empty();
        }
        return Optional.of(suspiciousIDRepository.save(suspiciousID));
    }

    public List<SuspiciousID> getIp() {
        return suspiciousIDRepository.findAll(Sort.sort(SuspiciousID.class).by(SuspiciousID::getId).ascending());
    }

    @Transactional
    public boolean delete(String ip) {
        return suspiciousIDRepository.deleteByIp(ip) == 1;
    }
}
