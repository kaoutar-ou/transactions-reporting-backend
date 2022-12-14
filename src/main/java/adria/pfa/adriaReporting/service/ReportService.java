package adria.pfa.adriaReporting.service;

import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.repository.ClientRepository;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
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

    private TransactionService transactionService;
    private TemplateEngine templateEngine;

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] getPdfReport(Long id_client, Long id_transaction) {

        Map<String, Object> data = new HashMap<>();

        Transaction transaction = transactionService.getTransactionByID(id_transaction);
        data.put("transaction", transaction);

        data.put("date", LocalDate.now());

        Context context = new Context();
        context.setVariables(data);

        String templateName = "report-template";

        String reportHtmlContent = templateEngine.process(templateName, context);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ConverterProperties converterProperties = new ConverterProperties();

        HtmlConverter.convertToPdf(reportHtmlContent, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getReportPDF(Long idClient, String ref) throws FileNotFoundException, JRException {
        List<Transaction> transactions = transactionService.rechercheTransactionsParReference(idClient,ref);
        JRBeanCollectionDataSource beanCollectionDataSource =new JRBeanCollectionDataSource(transactions);
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
        File file = ResourceUtils.getFile("classpath:jasperReport.jrxml");
        JasperReport compileReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String,Object> params = new HashMap<>();
        params.put("name","name");
        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(transactions);
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,params,source);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition","inline;filename=report.pdf");
        final ServletOutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,outStream);
    }
}
