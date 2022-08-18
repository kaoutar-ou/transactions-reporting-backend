package adria.pfa.adriaReporting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Beneficiaire extends User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long id_Beneficiary;
    @Column(name = "nomComplet")
    private String nomComplet;
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    private String status;
    @Column(name = "account")
    private Long account;

    @ManyToOne
    @JoinColumn(name = "banque_id")
//    @JsonBackReference
    private Banque banque;

    @JsonIgnore
    @OneToMany(mappedBy = "beneficiaire", fetch = FetchType.EAGER)
//    @JsonManagedReference
    private Collection<Transaction> transactions = new ArrayList<>();
}