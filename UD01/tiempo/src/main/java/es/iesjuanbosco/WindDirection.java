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
public class WindDirection {
  @JacksonXmlProperty(isAttribute = true,localName = "code")
    private String codigo;
  @JacksonXmlProperty(isAttribute = true, localName = "deg")
    private Double grados;
  @JacksonXmlProperty(isAttribute = true, localName = "name")
    private String descripcion;
}
