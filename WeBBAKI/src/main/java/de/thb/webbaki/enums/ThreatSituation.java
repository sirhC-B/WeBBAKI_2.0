package de.thb.webbaki.enums;

public enum ThreatSituation {
    UNKNOWN("Unbekannt", "white",-1), NONE("keine Gefährdung", "white", 0), LOW("geringe Gefährdung", "green", 4), INCREASED("erhöhte Gefährdung","yellow", 9), HIGH("hohe Gefährdung", "orange", 12), VERYHIGH("sehr hohe Gefährdung", "red", 16);
    private String symbol;
    private float value;
    private String color;

    ThreatSituation(String symbol, String color, float value){
        this.symbol = symbol;
        this.color = color;
        setValue(value);
    }

    public static ThreatSituation getThreatSituationFromValue(float value){
        ThreatSituation threadSituation;
        if(value <= -1){
            threadSituation = UNKNOWN;
        }else if(value == 0){
            threadSituation = NONE;
        }else if(value < 5){
            threadSituation = LOW;
        }else if(value < 10){
            threadSituation = INCREASED;
        }else if(value < 13){
            threadSituation = HIGH;
        }else{
            threadSituation = VERYHIGH;
        }
        threadSituation.setValue(value);
        return threadSituation;
    }

    public void setValue(float value){
        if(value < - 1){
            this.value = -1;
        } else if(value > 16) {
            this.value = 16;
        }else{
            this.value = value;
        }
    }

    public float getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }
}
