package adria.pfa.adriaReporting;

import adria.pfa.adriaReporting.enumeration.TypePayement;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.*;
import adria.pfa.adriaReporting.repository.*;
import adria.pfa.adriaReporting.service.CodificationService;
import adria.pfa.adriaReporting.service.TransactionService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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


    public Banque banqueFaker() {
        Faker faker = new Faker();
        Banque banque = new Banque();
        banque.setNom(faker.regexify("[A-Z]{4}") + " Bank");
        banque.setCodeBIC(faker.regexify("[A-Z0-9]{4}[A-Z]{2}[A-Z0-9]{2}"));
        banque.setAddress(faker.address().fullAddress());
//        System.out.println(banque.toString());
        return banqueRepository.save(banque);
    }

    public Client clientFaker(Banque banque) {
        Faker faker = new Faker();
        Client client = new Client();
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        client.setNom(lastName);
        client.setPrenom(firstName);
        client.setUsername(faker.name().username());
        client.setEmail(faker.regexify("[a-z]{1}[a-z0-9]{5,10}[@]{1}(gmail|hotmail|outlook)") + ".com");
        client.setPassword(faker.regexify("[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]{8,15}"));
        client.setType("client");

        client.setNomComplet(lastName + " " + firstName);
        client.setAddress(faker.address().fullAddress());
        client.setAccount(Long.parseLong(faker.business().creditCardNumber().replace("-","")));
        client.setBanque(banque);
//        System.out.println(client.toString());
        return clientRepository.save(client);
    }

    public Beneficiaire beneficiaireFaker(Banque banque) {
        Faker faker = new Faker();
        Beneficiaire beneficiaire = new Beneficiaire();
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        beneficiaire.setNom(lastName);
        beneficiaire.setPrenom(firstName);
        beneficiaire.setUsername(faker.name().username());
        beneficiaire.setEmail(faker.regexify("[a-z]{1}[a-z0-9]{5,10}[@]{1}(gmail|hotmail|outlook)") + ".com");
        beneficiaire.setPassword(faker.regexify("[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]{8,15}"));
        beneficiaire.setType("beneficiaire");

        beneficiaire.setNomComplet(lastName + " " + firstName);
        beneficiaire.setAddress(faker.address().fullAddress());
        beneficiaire.setAccount(Long.parseLong(faker.business().creditCardNumber().replace("-","")));
        beneficiaire.setBanque(banque);
//        System.out.println(beneficiaire.toString());
        return beneficiaireRepository.save(beneficiaire);
    }

    public Transaction transactionFaker(Client client, Beneficiaire beneficiaire) {
        Faker faker = new Faker();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Random random = new Random();

        // TODO .. put this in a function
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 8, 30);
        long randomDate = ThreadLocalRandom.current().nextLong(startDate.toEpochDay(), endDate.toEpochDay());
        LocalDate localDateCreation = LocalDate.ofEpochDay(randomDate);
        LocalTime localTimeCreation = LocalTime.now();
        Timestamp dateCreation = Timestamp.valueOf(LocalDateTime.of(localDateCreation, localTimeCreation));

        LocalDate localDateExpiration = LocalDate.ofEpochDay(randomDate + random.nextInt(20,90));
        LocalTime localTimeExpiration = LocalTime.now();
        Timestamp dateExpiration = Timestamp.valueOf(LocalDateTime.of(localDateExpiration, localTimeExpiration));

        List<TypeTransaction> typeTransactions = Arrays.asList(TypeTransaction.AMENDEMENT, TypeTransaction.EMISSION,
                TypeTransaction.MESSAGE, TypeTransaction.MODIFICATION,
                TypeTransaction.UTILISATION_A_ECHEANCE, TypeTransaction.UTILISATION_A_VUE);
        List<TypePayement> typePayements = Arrays.asList(TypePayement.PAYEMENT_CONTRE, TypePayement.PAYEMENT_DIFFERE, TypePayement.MIXTE);
        List<TypeProduit> typeProduits = Arrays.asList(TypeProduit.EXPORT, TypeProduit.IMPORT);
        Transaction transaction = new Transaction();
        transaction.setReference("RF" + calendar.get(Calendar.YEAR) + faker.regexify("[0-9]{5}"));
        transaction.setTypeTransaction(typeTransactions.get(random.nextInt(typeTransactions.size())));
        transaction.setTypePayement(typePayements.get(random.nextInt(typePayements.size())));
        transaction.setTypeProduit(typeProduits.get(random.nextInt(typeProduits.size())));
        transaction.setDateCreation(dateCreation);
        transaction.setDateExpiration(dateExpiration);
        transaction.setClient(client);
        transaction.setBeneficiaire(beneficiaire);
        transaction.setMontant(random.nextDouble(1000, 1000000));
//        System.out.println(transaction.toString());
        return transactionRepository.save(transaction);
    }
    @Bean
    CommandLineRunner run(TransactionService transactionService) {
        return args -> {
//            test();


//            LocalDate startDate = LocalDate.of(2020, 1, 1);
//            LocalDate endDate = LocalDate.of(2022, 8, 30);
//            long randomDate = ThreadLocalRandom.current().longs(startDate.toEpochDay(), endDate.toEpochDay()).findAny().getAsLong();
//
//            System.out.println(randomDate);
//            System.out.println(LocalDate.ofEpochDay(randomDate));
//            System.out.println(LocalDate.ofEpochDay(randomDate + 30));
//            System.out.println(new Timestamp(randomDate));
////            System.out.println(Timestamp.valueOf(LocalDateTime.of(LocalDate.ofEpochDay(randomDate).getYear(), LocalDate.ofEpochDay(randomDate).getMonth(), LocalDate.ofEpochDay(randomDate).getDayOfMonth(), )));
//            System.out.println(Timestamp.valueOf(LocalDateTime.of(LocalDate.ofEpochDay(randomDate), LocalTime.now())));

//            Faker faker = new Faker();
//            System.out.println(faker.regexify("[a-z]{1}[a-z0-9]{5,10}[@]{1}(gmail|hotmail|outlook)"));
//            System.out.println(faker.regexify("[a-z]{1}[a-z0-9]{5,10}[@]{1}(gmail|hotmail|outlook)"));
//            System.out.println(faker.regexify("[a-z]{1}[a-z0-9]{5,10}[@]{1}(gmail|hotmail|outlook)"));
//            System.out.println(faker.regexify("[a-z]{1}[a-z0-9]{5,10}[@]{1}(gmail|hotmail|outlook)"));
//            System.out.println(faker.regexify("[a-z]{1}[a-z0-9]{5,10}[@]{1}(gmail|hotmail|outlook)"));
//            System.out.println(Long.parseLong(faker.business().creditCardNumber().replace("-","")));
////            Banque banque1 = banqueRepository.save(new Banque(null, "Banque 1", "codeBIC1", "Adresse 1", new ArrayList<Client>(), new ArrayList<Beneficiaire>()));
////            Banque banque2 = banqueRepository.save(new Banque(null, "Banque 2", "codeBIC2", "Adresse 2", new ArrayList<Client>(), new ArrayList<Beneficiaire>()));
//
//            Banque banque1 = banqueRepository.findById(1L).get();
//            Banque banque2 = banqueRepository.findById(1L).get();
//
//            Random random = new Random();
////            Client client1 = new Client("Nom Client 1", "Adresse 1", 123456L, banque1, new ArrayList<Transaction>());
////            client1.setNom("nom1");
////            client1.setPrenom("prenom1");
////            client1.setUsername("username1");
////            client1.setEmail("email1");
////            client1.setPassword("password1");
////            client1.setType("type1");
////            clientRepository.save(client1);
//
//            Client client1 = clientRepository.findById(56L).get();
//
////            Beneficiaire beneficiaire1 = new Beneficiaire("Nom Bénéficiaire 1", "Adresse 1", "status1", 4787894756L, banque1, new ArrayList<Transaction>());
////            beneficiaire1.setNom("bnom1");
////            beneficiaire1.setPrenom("bprenom1");
////            beneficiaire1.setUsername("busername1");
////            beneficiaire1.setEmail("bemail1");
////            beneficiaire1.setPassword("bpassword1");
////            beneficiaire1.setType("btype1");
////            beneficiaireRepository.save(beneficiaire1);
//
//            Beneficiaire beneficiaire1 = beneficiaireRepository.findById(4L).get();
//            Beneficiaire beneficiaire2 = beneficiaireRepository.findById(5L).get();
//            Beneficiaire beneficiaire3 = beneficiaireRepository.findById(6L).get();
//
////            Beneficiaire beneficiaire2 = new Beneficiaire("Nom Bénéficiaire 2", "Adresse 2", "status2", 123744872567L, banque2, new ArrayList<Transaction>());
////            beneficiaire2.setNom("bnom2");
////            beneficiaire2.setPrenom("bprenom2");
////            beneficiaire2.setUsername("busername2");
////            beneficiaire2.setEmail("bemail2");
////            beneficiaire2.setPassword("bpassword2");
////            beneficiaire2.setType("btype2");
////            beneficiaireRepository.save(beneficiaire2);
////            Beneficiaire beneficiaire3 = new Beneficiaire("Nom Bénéficiaire 3", "Adresse 3", "status3", 8157710648L, banque2, new ArrayList<Transaction>());
////            beneficiaire3.setNom("bnom3");
////            beneficiaire3.setPrenom("bprenom3");
////            beneficiaire3.setUsername("busername3");
////            beneficiaire3.setEmail("bemail3");
////            beneficiaire3.setPassword("bpassword3");
////            beneficiaire3.setType("btype3");
////            beneficiaireRepository.save(beneficiaire3);
//
////            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.EMISSION.toString(),TypeTransaction.EMISSION.getValue()));
////            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.MODIFICATION.toString(),TypeTransaction.MODIFICATION.getValue()));
////            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.AMENDEMENT.toString(),TypeTransaction.AMENDEMENT.getValue()));
////            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.UTILISATION_A_VUE.toString(),TypeTransaction.UTILISATION_A_VUE.getValue()));
////            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.UTILISATION_A_ECHEANCE.toString(),TypeTransaction.UTILISATION_A_ECHEANCE.getValue()));
////            codificationRepository.save(new Codification(null,"TypeTransaction",TypeTransaction.MESSAGE.toString(),TypeTransaction.MESSAGE.getValue()));
////            codificationRepository.save(new Codification(null,"TypeProduit",TypeProduit.EXPORT.toString(),TypeProduit.EXPORT.getValue()));
////            codificationRepository.save(new Codification(null,"TypeProduit",TypeProduit.IMPORT.toString(),TypeProduit.IMPORT.getValue()));
//
//            codificationService.fillCodificationTable();
//
//            for (int i=0 ; i<2 ; i++) {
//                transactionService.creatTransaction( TypeTransaction.EMISSION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT, new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire1);
//                transactionService.creatTransaction( TypeTransaction.AMENDEMENT, TypePayement.PAYEMENT_DIFFERE, TypeProduit.IMPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire2);
//                transactionService.creatTransaction( TypeTransaction.MESSAGE, TypePayement.PAYEMENT_CONTRE, TypeProduit.IMPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire1);
//                transactionService.creatTransaction( TypeTransaction.MODIFICATION, TypePayement.PAYEMENT_DIFFERE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire1);
//                transactionService.creatTransaction( TypeTransaction.EMISSION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire2);
//                transactionService.creatTransaction( TypeTransaction.UTILISATION_A_ECHEANCE, TypePayement.PAYEMENT_DIFFERE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
//                transactionService.creatTransaction( TypeTransaction.UTILISATION_A_VUE, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
//                transactionService.creatTransaction( TypeTransaction.MESSAGE, TypePayement.PAYEMENT_DIFFERE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
//                transactionService.creatTransaction( TypeTransaction.MODIFICATION, TypePayement.PAYEMENT_CONTRE, TypeProduit.EXPORT,  new Timestamp(Calendar.getInstance().getTime().getTime()), random.nextDouble(0, 900000), new ArrayList<DocumentJoint>(), client1, beneficiaire3);
//            }
        };
    }

}
