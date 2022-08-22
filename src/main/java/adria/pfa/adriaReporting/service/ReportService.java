package adria.pfa.adriaReporting.service;

import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    TransactionService transactionService;

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] getPdfReport(Long id_client, Long id_transaction) {

        Map<String, Object> data = new HashMap<>();

        Transaction transaction = transactionService.getTransactionByID(id_transaction);
        data.put("transaction", transaction);

        data.put("date", LocalDate.now());

        Context context = new Context();
        context.setVariables(data);

        String templateName = "report-template";
//        String pdfFileName = "report.pdf";
        String reportHtmlContent = templateEngine.process(templateName, context);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ConverterProperties converterProperties = new ConverterProperties();

//        converterProperties.setBaseUri("http://localhost:8080");

//        HtmlConverter.convertToPdf(reportHtmlContent, byteArrayOutputStream, converterProperties);

        HtmlConverter.convertToPdf(reportHtmlContent, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getPdfReportAll(Long id_client) {
        Map<String, Object> data = new HashMap<>();
        List<Transaction> transactions = transactionService.listTransactions(id_client);
        data.put("transactions", transactions);

        Client client = transactionService.getClientById(id_client);
        data.put("client", client);

        data.put("date", LocalDate.now());

        Context context = new Context();
        context.setVariables(data);

        String templateName = "report-all-template";
        String pdfFileName = "report.pdf";
        String reportHtmlContent = templateEngine.process(templateName, context);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ConverterProperties converterProperties = new ConverterProperties();

//        converterProperties.setBaseUri("http://localhost:8080");
//        FileOutputStream fileOutputStream = new FileOutputStream(templateName);

        HtmlConverter.convertToPdf(reportHtmlContent, byteArrayOutputStream, converterProperties);


        HtmlConverter.convertToPdf(reportHtmlContent, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getReportPDF(Long idClient, String ref) throws FileNotFoundException, JRException {
        List<Transaction> transactions = transactionService.rechercheTransactionsParReference(idClient,ref);
        JRBeanCollectionDataSource beanCollectionDataSource =new JRBeanCollectionDataSource(transactions);
        //JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/jasperReport.jrxml"));
        File file = ResourceUtils.getFile("classpath:jasperReport.jrxml");
        JasperReport compileReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String,Object> parameters =new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport,parameters,beanCollectionDataSource);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        JasperExportManager.exportReportToPdfFile(report,"report.pdf");
        return data;
    }

    public void downloadReportPDF(Long idClient, String ref, HttpServletResponse response) throws IOException, JRException {
        List<Transaction> transactions = transactionService.rechercheTransactionsParReference(idClient,ref);
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
}
