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

    public void fillCodificationTable() {

        ArrayList<Codification> codifications = new ArrayList<>();
//        codifications.add()
        codifications.add(new Codification(null, TypeCodification.TYPE_TRANSACTION.toString(), TypeTransaction.EMISSION.toString(),TypeTransaction.EMISSION.getCode()));
        codifications.add(new Codification(null,TypeCodification.TYPE_TRANSACTION.toString(),TypeTransaction.MODIFICATION.toString(),TypeTransaction.MODIFICATION.getCode()));
        codifications.add(new Codification(null,TypeCodification.TYPE_TRANSACTION.toString(),TypeTransaction.AMENDEMENT.toString(),TypeTransaction.AMENDEMENT.getCode()));
        codifications.add(new Codification(null,TypeCodification.TYPE_TRANSACTION.toString(),TypeTransaction.UTILISATION_A_VUE.toString(),TypeTransaction.UTILISATION_A_VUE.getCode()));
        codifications.add(new Codification(null,TypeCodification.TYPE_TRANSACTION.toString(),TypeTransaction.UTILISATION_A_ECHEANCE.toString(),TypeTransaction.UTILISATION_A_ECHEANCE.getCode()));
        codifications.add(new Codification(null,TypeCodification.TYPE_TRANSACTION.toString(),TypeTransaction.MESSAGE.toString(),TypeTransaction.MESSAGE.getCode()));
        codifications.add(new Codification(null,TypeCodification.TYPE_PRODUIT.toString(), TypeProduit.EXPORT.toString(),TypeProduit.EXPORT.getCode()));
        codifications.add(new Codification(null,TypeCodification.TYPE_PRODUIT.toString(),TypeProduit.IMPORT.toString(),TypeProduit.IMPORT.getCode()));

        for (Codification codification: codifications
             ) {
            if (codificationRepository.findByCode(codification.getCode()).isEmpty()) {
                codificationRepository.save(codification);
            }
        }
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
