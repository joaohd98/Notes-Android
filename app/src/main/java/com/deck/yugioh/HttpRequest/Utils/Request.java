package com.deck.yugioh.HttpRequest.Utils;

public interface Request<TypeRequest> {

    void callRequest(final TypeRequest object, final RequestCallBack callback);

}
