package adria.pfa.adriaReporting.repository;

import adria.pfa.adriaReporting.model.Beneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, Long> {
    Optional<Object> findByAccount(Long account);
}
