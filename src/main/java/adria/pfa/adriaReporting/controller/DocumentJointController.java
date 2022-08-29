package adria.pfa.adriaReporting.controller;

import adria.pfa.adriaReporting.dao.DocumentJointDto;
import adria.pfa.adriaReporting.dao.MessageResponse;
import adria.pfa.adriaReporting.model.DocumentJoint;
import adria.pfa.adriaReporting.service.DocumentJointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*", maxAge = 3700)
public class DocumentJointController {

    DocumentJointService documentJointService;

    @Autowired
    public void setDocumentJointService(DocumentJointService documentJointService) {
        this.documentJointService = documentJointService;
    }

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> saveDocumentJoint(@RequestBody MultipartFile document,
                                                             @RequestParam("idTransaction") Long idTransaction) throws IOException {
        System.out.println("upload");
        String message = "";
        try {
            documentJointService.saveDocumentJoint(document, idTransaction);
            message = "Document uploaded successfully : '" + document.getOriginalFilename() + "'.";
            return ResponseEntity.ok().body(new MessageResponse(message));
        }
        catch (Exception e) {
            message = "Document  upload failed : \"" + document.getOriginalFilename() + "\".";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @Transactional
    @GetMapping("/{idTransaction}")
    public ResponseEntity<List<DocumentJointDto>> getListDocumentsJoints(@PathVariable Long idTransaction) {
        List<DocumentJointDto> documentJointDtos = documentJointService.getAllDocumentJointsByTransaction(idTransaction).map(document -> {
            String documentDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/documents/download/")
                    .path(document.getId().toString())
                    .toUriString();
            return new DocumentJointDto(
                    document.getType(),
                    document.getName(),
                    documentDownloadUrl,
                    document.getData().length);
        }).toList();
        return ResponseEntity.ok().body(documentJointDtos);
    }

    @GetMapping("/download/{idDocument}")
    public ResponseEntity<byte[]> getDocumentJoint(@PathVariable Long idDocument) {
        DocumentJoint documentJoint = documentJointService.getDocumentJoint(idDocument);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentJoint.getName() + "\"")
                .body(documentJoint.getData());

//        return ResponseEntity.ok()
//                .contentType(MediaType.valueOf(documentJoint.getType()))
//                .body(documentJoint.getData());
    }

}
