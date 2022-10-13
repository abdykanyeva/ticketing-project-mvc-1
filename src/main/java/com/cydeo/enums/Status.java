package com.cydeo.enums;

public enum Status {


    OPEN("Open"), IN_PROGRESS("In Progress"), COMPLETE("Complete");


    private String value;

    Status(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
