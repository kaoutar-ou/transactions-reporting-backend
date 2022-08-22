package adria.pfa.adriaReporting.controller;

import adria.pfa.adriaReporting.dao.TransactionDao;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.service.TransactionService;

import adria.pfa.adriaReporting.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*", maxAge = 3700)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/{idClient}")
    public ResponseEntity<List<Transaction>> listTransactions(@PathVariable Long idClient) {
        List<Transaction> transactions = transactionService.listTransactions(idClient);
        return  ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/{idClient}/beneficiaire/{idBeneficiaire}")
    public ResponseEntity<List<Transaction>> listTransactionsParBeneficiaire(@PathVariable Long idClient, @PathVariable Long idBeneficiaire) {
        List<Transaction> transactions = transactionService.rechercheTransactionsParBeneficiaire(idClient, idBeneficiaire);
        return ResponseEntity.ok().body(transactions);
    }

    @PostMapping("/search/{idClient}")
    public ResponseEntity<List<Transaction>> searchTransactionsBy(@PathVariable Long idClient, @RequestBody TransactionDao transaction) {
        List<Transaction> transactions = transactionService.searchTransactionsByClientAndCriteria(idClient, transaction);
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