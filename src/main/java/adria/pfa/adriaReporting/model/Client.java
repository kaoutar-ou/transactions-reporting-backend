package adria.pfa.adriaReporting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client extends User {
    @Column(name = "nomComplet")
    private String nomComplet;
    @Column(name = "address")
    private String address;
    @Column(name = "account")
    private Long account;

    @ManyToOne
    @JoinColumn(name = "banque_id")
    private Banque banque;

    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Collection<Transaction> transactions = new ArrayList<>();
}