package adria.pfa.adriaReporting.repository;

import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.Beneficiaire;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    Transaction findByReference(String reference);
    List<Transaction> findAllByDateCreation(Date date);
    List<Transaction> findAllByClient(Client client);
    List<Transaction> findAllByBeneficiaire(Beneficiaire beneficiaire);
    List<Transaction> findAllByTypeTransaction(TypeTransaction typeTransaction);
    List<Transaction> findAllByClientAndBeneficiaire(Client client, Beneficiaire beneficiaire);
    List<Transaction> findAllByClientAndTypeTransaction(Client client, TypeTransaction typeTransaction);
    List<Transaction> findAllByClientAndReference(Client client, String reference);
    List<Transaction> findAllByClientAndTypeProduit(Client client, TypeProduit typeProduit);
    List<Transaction> findAllByClientAndDateCreation(Client client, Date dateCreation);

//    List<Transaction> searchAllByBeneficiaireOrMontant(Beneficiaire b, double m);
}
