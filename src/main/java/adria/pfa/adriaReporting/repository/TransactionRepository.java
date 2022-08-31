package adria.pfa.adriaReporting.repository;

import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.Beneficiaire;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByDateCreation(Date date);
    List<Transaction> findAllByClient(Client client);
    List<Transaction> findAllByBeneficiaire(Beneficiaire beneficiaire);
    List<Transaction> findAllByTypeTransaction(TypeTransaction typeTransaction);
    List<Transaction> findAllByClientAndBeneficiaire(Client client, Beneficiaire beneficiaire);
    List<Transaction> findAllByClientAndTypeTransaction(Client client, TypeTransaction typeTransaction);
    List<Transaction> findAllByClientAndReference(Client client, String reference);
    List<Transaction> findAllByClientAndTypeProduit(Client client, TypeProduit typeProduit);
    List<Transaction> findAllByClientAndDateCreation(Client client, Date dateCreation);
    public Page<Transaction> findByClient(Client client, Pageable pageable);

    Optional<Transaction> findByReference(String reference);
}
