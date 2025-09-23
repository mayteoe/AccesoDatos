package es.iesjuanbosco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JacksonXmlRootElement(localName = "weatherdata")

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pronostico {
    @JacksonXmlProperty(localName = "forecast")
    @JacksonXmlElementWrapper(useWrapping = false)


    private Forecast pronostico;

    }
