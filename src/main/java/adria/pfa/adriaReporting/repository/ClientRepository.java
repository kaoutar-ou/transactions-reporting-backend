package adria.pfa.adriaReporting.repository;

import adria.pfa.adriaReporting.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
