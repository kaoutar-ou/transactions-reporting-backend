package adria.pfa.adriaReporting.utils.factory;

import adria.pfa.adriaReporting.model.Banque;
import adria.pfa.adriaReporting.model.Beneficiaire;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.repository.TransactionRepository;
import adria.pfa.adriaReporting.utils.faker.BanqueFaker;
import adria.pfa.adriaReporting.utils.faker.BeneficiaireFaker;
import adria.pfa.adriaReporting.utils.faker.ClientFaker;
import adria.pfa.adriaReporting.utils.faker.TransactionFaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component
public class PopulateDB {

    BanqueFaker banqueFaker;

    @Autowired
    public void setBanqueFaker(BanqueFaker banqueFaker) {
        this.banqueFaker = banqueFaker;
    }

    ClientFaker clientFaker;

    @Autowired
    public void setClientFaker(ClientFaker clientFaker) {
        this.clientFaker = clientFaker;
    }

    BeneficiaireFaker beneficiaireFaker;

    @Autowired
    public void setBeneficiaireFaker(BeneficiaireFaker beneficiaireFaker) {
        this.beneficiaireFaker = beneficiaireFaker;
    }

    TransactionFaker transactionFaker;

    @Autowired
    public void setTransactionFaker(TransactionFaker transactionFaker) {
        this.transactionFaker = transactionFaker;
    }

    TransactionRepository transactionRepository;

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void insertDataToDB() {

        int nombreBanques = 3;
        int nombreClientsParBanque = 1;
        int nombreBeneficiairesParBanque = 3;
        int nombreTransactionsParClient = 50;

        Random random = new Random();

        ArrayList<Banque> banques = new ArrayList<>();
        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Beneficiaire> beneficiaires = new ArrayList<>();

        for (int i = 0; i < nombreBanques; i++) {
            banques.add(banqueFaker.createBanque());
        }

        for (Banque banque : banques) {
            for (int i = 0; i < nombreClientsParBanque; i++) {
                clients.add(clientFaker.createClient(banque));
                beneficiaires.add(beneficiaireFaker.createBeneficiaire(banque));
            }
        }

        for (Banque banque : banques) {
            for (int i = 0; i < nombreBeneficiairesParBanque; i++) {
                beneficiaires.add(beneficiaireFaker.createBeneficiaire(banque));
            }
        }

        for (Client client : clients) {
            for (int i = 0; i < nombreTransactionsParClient; i++) {
                Transaction transaction = transactionFaker.createTransaction(client, beneficiaires.get(random.nextInt(0, beneficiaires.size())));
                transactionRepository.save(transaction);
            }
        }
    }
}
