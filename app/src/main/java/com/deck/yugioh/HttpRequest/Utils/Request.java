package com.deck.yugioh.HttpRequest.Utils;

public interface Request<TypeRequest> {

    void callRequest(TypeRequest object, RequestCallBack callback);

}
