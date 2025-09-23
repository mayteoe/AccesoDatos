package es.iesjuanbosco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WinSpeed {
    @JacksonXmlProperty(isAttribute = true, localName = "mps")
    private Double mps;
    @JacksonXmlProperty(isAttribute = true, localName = "name")
    private String name;


}
