package adria.pfa.adriaReporting.repository;

import adria.pfa.adriaReporting.model.Codification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CodificationRepository extends JpaRepository<Codification, Long> {
    List<Codification> findAllByType(String type);
    Optional<Codification> findByCode(String code);

}
