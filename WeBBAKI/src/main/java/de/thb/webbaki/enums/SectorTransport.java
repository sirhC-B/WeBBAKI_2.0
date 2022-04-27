package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorTransport {

    LUFTFAHRT("Luftfahrt"),
    SEESCHIFFFARHT("Seeschifffahrt"),
    BINNENSCHIFFFAHRT("Binnenschifffahrt"),
    SCHIENENVERKEHR("Schienenverkehr"),
    STRASSENVERKEHR("Straßenvekehr"),
    LOGISTIK("Logistik"),
    OEPNV("ÖPNV");

    @Getter
    private final String value;

}
