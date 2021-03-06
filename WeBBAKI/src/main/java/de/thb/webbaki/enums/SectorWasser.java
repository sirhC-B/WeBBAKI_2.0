package de.thb.webbaki.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorWasser {

    WASSERVERSORGUNG("√∂ffentliche Wasserversorgung"),
    ABWASSERVERSORGUNG("√∂ffentliche Abwasserbeseitigung");

    @Getter
    private final String value;

}
