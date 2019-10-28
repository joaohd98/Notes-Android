package com.deck.notes.HttpRequest;

import androidx.annotation.NonNull;

import com.deck.notes.HttpRequest.Utils.Request;
import com.deck.notes.HttpRequest.Utils.RequestCallBack;
import com.deck.notes.Model.Register.RegisterRequestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterAPI implements Request<RegisterRequestModel> {

    public void callRequest(final RegisterRequestModel request, final RequestCallBack callBack) {

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(request.getEmail(), request.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful() && task.getResult() != null && task.getResult().getUser() != null) {

                    FirebaseUser user = task.getResult().getUser() ;

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(request.getName()).build();

                    user.updateProfile(profileUpdates);

                    callBack.success();

                }

                else
                    callBack.error(task.getException());

            }

        });

    }

}
