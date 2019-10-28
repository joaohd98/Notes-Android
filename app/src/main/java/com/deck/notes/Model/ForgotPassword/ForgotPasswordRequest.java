package com.deck.notes.Model.ForgotPassword;

import com.deck.notes.Components.InputView;

public class ForgotPasswordRequest {

    private String email;

    public ForgotPasswordRequest(String email) {

        this.email = email;

    }

    public ForgotPasswordRequest(InputView email) {

        this.email = email.getInputValue();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
