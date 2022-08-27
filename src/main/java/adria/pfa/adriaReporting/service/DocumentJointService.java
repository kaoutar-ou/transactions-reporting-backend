package adria.pfa.adriaReporting.service;

import adria.pfa.adriaReporting.model.DocumentJoint;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.repository.DocumentJointRepository;
import adria.pfa.adriaReporting.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class DocumentJointService {
    DocumentJointRepository documentJointRepository;
    TransactionService transactionService;

    @Autowired
    public void setDocumentJointRepository(DocumentJointRepository documentJointRepository) {
        this.documentJointRepository = documentJointRepository;
    }

    @Autowired
    public void setTransactionRepository(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public DocumentJoint saveDocumentJoint(MultipartFile document, Long idTransaction) throws IOException {
        Transaction transaction = transactionService.getTransactionByID(idTransaction);
        String documentName = StringUtils.cleanPath(Objects.requireNonNull(document.getOriginalFilename()));
        DocumentJoint documentJoint = new DocumentJoint();
        documentJoint.setName(documentName);
        documentJoint.setType(document.getContentType());
        documentJoint.setData(document.getBytes());
        documentJoint.setTransaction(transaction);
        return documentJointRepository.save(documentJoint);
    }

    public DocumentJoint getDocumentJoint(Long id) {
        return documentJointRepository.findById(id).get();
    }

    public Stream<DocumentJoint> getAllDocumentJointsByTransaction(Long idTransaction) {
        Transaction transaction = transactionService.getTransactionByID(idTransaction);
        return documentJointRepository.findAllByTransaction(transaction);
    }
}
