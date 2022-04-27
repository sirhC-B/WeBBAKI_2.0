package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorMedandCult {

    RUNDFUNK("Rundfunk (Fernsehen und Radio)"),
    PRESSE("gedruckte und elektronische Presse"),
    KULTURGUT("Kulturgut"),
    BAUWERKE("symbolträchtige Bauwerke");

    @Getter
    private final String value;

}
