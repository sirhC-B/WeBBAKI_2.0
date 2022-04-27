package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorFinance {

    BANKEN("Banken"),
    BOERSEN("Börsen"),
    VERSICHERUNGEN("Versicherungen"),
    FINANZDIENSTLEISTER("Finanzdienstleister");

    @Getter
    private final String value;





}
