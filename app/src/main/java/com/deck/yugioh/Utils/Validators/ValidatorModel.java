package com.deck.yugioh.Utils.Validators;

import android.os.Parcel;

public class ValidatorModel implements android.os.Parcelable {

    private int rule;
    private String message;
    private Object parameter;

    static Creator<ValidatorModel> CREATOR;

    public ValidatorModel(int rule, String message) {

        this.rule = rule;
        this.message = message;

    }

    public ValidatorModel(int rule, String message, Object parameter) {

        this.rule = rule;
        this.message = message;
        this.parameter = parameter;

    }

    protected ValidatorModel(Parcel in) {

        this.rule = in.readInt();
        this.message = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.rule);
        dest.writeString(this.message);

    }

    public int getRule() {
        return rule;
    }

    public String getMessage() {
        return message;
    }

    public Object getParameter() {
        return parameter;
    }

}
