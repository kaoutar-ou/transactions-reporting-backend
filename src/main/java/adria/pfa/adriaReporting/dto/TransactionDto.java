package adria.pfa.adriaReporting.dto;

import adria.pfa.adriaReporting.enumeration.TypePayement;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private String reference;
    private TypeTransaction typeTransaction;
    private TypePayement typePayement;
    private TypeProduit typeProduit;
    private Timestamp dateExpiration;
    private double montant;
    private Timestamp dateCreation;
    private Long beneficiaire_id;
}
