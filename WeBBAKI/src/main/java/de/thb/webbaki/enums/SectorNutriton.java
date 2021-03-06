package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorNutriton{

    ERNAEHRUNGSWISSENSCHAFT("Ern√§hrungswissenschaft"),
    LEBENSMITTELHANDEL("Lebensmittelhandel");

    @Getter
    private final String value;

}