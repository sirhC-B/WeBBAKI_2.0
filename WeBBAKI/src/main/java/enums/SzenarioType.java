package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
All Mastertopics are listed here
 */
@AllArgsConstructor
public enum SzenarioType {
    WIRKUNG_AUF_BETREIBER("BetreiberEinwirkung"), // Szenarien mit indirekter Wirkung auf den Betreiber Höhere Gewalt
    EINWIRKUNG_AUF_SUBJEKTE("SubjektEinwirkung"), //Szenarien mit direkter Einwirkung auf/durch Subjekte (Personal, Lieferanten, Dritte)
    EINWIRKUNG_AUF_PERIMETER("PerimeterEinwirkung"), //Szenarien mit direkter Einwirkung auf die Perimetergrenze (Gebäude/Räume/Infrastruktur)
    EINWIRKUNG_AUF_TECHNIK("TechnikEinwirkung"), //Szenarien mit direkter Einwirkung auf die eingesetzte Technik (Einwirkung auf IT/IC-Systeme)
    EINWIRKUNG_AUF_ENTITY("EntityEinwirkung"); //Szenarien mit direkter Einwirkung auf logische Entitäten (Accounts/Daten)

    @Getter
    private final String value;

}
