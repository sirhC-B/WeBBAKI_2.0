package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum Impact {
    NONE("keine Auswirkungen"),
    SMALL("geringe Auswirkungen"),
    HIGH("hohe Auswirkungen"),
    VERY_HIGH("existenzielle Auswirkungen");

    @Getter
    private final String value;


}
