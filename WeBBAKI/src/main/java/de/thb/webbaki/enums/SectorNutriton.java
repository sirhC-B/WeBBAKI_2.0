package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorNutriton{

    ERNAEHRUNGSWISSENSCHAFT("Ernährungswissenschaft"),
    LEBENSMITTELHANDEL("Lebensmittelhandel");

    @Getter
    private final String value;

}