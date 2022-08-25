package adria.pfa.adriaReporting.service;

import adria.pfa.adriaReporting.model.Beneficiaire;
import adria.pfa.adriaReporting.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BeneficiaireService {

    @Autowired
    private TransactionService transactionService;

    public Set<Beneficiaire> listBeneficiairesByClientId(Long idClient) {
        List<Transaction> transactions = transactionService.listTransactions(idClient);
        Set<Beneficiaire> beneficiaires = new HashSet<>();
        for (Transaction transaction: transactions
        ) {
            beneficiaires.add(transaction.getBeneficiaire());
        }
        return  beneficiaires;
    }
}
