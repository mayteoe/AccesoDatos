package es.iesjuanbosco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.AssertTrue;


@Data
@NoArgsConstructor
@AllArgsConstructor


@JsonIgnoreProperties(ignoreUnknown = true)
public class Temperatura {
    @JacksonXmlProperty (isAttribute = true, localName = "value")
    private Double value;
    @JacksonXmlProperty(isAttribute = true, localName = "min")
    private Double minima;
    @JacksonXmlProperty(isAttribute = true, localName = "max")
    private Double maxima;


    /**
     * Validación compuesta: min <= max
     */
    @AssertTrue(message = "La temperatura mínima debe ser menor o igual que la máxima")
    public boolean isMinMenorIgualMax() {
        if (minima == null || maxima == null) return true; // otros @NotNull se encargarán
        return minima <= maxima;
    }

    /**
     * Validación compuesta: value dentro de [min, max]
     */
    @AssertTrue(message = "El valor de la temperatura debe estar entre la mínima y la máxima")
    public boolean isValueEntreMinMax() {
        if (value == null || minima == null || maxima == null) return true;
        return value >= minima && value <= maxima;
    }

    public double getPromedio() {
        if (minima != null && maxima != null) {
            return (minima + maxima) / 2.0;
        }
        return value != null ? value : 0.0;
    }

    @Override
    public String toString() {
        if (minima != null && maxima != null) {
            return String.format("%.1f°C (min %.1f, max %.1f)", value, minima, maxima);
        } else if (value != null) {
            return String.format("%.1f°C", value);
        }
        return "Sin datos temperatura";
    }
}

