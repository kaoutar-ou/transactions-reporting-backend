package adria.pfa.adriaReporting.utils.faker;

import adria.pfa.adriaReporting.model.Banque;
import adria.pfa.adriaReporting.repository.BanqueRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BanqueFaker {

    private BanqueRepository banqueRepository;

    @Autowired
    public void setBanqueRepository(BanqueRepository banqueRepository) {
        this.banqueRepository = banqueRepository;
    }

    public Banque createBanque() {
        Faker faker = new Faker();
        Banque banque = new Banque();
        banque.setNom(faker.regexify("[A-Z]{4}") + " Bank");
        String codeBIC = "";
        do {
            codeBIC = faker.regexify("[A-Z0-9]{4}[A-Z]{2}[A-Z0-9]{2}");
        } while (banqueRepository.findByCodeBIC(codeBIC).isPresent());
        banque.setCodeBIC(codeBIC);
        banque.setAddress(faker.address().fullAddress());
//        System.out.println(banque.toString());
        return banqueRepository.save(banque);
    }

}
