package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorState{

    REGIERUNG("Regierung und Verwaltung"),
    PARLAMENT("Parlament"),
    JUSTIZEINRICHTUNG("Justizeinrichtungen"),
    NOTFALLRETTUNGSWESEN("Notfall/Rettungswesen");

    @Getter
    private final String value;

}