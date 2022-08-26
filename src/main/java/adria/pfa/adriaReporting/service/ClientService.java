package adria.pfa.adriaReporting.service;

import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Client getClientById(Long id) {
        Client client = clientRepository.findById(id).get();
        return client;
    }
}
