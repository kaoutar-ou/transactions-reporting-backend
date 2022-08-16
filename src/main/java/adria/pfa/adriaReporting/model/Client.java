package adria.pfa.adriaReporting.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_User;
    @Column(name = "nomComplet")
    private String nomComplet;
    @Column(name = "address")
    private String address;
    @Column(name = "account")
    private Long account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "banque_id")
    private Banque banque;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Transaction> transactions = new ArrayList<>();
}