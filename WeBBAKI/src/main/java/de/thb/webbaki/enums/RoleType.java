package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RoleType {
    KRITIS_BETREIBER("Kritisbetreiber"),
    GESCHAEFTSSTELLE("Geschäftsstelle"),
    BRANCHENADMIN("Branchenadmin"),
    SEKTORENADMIN("Sektorenadmin"),
    BUNDESADMIN("Bundesadmin"),
    SUPERADMIN("Superadmin");

    @Getter
    private final String value;
}
