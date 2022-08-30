package adria.pfa.adriaReporting.repository;

import adria.pfa.adriaReporting.model.Banque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BanqueRepository extends JpaRepository<Banque, Long> {
    Optional<Banque> findByCodeBIC(String codeBIC);
}
