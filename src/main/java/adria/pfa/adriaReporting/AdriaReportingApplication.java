package adria.pfa.adriaReporting;

import adria.pfa.adriaReporting.enumeration.TypePayement;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.*;
import adria.pfa.adriaReporting.repository.BanqueRepository;
import adria.pfa.adriaReporting.repository.BeneficiaireRepository;
import adria.pfa.adriaReporting.repository.ClientRepository;
import adria.pfa.adriaReporting.repository.TransactionRepository;
import adria.pfa.adriaReporting.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class AdriaReportingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdriaReportingApplication.class, args);
    }

    @Autowired
    private BanqueRepository banqueRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BeneficiaireRepository beneficiaireRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Bean
    CommandLineRunner run(ClientService clientService) {
        return args -> {
            Banque banque1 = banqueRepository.save(new Banque(null, "banque1", "codeBIC1", "address1", new ArrayList<Client>(), new ArrayList<Beneficiaire>()));
            Banque banque2 = banqueRepository.save(new Banque(null, "banque2", "codeBIC2", "address2", new ArrayList<Client>(), new ArrayList<Beneficiaire>()));

            Client client1 = new Client("nomComplet1", "address1", 123456L, banque1, new ArrayList<Transaction>());
            client1.setNom("nom1");
            client1.setPrenom("prenom1");
            client1.setUsername("username1");
            client1.setEmail("email1");
            client1.setPassword("password1");
            client1.setType("type1");
            clientRepository.save(client1);
//			clientRepository.save(new Client( null, "nomComplet2", "address2", 1234567L, banque1, new ArrayList<Transaction>()));

            Beneficiaire beneficiaire1 = new Beneficiaire("nomComplet1", "address1", "status1", 123456L, banque1, new ArrayList<Transaction>());
            beneficiaire1.setNom("bnom1");
            beneficiaire1.setPrenom("bprenom1");
            beneficiaire1.setUsername("busername1");
            beneficiaire1.setEmail("bemail1");
            beneficiaire1.setPassword("bpassword1");
            beneficiaire1.setType("btype1");
            beneficiaireRepository.save(beneficiaire1);
            Beneficiaire beneficiaire2 = new Beneficiaire("nomComplet2", "address2", "status2", 1234567L, banque2, new ArrayList<Transaction>());
            beneficiaire2.setNom("bnom2");
            beneficiaire2.setPrenom("bprenom2");
            beneficiaire2.setUsername("busername2");
            beneficiaire2.setEmail("bemail2");
            beneficiaire2.setPassword("bpassword2");
            beneficiaire2.setType("btype2");
            beneficiaireRepository.save(beneficiaire2);

            transactionRepository.save(new Transaction("reference1", TypeTransaction.EMISSION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT, new Date(), 1452, new ArrayList<DocumentJoint>(), client1, beneficiaire1));
            transactionRepository.save(new Transaction("reference2", TypeTransaction.AMENDEMENT, TypePayement.PAYEMENT_DIFFERE, TypeProduit.IMPORT, new Date(), 14452, new ArrayList<DocumentJoint>(), client1, beneficiaire2));
            transactionRepository.save(new Transaction("reference3", TypeTransaction.MESSAGE, TypePayement.PAYEMENT_CONTRE, TypeProduit.IMPORT, new Date(), 41452, new ArrayList<DocumentJoint>(), client1, beneficiaire1));
            transactionRepository.save(new Transaction("reference4", TypeTransaction.MODIFICATION, TypePayement.PAYEMENT_DIFFERE, TypeProduit.EXPORT, new Date(), 71452, new ArrayList<DocumentJoint>(), client1, beneficiaire1));
            transactionRepository.save(new Transaction("reference5", TypeTransaction.EMISSION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT, new Date(), 17452, new ArrayList<DocumentJoint>(), client1, beneficiaire2));
        };
    }
}
