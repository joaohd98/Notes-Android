package com.deck.yugioh.HttpRequest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.deck.yugioh.HttpRequest.Utils.Request;
import com.deck.yugioh.HttpRequest.Utils.RequestCallBack;
import com.deck.yugioh.Model.Auth.AuthRequestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthAPI implements Request<AuthRequestModel> {

    public void callRequest(final AuthRequestModel request, final RequestCallBack callBack) {

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(request.getEmail(), request.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful() && firebaseAuth.getCurrentUser() != null)
                    callBack.success();

                else
                    callBack.error();

            }

        });

    }

}
