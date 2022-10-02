package antifraud.repository;

import antifraud.model.SuspiciousID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuspiciousIDRepository extends JpaRepository<SuspiciousID, Long> {

    boolean existsByIp(String ip);

    int deleteByIp(String ip);
}
