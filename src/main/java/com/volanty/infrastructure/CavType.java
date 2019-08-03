package com.volanty.infrastructure;

public enum CavType {

    INSPECTION("inspection"),
    VISIT("visit");

    private String type;

    public String type() {
        return type;
    }

    CavType(String type) {
        this.type = type;
    }

}
