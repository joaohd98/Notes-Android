package com.deck.yugioh.Utils.Validators;

import android.os.Parcel;

import androidx.annotation.Nullable;

public class ValidatorModel implements android.os.Parcelable {

    private int rule;
    private String message;
    private @Nullable Integer parameterInt;

    static Creator<ValidatorModel> CREATOR;

    public ValidatorModel(int rule, String message) {

        this.rule = rule;
        this.message = message;

    }

    public ValidatorModel(int rule, String message, @Nullable Integer parameterInt) {

        this.rule = rule;
        this.message = message;
        this.parameterInt = parameterInt;

    }

    protected ValidatorModel(Parcel in) {

        this.rule = in.readInt();
        this.message = in.readString();
        this.parameterInt = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.rule);
        dest.writeString(this.message);

        if(this.parameterInt != null)
            dest.writeInt(this.parameterInt);

    }

    public int getRule() {
        return rule;
    }

    public String getMessage() {
        return message;
    }

    @Nullable
    public Integer getParameterInt() {
        return parameterInt;
    }

}
