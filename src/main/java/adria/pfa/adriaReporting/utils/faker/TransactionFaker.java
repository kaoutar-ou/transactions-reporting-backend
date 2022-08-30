package adria.pfa.adriaReporting.utils.faker;

import adria.pfa.adriaReporting.enumeration.TypePayement;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.Beneficiaire;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.repository.TransactionRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TransactionFaker {

    private TransactionRepository transactionRepository;

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Client client, Beneficiaire beneficiaire) {
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
        String reference = "";
        do {
            reference = "RF" + calendar.get(Calendar.YEAR) + faker.regexify("[0-9]{5}");
        } while (transactionRepository.findByReference(reference).isPresent());
        transaction.setReference(reference);
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
}
