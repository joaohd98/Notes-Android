package com.deck.yugioh.Wrapper;

import com.deck.yugioh.Model.Register.RegisterRequestModel;
import com.deck.yugioh.Model.Register.RegisterResponseModel;

public class RegisterWrapper {

    private RegisterRequestModel registerRequestModel;
    private RegisterResponseModel registerResponseModel;


    public RegisterRequestModel getRegisterRequestModel() {
        return registerRequestModel;
    }

    public void setRegisterRequestModel(RegisterRequestModel registerRequestModel) {
        this.registerRequestModel = registerRequestModel;
    }

    public RegisterResponseModel getRegisterResponseModel() {
        return registerResponseModel;
    }

    public void setRegisterResponseModel(RegisterResponseModel registerResponseModel) {
        this.registerResponseModel = registerResponseModel;
    }
}
