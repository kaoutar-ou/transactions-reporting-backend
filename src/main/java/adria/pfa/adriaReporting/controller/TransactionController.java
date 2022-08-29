package adria.pfa.adriaReporting.controller;

import adria.pfa.adriaReporting.dao.TransactionDao;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.service.ClientService;
import adria.pfa.adriaReporting.service.ReportService;
import adria.pfa.adriaReporting.service.TransactionService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*", maxAge = 3700)
public class TransactionController {

    private TransactionService transactionService;

    private ReportService reportService;

    private ClientService clientService;

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<Page<Transaction>> listTransactions(@PathVariable Long idClient,
                                                              @RequestParam(name="page", defaultValue="0") int page,
                                                              @RequestParam(name="size", defaultValue="5") int size) {
        Page<Transaction> pageTransaction = transactionService.transactionsPage(idClient, page, size);
        return  ResponseEntity.ok().body(pageTransaction);
    }

    @GetMapping("/transaction/{idTransaction}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long idTransaction) {
        Transaction transaction = transactionService.getTransactionByID(idTransaction);
        return ResponseEntity.ok().body(transaction);
    }

    @GetMapping("/{idClient}/beneficiaire/{idBeneficiaire}")
    public ResponseEntity<List<Transaction>> listTransactionsParBeneficiaire(@PathVariable Long idClient, @PathVariable Long idBeneficiaire) {
        List<Transaction> transactions = transactionService.rechercheTransactionsParBeneficiaire(idClient, idBeneficiaire);
        return ResponseEntity.ok().body(transactions);
    }

    @PostMapping("/search/{idClient}")
    public ResponseEntity<Page<Transaction>> searchTransactionsByCriteria(@PathVariable Long idClient,
                                                                  @RequestBody(required = false) TransactionDao transaction,
                                                                  @RequestParam(name="page", defaultValue="0") int page,
                                                                  @RequestParam(name="size", defaultValue="5") int size) {
        Page<Transaction> transactions = transactionService.searchTransactionsByClientAndCriteria(idClient, transaction, PageRequest.of(page, size));
        return  ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/{idClient}/report/{ref}")
    public ResponseEntity<byte[]> getReportPDF(@PathVariable Long idClient,@PathVariable String ref) throws Exception, JRException {

        byte[] bytes = reportService.getReportPDF(idClient, ref);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=report.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bytes);
    }
    @GetMapping("/{idClient}/report/{ref}/download")
    public void getPDF(@PathVariable Long idClient, @PathVariable String ref, HttpServletResponse response) throws JRException, IOException {

        reportService.downloadReportPDF(idClient, ref, response);

    }

    @GetMapping("/report/{idClient}/{idTransaction}")
    public ResponseEntity<?> getPdfReport(HttpServletRequest request, HttpServletResponse response, @PathVariable Long idClient, @PathVariable Long idTransaction) throws IOException {

        byte[] bytes = reportService.getPdfReport(idClient, idTransaction);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }

    @GetMapping("/report/{idClient}")
    public ResponseEntity<?> getPdfReportAll(HttpServletRequest request, HttpServletResponse response, @PathVariable Long idClient) throws IOException {

        byte[] bytes = reportService.getPdfReportAll(idClient);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport.pdf")
                .body(bytes);

    }
}
