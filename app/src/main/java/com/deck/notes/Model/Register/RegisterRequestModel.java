package com.deck.notes.Model.Register;

import com.deck.notes.Components.InputView;

public class RegisterRequestModel {

    private String name;
    private String email;
    private String password;

    public RegisterRequestModel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public RegisterRequestModel(InputView name, InputView email, InputView password) {
        this.name = name.getInputValue();
        this.email = email.getInputValue();
        this.password = password.getInputValue();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
