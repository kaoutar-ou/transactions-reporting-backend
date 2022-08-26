package adria.pfa.adriaReporting.controller;

import adria.pfa.adriaReporting.model.Beneficiaire;
import adria.pfa.adriaReporting.service.BeneficiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/beneficiaires")
@CrossOrigin(origins = "*", maxAge = 3700)
public class BeneficiaireController {

    private BeneficiaireService beneficiaireService;

    @Autowired
    public void setBeneficiaireService(BeneficiaireService beneficiaireService) {
        this.beneficiaireService = beneficiaireService;
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<Set<Beneficiaire>> listBenefificiaires(@PathVariable Long idClient) {
        Set<Beneficiaire> beneficiaires = beneficiaireService.listBeneficiairesByClientId(idClient);
        return ResponseEntity.ok().body(beneficiaires);
    }
}
