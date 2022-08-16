package adria.pfa.adriaReporting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "codeBIC")
    private String codeBIC;
    @Column(name = "address")
    private String address;

    @OneToMany
    private Collection<Client> clients = new ArrayList<>();

    @OneToMany
    private Collection<Beneficiaire> beneficiaires = new ArrayList<>();
}
