package adria.pfa.adriaReporting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DocumentJointDto {
    private String type;
    private String name;
    private String url;
    private long size;
}
