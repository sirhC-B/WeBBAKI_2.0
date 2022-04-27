package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SectorInfandTel {

    TELEKOM("Telekommunikationstechnik"),
    INFORMATIONSTECHNK("Informationstechnik");

    @Getter
    private final String value;

}
