package adria.pfa.adriaReporting.dao;

import adria.pfa.adriaReporting.enumeration.TypePayement;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDao {
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
