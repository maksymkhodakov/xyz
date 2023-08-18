package com.example.xyz.Utils;

import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class OWASPUtil {
    private final int MINIMAL_LENGTH = 1;
    private final String MASK_REGEX = "(.)";
    private final String MASK_SYMBOL = "*";

    public String maskValue(String value) {
        return Optional.ofNullable(value)
                .filter(val -> val.length() > MINIMAL_LENGTH)
                .map(OWASPUtil::mask)
                .orElse(value);
    }

    private String mask(String s) {
        return s.replaceAll(MASK_REGEX, MASK_SYMBOL);
    }
}
