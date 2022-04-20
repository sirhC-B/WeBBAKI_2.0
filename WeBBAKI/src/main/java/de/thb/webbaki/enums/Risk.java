package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Risk {
    NONE("keine Gefaehrdung"),
    SMALL("geringe Gefaehrdung"),
    INCREASED("erhoehte Gefaehrdung"),
    HIGH("hohe Gefaehrdung"),
    VERY_HIGH("sehr hohe Gefaehrdung");

    @Getter
    private final String value;

}

