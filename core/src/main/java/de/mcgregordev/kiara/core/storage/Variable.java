package de.mcgregordev.kiara.core.storage;

import lombok.Getter;

@Getter
public class Variable {

    private String variableName;
    private Object value;

    public Variable(String variableName, Object value) {
        this.variableName = variableName;
        this.value = value;
    }

}
