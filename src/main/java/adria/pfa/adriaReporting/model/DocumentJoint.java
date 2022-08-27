package adria.pfa.adriaReporting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "name")
    private String name;
    @Column(name="type")
    private String type;
    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
