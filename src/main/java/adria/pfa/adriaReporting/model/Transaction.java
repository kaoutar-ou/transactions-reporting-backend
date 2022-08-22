package adria.pfa.adriaReporting.model;

import adria.pfa.adriaReporting.enumeration.TypePayement;
import adria.pfa.adriaReporting.enumeration.TypeProduit;
import adria.pfa.adriaReporting.enumeration.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "reference")
    private String reference;
    @Enumerated(EnumType.STRING)
    @Column(name = "typeTransaction")
    private TypeTransaction typeTransaction;
    @Enumerated(EnumType.STRING)
    @Column(name = "typePayement")
    private TypePayement typePayement;
    @Enumerated(EnumType.STRING)
    @Column(name = "typeProduit")
    private TypeProduit typeProduit;
    @Column(name = "dateExpiration")
    private Date dateExpiration;
    @Column(name = "montant")
    private double montant;

    @CreationTimestamp
    private Timestamp dateCreation;

    @OneToMany(mappedBy = "transaction", fetch = FetchType.EAGER)
    private Collection<DocumentJoint> documentJoints = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "beneficaire_id")
    private Beneficiaire beneficiaire;

    public Transaction(String reference, TypeTransaction typeTransaction, TypePayement typePayement, TypeProduit typeProduit, Date date, double montant, ArrayList<DocumentJoint> documentJoints, Client client, Beneficiaire beneficiaire) {
        this.reference = reference;
        this.typeTransaction = typeTransaction;
        this.typePayement = typePayement;
        this.typeProduit = typeProduit;
        this.dateExpiration = date;
        this.montant = montant;
        this.documentJoints = documentJoints;
        this.client = client;
        this.beneficiaire = beneficiaire;
    }
    public String getBeneficiairename(){
        return this.beneficiaire.getNomComplet();
    }

    public Long getBeneficiaireaccount(){
        return this.beneficiaire.getAccount();
    }
    public String getBeneficiaireaddress(){
        return this.beneficiaire.getAddress();
    }
    public String getBeneficiairebankname(){
        return this.beneficiaire.getBanque().getNom();
    }
    public String getBeneficiairecodeBIC(){
        return this.beneficiaire.getBanque().getCodeBIC();
    }
    public String getBeneficiaireBankaddress(){
        return this.beneficiaire.getBanque().getAddress();
    }
    public String getBeneficiairestatus(){
        return this.beneficiaire.getStatus();
    }
    public String getClientname(){
        return this.client.getNomComplet();
    }
    public String getClientaddress(){
        return this.client.getAddress();
    }
    public Long getClientaccount(){
        return this.client.getAccount();
    }
    public String getClientbankname(){
        return this.client.getBanque().getNom();
    }
    public String getClientcodeBIC(){
        return this.client.getBanque().getCodeBIC();
    }
    public String getClientbankadaress(){
        return this.client.getBanque().getAddress();
    }

}
