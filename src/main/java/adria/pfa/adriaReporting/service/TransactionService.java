package adria.pfa.adriaReporting.service;

import adria.pfa.adriaReporting.dao.TransactionDao;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.Beneficiaire;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.repository.BeneficiaireRepository;
import adria.pfa.adriaReporting.repository.ClientRepository;
import adria.pfa.adriaReporting.repository.SearchTransactionRepository;
import adria.pfa.adriaReporting.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    private ClientRepository clientRepository;

    private BeneficiaireRepository beneficiaireRepository;

    private TransactionRepository transactionRepository;

    private SearchTransactionRepository searchTransactionRepository;
    private static String letterLower = "abcdefghijklmnopqrstuvwxyz";
    private static String letterUpper= letterLower.toUpperCase();


    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setBeneficiaireRepository(BeneficiaireRepository beneficiaireRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
    }

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Autowired
    public void setSearchTransactionRepository(SearchTransactionRepository searchTransactionRepository) {
        this.searchTransactionRepository = searchTransactionRepository;
    }

    public List<Transaction> listTransactions(Long client_id) {
        Client client = clientRepository.findById(client_id).get();
        List<Transaction> transactions = transactionRepository.findAllByClient(client);
        return transactions;
    }

    public Page<Transaction> transactionsPage(Long client_id, int page, int size) {

        Client client = clientRepository.findById(client_id).get();
        Page<Transaction> pageTransaction = transactionRepository.findByClient(client, PageRequest.of(page, size));

        return pageTransaction;
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

    public Page<Transaction> searchTransactionsByClientAndCriteria(Long client_id, TransactionDao transaction, Pageable page) {
        Client client = clientRepository.findById(client_id).get();
        return searchTransactionRepository.searchTransactionsByClientAndCriteria(client, transaction, page);
    }

    public Transaction getTransactionByID(Long id) {
        Transaction transaction = transactionRepository.findById(id).get();
        return transaction;
    }


    public Client getClientById(Long id) {
        Client client = clientRepository.findById(id).get();
        return client;
    }

    public static String genererReferenceWithcurrentTimeMillis() {
        Long dateoftoday =  System.currentTimeMillis();
        String dateoftodayinms = dateoftoday.toString().substring(8);
        LocalDate current_date = LocalDate.now();
        int current_Year = current_date.getYear();

        SecureRandom random = new SecureRandom();
        String ref="";



        for(int i=0;i<2;i++) {
            ref+=letterUpper.charAt(random.nextInt(letterUpper.length()));
        }
        ref+=current_Year;
        ref+=dateoftodayinms;

        return ref;
    }

}
