package adria.pfa.adriaReporting.service;

import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    ClientService clientService;

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] getPdfReport(Long id_client, Long id_transaction) {

        Map<String, Object> data = new HashMap<>();
        List<Transaction> transactions = clientService.listTransactions(id_client);
        data.put("transactions", transactions);

        Transaction transaction = clientService.getTransactionByID(id_transaction);
        data.put("transaction", transaction);

        data.put("date", LocalDate.now());

        Context context = new Context();
        context.setVariables(data);

        String templateName = "report-template";
        String pdfFileName = "report.pdf";
        String reportHtmlContent = templateEngine.process(templateName, context);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ConverterProperties converterProperties = new ConverterProperties();

//        converterProperties.setBaseUri("http://localhost:8080");

        HtmlConverter.convertToPdf(reportHtmlContent, byteArrayOutputStream, converterProperties);

//        FileOutputStream fileOutputStream = new FileOutputStream(templateName);

        HtmlConverter.convertToPdf(reportHtmlContent, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getPdfReportAll(Long id_client) {
        Map<String, Object> data = new HashMap<>();
        List<Transaction> transactions = clientService.listTransactions(id_client);
        data.put("transactions", transactions);

        Client client = clientService.getClientById(id_client);
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

}
