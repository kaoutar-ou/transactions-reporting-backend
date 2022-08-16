package adria.pfa.adriaReporting.controller;

import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.table.TableRowSorter;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/transactions/{id_client}")
    public ResponseEntity<List<Transaction>> listTransactions(@PathVariable Long id_client) {
        List<Transaction> transactions = clientService.listTransactions(id_client);
        return  ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/transactions/{id_client}/beneficiaire/{id_beneficiaire}")
    public ResponseEntity<List<Transaction>> listTransactionsParBeneficiaire(@PathVariable Long id_client, @PathVariable Long id_beneficiaire) {
        List<Transaction> transactions = clientService.rechercheTransactionsParBeneficiaire(id_client, id_beneficiaire);
        return ResponseEntity.ok().body(transactions);
    }
}
