package antifraud.service;

import antifraud.model.StolenCard;
import antifraud.model.SuspiciousID;
import antifraud.model.dto.ResultResponse;
import antifraud.repository.StolenCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StolenCardService {

    @Autowired
    StolenCardRepository stolenCardRepository;


    @Transactional
    public Optional<StolenCard> addStolenCard(StolenCard stolenCard) {
        if (stolenCardRepository.existsByNumber(stolenCard.getNumber())) {
            return Optional.empty();
        }
        return Optional.of(stolenCardRepository.save(stolenCard));
    }

    public List<StolenCard> getStolenCards() {
        return stolenCardRepository.findAll(Sort.sort(StolenCard.class).by(StolenCard::getId).ascending());
    }

    @Transactional
    public boolean delete(String number) {
        return stolenCardRepository.deleteByNumber(number) == 1;
    }
}
