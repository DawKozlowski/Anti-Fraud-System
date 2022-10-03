package antifraud.repository;

import antifraud.model.dto.AmountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<AmountRequest, Long> {

    @Query(value = "SELECT * FROM amount_request m WHERE m.number = :number AND m.date >= :givenDateMinusHour AND m.date < :givenDate", nativeQuery = true)
    List<AmountRequest> getAllOneHourOld(@Param("number") String number, @Param("givenDateMinusHour") LocalDateTime givenDateMinusHour, @Param("givenDate") LocalDateTime givenDate);
}
