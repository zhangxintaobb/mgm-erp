package com.mgm.common.helper;

import java.util.regex.*;

import static com.mgm.common.helper.GeneralHelper.isNullOrEmpty;

public class EmailHelper {

    private final static Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    public static boolean isValidEmail(String email, boolean sanitize) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        if (sanitize) {
            email = cleanEmail(email);
            if (isNullOrEmpty(email)) {
                return false;
            }
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static String cleanEmail(String email) {
        return email.replaceAll("[^\\w._@]", "");
    }

}
