package com.deck.yugioh.Utils.Validators;

import com.deck.yugioh.R;

import java.util.regex.Pattern;

public class Validators {

    private static boolean required(String text) {

        return !text.isEmpty();

    }

    private static boolean isEmail(String text) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(text).matches();

    }

    private static boolean hasMinLength(String text, Integer size) {

        return text.length() >= size;

    }

    private static boolean hasMaxLength(String text, Integer size) {

        return text.length() <= size;

    }

    public static boolean isValid(int rule, String text) {

        return Validators.isValid(rule, text, null);

    }

    public static boolean isValid(int rule, String text, Object parameter) {

        if(rule == R.string.validators_required)
            return Validators.required(text);

        else if(rule == R.string.validators_email)
            return Validators.isEmail(text);

        else if(rule == R.string.validators_min_length)
            return Validators.hasMinLength(text, (Integer) parameter);


        else if(rule == R.string.validators_max_length)
            return Validators.hasMaxLength(text, (Integer) parameter);

        else
            return true;

    }

}
