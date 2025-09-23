package es.iesjuanbosco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tiempo {

    @JacksonXmlProperty(isAttribute = true, localName = "from")
    public LocalDateTime fechaIni;
    @JacksonXmlProperty(isAttribute = true, localName = "to")
    private LocalDateTime fechaFin;
    @JacksonXmlProperty(localName = "temperature")
    private Temperatura temperatura;
    @JacksonXmlProperty(localName = "windSpeed")
    private WinSpeed velocidadViento;
    @JacksonXmlProperty(localName = "windDirection")
    private WindDirection direccionViento;
    @JacksonXmlProperty(localName="precipitation")
    private Precipitacion precipitacion;
  @JacksonXmlProperty(localName = "symbol")
    private Symbol simbolo;

}
