package adria.pfa.adriaReporting.service;

import adria.pfa.adriaReporting.model.Transaction;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.fonts.otf.TableHeader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class GeneratReportPDF {
    public  ByteArrayInputStream tranactionPDFReport(List<Transaction> transactions){
        Document document = new Document();
        ByteArrayOutputStream out =new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document,out);
            document.setPageSize(PageSize.A4);
            document.open();
            float columnWidth []= {280f,280f};
            PdfPTable tableHeader = new PdfPTable(2);
            PdfPCell iconCell = new PdfPCell(Image.getInstance("C:\\Users\\kaout\\Downloads\\adriaReporting\\adria-logo.png"));

            tableHeader.addCell(iconCell);
            PdfPCell titleCell = new PdfPCell(new Phrase("Adria Report"));
            tableHeader.addCell(titleCell);
            document.add(tableHeader);

            document.add(Chunk.NEWLINE);
            PdfPTable table =new PdfPTable(6);
            Stream.of("reference","typeTransaction","typePayement","typeProduit","dateExpiration","dateCreation")
                    .forEach(headerTitle ->{
                        PdfPCell header = new PdfPCell();
                        header.setPhrase(new Phrase(headerTitle));
                        table.addCell(header);
                    });
            for(Transaction transaction: transactions){
                PdfPCell refCell = new PdfPCell(new Phrase(transaction.getReference()));

                table.addCell(refCell);
                PdfPCell typeTransCell = new PdfPCell(new Phrase(transaction.getTypeTransaction().toString()));
                table.addCell(typeTransCell);
                PdfPCell typePayCell = new PdfPCell(new Phrase(transaction.getTypePayement().toString()));
                table.addCell(typePayCell);
                PdfPCell typeProdCell = new PdfPCell(new Phrase(transaction.getTypeProduit().toString()));
                table.addCell(typeProdCell);
                PdfPCell dateExpCell = new PdfPCell(new Phrase(transaction.getDateExpiration().toString()));
                table.addCell(dateExpCell);
                PdfPCell dateCrtCell = new PdfPCell(new Phrase(transaction.getDateCreation().toString()));
                table.addCell(dateCrtCell);

            }
            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}