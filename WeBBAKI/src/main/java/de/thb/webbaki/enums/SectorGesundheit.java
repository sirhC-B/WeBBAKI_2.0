package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorGesundheit {

    MEDIZIN("medizinische Versorgung"),
    ARZNEI("Arzneimittel und Impfstoffe"),
    Labore("labore");


    @Getter
    private final String value;
}