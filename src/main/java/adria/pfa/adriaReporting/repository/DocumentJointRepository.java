package adria.pfa.adriaReporting.repository;

import adria.pfa.adriaReporting.model.DocumentJoint;
import adria.pfa.adriaReporting.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface DocumentJointRepository extends JpaRepository<DocumentJoint, Long> {
    public Stream<DocumentJoint> findAllByTransaction(Transaction transaction);
}
