package adria.pfa.adriaReporting.utils.faker;

import adria.pfa.adriaReporting.model.Banque;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.repository.ClientRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientFaker {

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Client createClient(Banque banque) {
        Faker faker = new Faker();
        Client client = new Client();
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        client.setNom(lastName);
        client.setPrenom(firstName);
        client.setUsername(faker.name().username());
        client.setEmail(faker.regexify("[a-z]{3}") + System.currentTimeMillis() + faker.regexify("[@]{1}(gmail|hotmail|outlook)") + ".com");
        client.setPassword(faker.regexify("[-!#$%&'*+/0-9=?A-Z^_`a-z{|}~]{8,15}"));
        client.setType("client");

        client.setNomComplet(lastName + " " + firstName);
        client.setAddress(faker.address().fullAddress());

//        client.setAccount(Long.parseLong(faker.business().creditCardNumber().replace("-","")));

        Long account = 0L;
        do {
            account = Long.parseLong(faker.regexify("[0-9]{12}"));
        } while (clientRepository.findByAccount(account).isPresent());

        client.setAccount(account);

        client.setBanque(banque);

//        System.out.println(client.toString());
        return clientRepository.save(client);
    }
}
