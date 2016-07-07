package br.com.deroldo.form.entity;

import java.util.Arrays;

public enum FieldType {

    COLOR("color", "^#([a-zA-Z]|[0-9]){6}$"),
    DATE("date", "^\\d{4}-(0[1-9]|1[0,1,2])-(0[1-9]|[1,2][0-9]|3[0,1])$"),
    DATETIME("datetime", "^\\d{4}-(0[1-9]|1[0,1,2])-(0[1-9]|[1,2][0-9]|3[0,1]) ([0,1][0-9]|2[0,1,2,3])(:[0,1,2,3,4,5][0-9]){2}$"),
    DATETIME_LOCAL("datetime-local", "^\\d{4}-(0[1-9]|1[0,1,2])-(0[1-9]|[1,2][0-9]|3[0,1])T([0,1][0-9]|2[0,1,2,3])(:[0,1,2,3,4,5][0-9]){2}(-|\\+)(0[0-9]|1[1,2,3,4])00$"),
    EMAIL("email", "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),
    MONTH("month", "^([1-9]|0[1-9]|1[0,1,2])$"),
    NUMBER("number", "^-?\\d*[0-9](\\.\\d*[0-9])?$"),
    TEL("tel", "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}$"),
    TIME("time", "^([0,1][0-9]|2[0,1,2,3])(:[0,1,2,3,4,5][0-9]){2}$"),
    URL("url", "^(http[s]?://|ftp://)?(www\\.)?[a-zA-Z0-9-\\.]+\\.(com|org|net|mil|edu|ca|co.uk|com.au|gov|br)$"),
    WEEK("week", null),
    CHECKBOX("checkbox", null),
    TEXT("text", null),
    RADIO("radio", null);

    private String textType;
    private String regex;

    FieldType(final String textType, final String regex){
        this.textType = textType;
        this.regex = regex;
    }

    public static FieldType get(final String type) {
        return Arrays.asList(values()).stream()
                .filter(fieldType -> fieldType.getTextType().equals(type))
                .findFirst()
                .orElse(null);
    }

    public String getTextType() {
        return textType;
    }

    public String getRegex() {
        return regex;
    }
}
