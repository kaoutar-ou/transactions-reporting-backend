package adria.pfa.adriaReporting.utils.faker;

import adria.pfa.adriaReporting.model.Banque;
import adria.pfa.adriaReporting.model.Beneficiaire;
import adria.pfa.adriaReporting.repository.BeneficiaireRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeneficiaireFaker {

    private BeneficiaireRepository beneficiaireRepository;

    @Autowired
    public void setBeneficiaireRepository(BeneficiaireRepository beneficiaireRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
    }

    public Beneficiaire createBeneficiaire(Banque banque) {
        Faker faker = new Faker();
        Beneficiaire beneficiaire = new Beneficiaire();
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        beneficiaire.setNom(lastName);
        beneficiaire.setPrenom(firstName);
        beneficiaire.setUsername(faker.name().username());
        beneficiaire.setEmail(faker.regexify("[a-z]{3}") + System.currentTimeMillis() + faker.regexify("[@]{1}(gmail|hotmail|outlook)") + ".com");
        beneficiaire.setPassword(faker.regexify("[#$%&0-9=A-Z_a-z~]{8,15}"));
        beneficiaire.setType("beneficiaire");

        beneficiaire.setNomComplet(lastName + " " + firstName);
        beneficiaire.setAddress(faker.address().fullAddress());

        Long account = 0L;
        do {
            account = Long.parseLong(faker.regexify("[0-9]{12}"));
        } while (beneficiaireRepository.findByAccount(account).isPresent());

        beneficiaire.setAccount(account);

        beneficiaire.setBanque(banque);
        return beneficiaireRepository.save(beneficiaire);
    }
}
