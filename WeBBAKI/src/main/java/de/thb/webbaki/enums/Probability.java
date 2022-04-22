package de.thb.webbaki.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Probability {
    NEVER("nie"),
    SELDOM("selten"),
    MEDIUM("mittel"),
    OFTEN("haeufig"),
    VERY_OFTEN("sehr haeufig");



    @Getter
    private final String value;
}
