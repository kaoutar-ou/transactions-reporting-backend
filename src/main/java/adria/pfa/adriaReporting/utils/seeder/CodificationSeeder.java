package adria.pfa.adriaReporting.utils.seeder;

import adria.pfa.adriaReporting.enumeration.TypeCodification;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import adria.pfa.adriaReporting.model.Codification;
import adria.pfa.adriaReporting.repository.CodificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CodificationSeeder {
    CodificationRepository codificationRepository;

    @Autowired
    public void setCodificationRepository(CodificationRepository codificationRepository) {
        this.codificationRepository = codificationRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillCodificationTable() {

        ArrayList<Codification> codifications = new ArrayList<>();
        codifications.add(new Codification(null, TypeCodification.TYPE_TRANSACTION.toString(), TypeTransaction.EMISSION.getValue(),TypeTransaction.EMISSION.toString()));
        codifications.add(new Codification(null,TypeCodification.TYPE_TRANSACTION.toString(),TypeTransaction.MODIFICATION.getValue(),TypeTransaction.MODIFICATION.toString()));
        codifications.add(new Codification(null,TypeCodification.TYPE_TRANSACTION.toString(),TypeTransaction.AMENDEMENT.getValue(),TypeTransaction.AMENDEMENT.toString()));
        codifications.add(new Codification(null,TypeCodification.TYPE_TRANSACTION.toString(),TypeTransaction.UTILISATION_A_VUE.getValue(),TypeTransaction.UTILISATION_A_VUE.toString()));
        codifications.add(new Codification(null,TypeCodification.TYPE_TRANSACTION.toString(),TypeTransaction.UTILISATION_A_ECHEANCE.getValue(),TypeTransaction.UTILISATION_A_ECHEANCE.toString()));
        codifications.add(new Codification(null,TypeCodification.TYPE_TRANSACTION.toString(),TypeTransaction.MESSAGE.getValue(),TypeTransaction.MESSAGE.toString()));
        codifications.add(new Codification(null,TypeCodification.TYPE_PRODUIT.toString(), TypeProduit.EXPORT.getValue(),TypeProduit.EXPORT.toString()));
        codifications.add(new Codification(null,TypeCodification.TYPE_PRODUIT.toString(),TypeProduit.IMPORT.getValue(),TypeProduit.IMPORT.toString()));

        for (Codification codification: codifications
        ) {
            if (codificationRepository.findByCode(codification.getCode()).isEmpty()) {
                codificationRepository.save(codification);
            }
        }
    }
}
