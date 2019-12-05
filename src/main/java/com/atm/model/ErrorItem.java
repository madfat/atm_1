package com.atm.model;

public class ErrorItem {
    private String fieldName;
    private String errorDesc;

    public ErrorItem() {
    }

    public ErrorItem(String fieldName, String errorDesc) {
        this.fieldName = fieldName;
        this.errorDesc = errorDesc;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
