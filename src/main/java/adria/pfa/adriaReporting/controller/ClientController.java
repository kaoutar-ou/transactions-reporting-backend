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

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.TableRowSorter;
import java.io.File;
import java.io.IOException;

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
    @GetMapping("/transactions/{id_client}/{ref}/pdf")
    public ResponseEntity<byte[]> getReportPDF(@PathVariable Long id_client,@PathVariable String ref) throws Exception, JRException {
        List<Transaction> transactions = clientService.rechercheTransactionsParReference(id_client,ref);
        JRBeanCollectionDataSource beanCollectionDataSource =new JRBeanCollectionDataSource(transactions);
        //JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/jasperReport.jrxml"));
        File file = ResourceUtils.getFile("classpath:jasperReport.jrxml");
        JasperReport compileReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String,Object> parameters =new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport,parameters,beanCollectionDataSource);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();

        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=report.pdf");
        JasperExportManager.exportReportToPdfFile(report,"C:\\Users\\HP\\Desktop\\Adria-PFA\\report.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }
    @GetMapping("/transactions/{id_client}/{ref}/pdfReport")
    public void getPDF(@PathVariable Long id_client, @PathVariable String ref, HttpServletResponse response) throws JRException, IOException {
        List<Transaction> transactions = clientService.rechercheTransactionsParReference(id_client,ref);

        //InputStream jasperStream = (InputStream) this.getClass().getResourceAsStream("/jasperReport.jrxml");
        File file = ResourceUtils.getFile("classpath:jasperReport.jrxml");
        JasperReport compileReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String,Object> params = new HashMap<>();
        params.put("name","name");
        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(transactions);
        //JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,params,source);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition","inline;filename=report.pdf");
        final ServletOutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,outStream);


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
