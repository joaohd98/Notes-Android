package com.deck.yugioh.Wrapper;

import com.deck.yugioh.Model.Auth.AuthRequestModel;
import com.deck.yugioh.Model.Auth.AuthResponseModel;

public class AuthWrapper {

    private AuthResponseModel authResponseModel;
    private AuthRequestModel authRequestModel;

    public AuthResponseModel getAuthResponseModel() {
        return authResponseModel;
    }

    public void setAuthResponseModel(AuthResponseModel authResponseModel) {
        this.authResponseModel = authResponseModel;
    }

    public AuthRequestModel getAuthRequestModel() {
        return authRequestModel;
    }

    public void setAuthRequestModel(AuthRequestModel authRequestModel) {
        this.authRequestModel = authRequestModel;
    }
}
