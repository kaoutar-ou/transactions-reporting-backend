package adria.pfa.adriaReporting;

import adria.pfa.adriaReporting.enumeration.TypePayement;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.*;
import adria.pfa.adriaReporting.repository.*;
import adria.pfa.adriaReporting.service.CodificationService;
import adria.pfa.adriaReporting.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
public class AdriaReportingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdriaReportingApplication.class, args);
    }

    private BanqueRepository banqueRepository;

    @Autowired
    public void setBanqueRepository(BanqueRepository banqueRepository) {
        this.banqueRepository = banqueRepository;
    }

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private CodificationRepository codificationRepository;

    @Autowired
    public void setCodificationRepository(CodificationRepository codificationRepository) {
        this.codificationRepository = codificationRepository;
    }

    private BeneficiaireRepository beneficiaireRepository;

    @Autowired
    public void setBeneficiaireRepository(BeneficiaireRepository beneficiaireRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
    }

    private TransactionRepository transactionRepository;

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    CodificationService codificationService;

    @Autowired
    public void setCodificationService(CodificationService codificationService) {
        this.codificationService = codificationService;
    }


    @Bean
    CommandLineRunner run(TransactionService transactionService) {
        return args -> {
//            Banque banque1 = banqueRepository.save(new Banque(null, "Banque 1", "codeBIC1", "Adresse 1", new ArrayList<Client>(), new ArrayList<Beneficiaire>()));
//            Banque banque2 = banqueRepository.save(new Banque(null, "Banque 2", "codeBIC2", "Adresse 2", new ArrayList<Client>(), new ArrayList<Beneficiaire>()));

            Banque banque1 = banqueRepository.findById(1L).get();
            Banque banque2 = banqueRepository.findById(1L).get();

            Random random = new Random();
//            Client client1 = new Client("Nom Client 1", "Adresse 1", 123456L, banque1, new ArrayList<Transaction>());
//            client1.setNom("nom1");
//            client1.setPrenom("prenom1");
//            client1.setUsername("username1");
//            client1.setEmail("email1");
//            client1.setPassword("password1");
//            client1.setType("type1");
//            clientRepository.save(client1);

            Client client1 = clientRepository.findById(3L).get();

//            Beneficiaire beneficiaire1 = new Beneficiaire("Nom Bénéficiaire 1", "Adresse 1", "status1", 4787894756L, banque1, new ArrayList<Transaction>());
//            beneficiaire1.setNom("bnom1");
//            beneficiaire1.setPrenom("bprenom1");
//            beneficiaire1.setUsername("busername1");
//            beneficiaire1.setEmail("bemail1");
//            beneficiaire1.setPassword("bpassword1");
//            beneficiaire1.setType("btype1");
//            beneficiaireRepository.save(beneficiaire1);

            Beneficiaire beneficiaire1 = beneficiaireRepository.findById(4L).get();
            Beneficiaire beneficiaire2 = beneficiaireRepository.findById(5L).get();
            Beneficiaire beneficiaire3 = beneficiaireRepository.findById(6L).get();

//            Beneficiaire beneficiaire2 = new Beneficiaire("Nom Bénéficiaire 2", "Adresse 2", "status2", 123744872567L, banque2, new ArrayList<Transaction>());
//            beneficiaire2.setNom("bnom2");
//            beneficiaire2.setPrenom("bprenom2");
//            beneficiaire2.setUsername("busername2");
//            beneficiaire2.setEmail("bemail2");
//            beneficiaire2.setPassword("bpassword2");
//            beneficiaire2.setType("btype2");
//            beneficiaireRepository.save(beneficiaire2);
//            Beneficiaire beneficiaire3 = new Beneficiaire("Nom Bénéficiaire 3", "Adresse 3", "status3", 8157710648L, banque2, new ArrayList<Transaction>());
//            beneficiaire3.setNom("bnom3");
//            beneficiaire3.setPrenom("bprenom3");
//            beneficiaire3.setUsername("busername3");
//            beneficiaire3.setEmail("bemail3");
//            beneficiaire3.setPassword("bpassword3");
//            beneficiaire3.setType("btype3");
//            beneficiaireRepository.save(beneficiaire3);

//            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.EMISSION.toString(),TypeTransaction.EMISSION.getValue()));
//            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.MODIFICATION.toString(),TypeTransaction.MODIFICATION.getValue()));
//            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.AMENDEMENT.toString(),TypeTransaction.AMENDEMENT.getValue()));
//            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.UTILISATION_A_VUE.toString(),TypeTransaction.UTILISATION_A_VUE.getValue()));
//            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.UTILISATION_A_ECHEANCE.toString(),TypeTransaction.UTILISATION_A_ECHEANCE.getValue()));
//            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.MESSAGE.toString(),TypeTransaction.MESSAGE.getValue()));
//            codificationRepository.save(new Codification(null,"TypeProduit",TypeProduit.EXPORT.toString(),TypeProduit.EXPORT.getValue()));
//            codificationRepository.save(new Codification(null,"TypeProduit",TypeProduit.IMPORT.toString(),TypeProduit.IMPORT.getValue()));

            codificationService.fillCodificationTable();

            java.util.Date myDate = new java.util.Date("08/08/2022");
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());

            for (int i=0 ; i<2 ; i++) {
                transactionService.creatTransaction( TypeTransaction.EMISSION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT, sqlDate, random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire1);
                transactionService.creatTransaction( TypeTransaction.AMENDEMENT, TypePayement.PAYEMENT_DIFFERE, TypeProduit.IMPORT,  sqlDate, random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire2);
                transactionService.creatTransaction( TypeTransaction.MESSAGE, TypePayement.PAYEMENT_CONTRE, TypeProduit.IMPORT,  sqlDate, random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire1);
                transactionService.creatTransaction( TypeTransaction.MODIFICATION, TypePayement.PAYEMENT_DIFFERE, TypeProduit.EXPORT,  sqlDate, random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire1);
                transactionService.creatTransaction( TypeTransaction.EMISSION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT,  sqlDate, random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire2);
                transactionService.creatTransaction( TypeTransaction.UTILISATION_A_ECHEANCE, TypePayement.PAYEMENT_DIFFERE, TypeProduit.EXPORT,  sqlDate, random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
                transactionService.creatTransaction( TypeTransaction.UTILISATION_A_VUE, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT,  sqlDate, random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
                transactionService.creatTransaction( TypeTransaction.MESSAGE, TypePayement.PAYEMENT_DIFFERE, TypeProduit.EXPORT,  sqlDate, random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
                transactionService.creatTransaction( TypeTransaction.MODIFICATION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT,  sqlDate, random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
            }
        };
    }

}
