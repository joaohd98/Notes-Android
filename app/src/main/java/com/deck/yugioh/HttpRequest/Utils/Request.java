package com.deck.yugioh.HttpRequest.Utils;


public interface Request<Type> {

    void callRequest(Type object, RequestCallBack<Type> callback);

}
