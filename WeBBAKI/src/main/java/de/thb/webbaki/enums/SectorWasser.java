package de.thb.webbaki.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorWasser {

    WASSERVERSORGUNG("öffentliche Wasserversorgung"),
    ABWASSERVERSORGUNG("öffentliche Abwasserbeseitigung");

    @Getter
    private final String value;

}
