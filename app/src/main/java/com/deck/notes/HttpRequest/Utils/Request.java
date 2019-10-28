package com.deck.notes.HttpRequest.Utils;

public interface Request<TypeRequest> {

    void callRequest(final TypeRequest object, final RequestCallBack callback);

}
