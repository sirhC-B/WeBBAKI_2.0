package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum Impact {
    NONE("keine"),
    SMALL("geringe"),
    HIGH("hohe"),
    VERY_HIGH("existenzielle");

    @Getter
    private final String value;


}
