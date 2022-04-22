package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorEnergie {

    ELEKTRIZITAET("Elektrizität"),
    GAS("Gas"),
    MINERALOEL("Mineralöl"),
    FERNWAERME("Fernwärme");

    @Getter
    private final String value;

}
