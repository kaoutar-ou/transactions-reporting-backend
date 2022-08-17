package adria.pfa.adriaReporting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentJoint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "intitule")
    private String intitule;
    @Column(name="type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
