package adria.pfa.adriaReporting.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Beneficiaire extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_Beneficiary;
    @Column(name = "nomComplet")
    private String nomComplet;
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    private String status;
    @Column(name = "account")
    private Long account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "banque_id")
    private Banque banque;

    @OneToMany
    private Collection<Transaction> transactions = new ArrayList<>();
}