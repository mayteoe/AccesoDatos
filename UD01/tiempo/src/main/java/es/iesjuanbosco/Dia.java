package es.iesjuanbosco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dia {
    @JacksonXmlProperty(isAttribute = true, localName = "from")
    public LocalDateTime fechaIni;
    @JacksonXmlProperty(isAttribute = true, localName = "to")
    private LocalDateTime fechaFin;
}

