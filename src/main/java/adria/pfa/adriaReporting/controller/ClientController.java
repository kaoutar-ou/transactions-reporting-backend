package adria.pfa.adriaReporting.controller;

import adria.pfa.adriaReporting.dao.TransactionDao;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.service.ClientService;
import adria.pfa.adriaReporting.service.GeneratReportPDF;
import adria.pfa.adriaReporting.service.ReportService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.TableRowSorter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3700)
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private GeneratReportPDF generatReportPDF;

    @Autowired
    private ReportService reportService;

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

    @PostMapping("/transactions/search/{id_client}")
    public ResponseEntity<List<Transaction>> searchTransactionsBy(@PathVariable Long id_client, @RequestBody TransactionDao transaction) {
        List<Transaction> transactions = clientService.searchTransactionsBy(id_client, transaction);
        return  ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/transactions/{id_client}/itextpdf")
    public ResponseEntity<InputStreamResource> getReportItextPDF(@PathVariable Long id_client)  {
        List<Transaction> transactions = clientService.listTransactions(id_client);
        ByteArrayInputStream data = generatReportPDF.tranactionPDFReport(transactions);
        HttpHeaders headers= new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=report.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(data));
    }

    @GetMapping("/pdf/{id_client}/transaction/{id_transaction}")
    public ResponseEntity<?> getPdfReport(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id_client, @PathVariable Long id_transaction) throws IOException {

        byte[] bytes = reportService.getPdfReport(id_client, id_transaction);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }

    @GetMapping("/pdf/{id_client}")
    public ResponseEntity<?> getPdfReportAll(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id_client) throws IOException {

        byte[] bytes = reportService.getPdfReportAll(id_client);

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentLength(bytes.length);
//        headers.add("Content-Disposition", "inline;filename=report.pdf");

        return ResponseEntity.ok()
//                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport.pdf")
                .body(bytes);

    }
}
