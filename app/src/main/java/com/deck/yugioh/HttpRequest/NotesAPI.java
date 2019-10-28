package com.deck.yugioh.HttpRequest;

import androidx.annotation.NonNull;

import com.deck.yugioh.HttpRequest.Utils.RequestWithResponse;
import com.deck.yugioh.HttpRequest.Utils.RequestWithResponseCallback;
import com.deck.yugioh.Model.Notes.NotesView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesAPI implements RequestWithResponse<String, ArrayList<NotesView>> {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("notes");

    @Override
    public void callRequest(String object, final RequestWithResponseCallback<ArrayList<NotesView>> callback) {

        this.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<NotesView> list = new ArrayList<>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    list.add(postSnapshot.getValue(NotesView.class));
                }

                callback.success(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                callback.error();

            }

        });

    }




}
