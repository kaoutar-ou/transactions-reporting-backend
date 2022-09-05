package adria.pfa.adriaReporting;

import adria.pfa.adriaReporting.enumeration.TypePayement;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.*;
import adria.pfa.adriaReporting.repository.*;
import adria.pfa.adriaReporting.service.CodificationService;
import adria.pfa.adriaReporting.service.TransactionService;
import adria.pfa.adriaReporting.utils.factory.PopulateDB;
import adria.pfa.adriaReporting.utils.seeder.CodificationSeeder;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

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

    PopulateDB populateDB;
    @Autowired
    public void setPopulateDB(PopulateDB populateDB) {
        this.populateDB = populateDB;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
//        populateDB.insertDataToDB(2, 1, 3, 50);
    }

}
