package adria.pfa.adriaReporting.service;

import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.Beneficiaire;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.repository.BeneficiaireRepository;
import adria.pfa.adriaReporting.repository.ClientRepository;
import adria.pfa.adriaReporting.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.Reference;
import java.util.Date;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BeneficiaireRepository beneficiaireRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> listTransactions(Long client_id) {
        Client client = clientRepository.findById(client_id).get();
        List<Transaction> transactions = transactionRepository.findAllByClient(client);
        return transactions;
    }

    public List<Transaction> rechercheTransactionsParBeneficiaire(Long client_id, Long beneficiaire_id) {
        Client client = clientRepository.findById(client_id).get();
        Beneficiaire beneficiaire = beneficiaireRepository.findById(beneficiaire_id).get();
        List<Transaction> transactions = transactionRepository.findAllByClientAndBeneficiaire(client, beneficiaire);
        return transactions;
    }

    public List<Transaction> rechercheTransactionsParTypeTransaction(Long client_id, TypeTransaction typeTransaction) {
        Client client = clientRepository.findById(client_id).get();
        List<Transaction> transactions = transactionRepository.findAllByClientAndTypeTransaction(client, typeTransaction);
        return transactions;
    }

    public List<Transaction> rechercheTransactionsParTypeProduit(Long client_id, TypeProduit typeProduit) {
        Client client = clientRepository.findById(client_id).get();
        List<Transaction> transactions = transactionRepository.findAllByClientAndTypeProduit(client, typeProduit);
        return transactions;
    }

    public List<Transaction> rechercheTransactionsParDateCreation(Long client_id, Date dateCreation) {
        Client client = clientRepository.findById(client_id).get();
        List<Transaction> transactions = transactionRepository.findAllByClientAndDateCreation(client, dateCreation);
        return transactions;
    }

    public List<Transaction> rechercheTransactionsParReference(Long client_id, String reference) {
        Client client = clientRepository.findById(client_id).get();
        List<Transaction> transactions = transactionRepository.findAllByClientAndReference(client, reference);
        return transactions;
    }

//    public List<Transaction> recherche() {
//
//    }

}
