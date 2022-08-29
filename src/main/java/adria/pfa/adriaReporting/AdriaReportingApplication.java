package adria.pfa.adriaReporting;

import adria.pfa.adriaReporting.enumeration.TypePayement;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.*;
import adria.pfa.adriaReporting.repository.*;
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

    @Autowired
    private BanqueRepository banqueRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BeneficiaireRepository beneficiaireRepository;
    @Autowired
    private CodificationRepository codificationRepository;


    @Autowired
    private TransactionRepository transactionRepository;

    @Bean
    CommandLineRunner run(TransactionService transactionService) {
        return args -> {
            Banque banque1 = banqueRepository.save(new Banque(null, "banque1", "codeBIC1", "address1", new ArrayList<Client>(), new ArrayList<Beneficiaire>()));
            Banque banque2 = banqueRepository.save(new Banque(null, "banque2", "codeBIC2", "address2", new ArrayList<Client>(), new ArrayList<Beneficiaire>()));

            Random random = new Random();
            Client client1 = new Client("nomComplet1", "address1", 123456L, banque1, new ArrayList<Transaction>());
            client1.setNom("nom1");
            client1.setPrenom("prenom1");
            client1.setUsername("username1");
            client1.setEmail("email1");
            client1.setPassword("password1");
            client1.setType("type1");
            clientRepository.save(client1);

            Beneficiaire beneficiaire1 = new Beneficiaire("nomComplet1", "address1", "status1", 478123456L, banque1, new ArrayList<Transaction>());
            beneficiaire1.setNom("bnom1");
            beneficiaire1.setPrenom("bprenom1");
            beneficiaire1.setUsername("busername1");
            beneficiaire1.setEmail("bemail1");
            beneficiaire1.setPassword("bpassword1");
            beneficiaire1.setType("btype1");
            beneficiaireRepository.save(beneficiaire1);
            Beneficiaire beneficiaire2 = new Beneficiaire("nomComplet2", "address2", "status2", 123444567L, banque2, new ArrayList<Transaction>());
            beneficiaire2.setNom("bnom2");
            beneficiaire2.setPrenom("bprenom2");
            beneficiaire2.setUsername("busername2");
            beneficiaire2.setEmail("bemail2");
            beneficiaire2.setPassword("bpassword2");
            beneficiaire2.setType("btype2");
            beneficiaireRepository.save(beneficiaire2);
            Beneficiaire beneficiaire3 = new Beneficiaire("nomComplet3", "address3", "status3", 815355648L, banque2, new ArrayList<Transaction>());
            beneficiaire3.setNom("bnom3");
            beneficiaire3.setPrenom("bprenom3");
            beneficiaire3.setUsername("busername3");
            beneficiaire3.setEmail("bemail3");
            beneficiaire3.setPassword("bpassword3");
            beneficiaire3.setType("btype3");
            beneficiaireRepository.save(beneficiaire3);
            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.EMISSION.toString(),TypeTransaction.EMISSION.getValue()));
            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.MODIFICATION.toString(),TypeTransaction.MODIFICATION.getValue()));
            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.AMENDEMENT.toString(),TypeTransaction.AMENDEMENT.getValue()));
            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.UTILISATION_A_VUE.toString(),TypeTransaction.UTILISATION_A_VUE.getValue()));
            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.UTILISATION_A_ECHEANCE.toString(),TypeTransaction.UTILISATION_A_ECHEANCE.getValue()));
            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.MESSAGE.toString(),TypeTransaction.MESSAGE.getValue()));
            codificationRepository.save(new Codification(null,"TypeProduit",TypeProduit.EXPORT.toString(),TypeProduit.EXPORT.getValue()));
            codificationRepository.save(new Codification(null,"TypeProduit",TypeProduit.IMPORT.toString(),TypeProduit.IMPORT.getValue()));
            for (int i=0 ; i<15 ; i++) {
                transactionService.creatTransaction( TypeTransaction.EMISSION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT, new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire1);
                transactionService.creatTransaction( TypeTransaction.AMENDEMENT, TypePayement.PAYEMENT_DIFFERE, TypeProduit.IMPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire2);
                transactionService.creatTransaction( TypeTransaction.MESSAGE, TypePayement.PAYEMENT_CONTRE, TypeProduit.IMPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire1);
                transactionService.creatTransaction( TypeTransaction.MODIFICATION, TypePayement.PAYEMENT_DIFFERE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire1);
                transactionService.creatTransaction( TypeTransaction.EMISSION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire2);
                transactionService.creatTransaction( TypeTransaction.UTILISATION_A_ECHEANCE, TypePayement.PAYEMENT_DIFFERE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
                transactionService.creatTransaction( TypeTransaction.UTILISATION_A_VUE, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
                transactionService.creatTransaction( TypeTransaction.MESSAGE, TypePayement.PAYEMENT_DIFFERE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
                transactionService.creatTransaction( TypeTransaction.MODIFICATION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
            }
        };
    }
}
