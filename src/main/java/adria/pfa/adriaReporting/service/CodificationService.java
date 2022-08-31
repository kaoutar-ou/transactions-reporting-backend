package adria.pfa.adriaReporting.service;

import adria.pfa.adriaReporting.enumeration.TypeCodification;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.Codification;
import adria.pfa.adriaReporting.repository.CodificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodificationService {

    CodificationRepository codificationRepository;

    @Autowired
    public void setCodificationRepository(CodificationRepository codificationRepository) {
        this.codificationRepository = codificationRepository;
    }

    public Codification getCodeificationByCode(Long idCodification) {
        Codification codification = codificationRepository.findById(idCodification).get();
        return codification;
    }

    public Codification getCodeificationById(Long idCodification) {
        Codification codification = codificationRepository.findById(idCodification).get();
        return codification;
    }

    public List<Codification> getCodeificationsByType(String type) {
        List<Codification> codifications = codificationRepository.findAllByType(type);
        return codifications;
    }

}
