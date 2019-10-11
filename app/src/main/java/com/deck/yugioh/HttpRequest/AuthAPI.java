package com.deck.yugioh.HttpRequest;

import androidx.annotation.NonNull;

import com.deck.yugioh.HttpRequest.Utils.RequestInterface;
import com.deck.yugioh.Model.Auth.AuthRequestModel;
import com.deck.yugioh.Model.Auth.AuthResponseModel;
import com.deck.yugioh.Wrapper.AuthWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthAPI implements RequestInterface<AuthWrapper> {

    public void callRequest(final AuthWrapper auth) {

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        final AuthRequestModel request = auth.getAuthRequestModel();

        firebaseAuth.signInWithEmailAndPassword(request.getEmail(), request.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            if (task.isSuccessful() && firebaseAuth.getCurrentUser() != null) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                AuthResponseModel response = new AuthResponseModel(user.getDisplayName(), user.getEmail());

                auth.setAuthResponseModel(response);

            }

            }

        });

    }

}
