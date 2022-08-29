package adria.pfa.adriaReporting.controller;

import adria.pfa.adriaReporting.model.Codification;
import adria.pfa.adriaReporting.repository.CodificationRepository;
import adria.pfa.adriaReporting.service.CodificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/codifications")
@CrossOrigin(origins = "*", maxAge = 3700)
public class CodificationController {
    @Autowired
    private CodificationRepository codificationRepository;

    private CodificationService codificationService;

    @Autowired
    public void setCodificationService(CodificationService codificationService) {
        this.codificationService = codificationService;
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Codification>> getListCodificationByType(@PathVariable String type) {
//        List codifications = codificationRepository.findAllByType(type);
        List<Codification> codifications = codificationService.getCodeificationsByType(type);
        return ResponseEntity.ok().body(codifications);}
    @GetMapping("/{idCodification}")
    public ResponseEntity<Codification> getCodificationById(@PathVariable Long idCodification) {
//        Codification codification = codificationRepository.findById(idCodification).get();
        Codification codification = codificationService.getCodeificationById(idCodification);
        return ResponseEntity.ok().body(codification);}
}
