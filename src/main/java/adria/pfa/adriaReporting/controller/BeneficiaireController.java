package adria.pfa.adriaReporting.controller;

import adria.pfa.adriaReporting.model.Beneficiaire;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.service.BeneficiaireService;
import adria.pfa.adriaReporting.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/beneficiaires")
@CrossOrigin(origins = "*", maxAge = 3700)
public class BeneficiaireController {

    @Autowired
    private BeneficiaireService beneficiaireService;

    @GetMapping("/{idClient}")
    public ResponseEntity<Set<Beneficiaire>> listBenefificiaires(@PathVariable Long idClient) {
        Set<Beneficiaire> beneficiaires = beneficiaireService.listBeneficiairesByClientId(idClient);
        return ResponseEntity.ok().body(beneficiaires);
    }
}
