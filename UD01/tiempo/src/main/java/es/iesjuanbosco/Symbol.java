package es.iesjuanbosco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Symbol {
    @JacksonXmlProperty(isAttribute = true, localName = "number")
    private String number;
    @JacksonXmlProperty(isAttribute = true, localName = "name")
    private String name;
    @JacksonXmlProperty(isAttribute = true, localName = "var")
    private String var;
}
