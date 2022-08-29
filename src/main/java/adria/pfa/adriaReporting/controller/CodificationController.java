package adria.pfa.adriaReporting.controller;

import adria.pfa.adriaReporting.model.Codification;
import adria.pfa.adriaReporting.repository.CodificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/codification")
@CrossOrigin(origins = "*", maxAge = 3700)
public class CodificationController {
    @Autowired
    private CodificationRepository codificationRepository;
    @GetMapping("/typeCodification/{type}")
    public ResponseEntity<List<Codification>> getListCodificationByType(@PathVariable String type) {
       List codifications = codificationRepository.findAllByType(type);
        return ResponseEntity.ok().body(codifications);}
    @GetMapping("/{idCodification}")
    public ResponseEntity<Codification> getCodificationById(@PathVariable Long idCodification) {
        Codification codification = codificationRepository.findById(idCodification).get();
        return ResponseEntity.ok().body(codification);}
}
