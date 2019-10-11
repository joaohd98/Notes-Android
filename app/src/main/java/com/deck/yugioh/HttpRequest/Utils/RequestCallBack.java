package com.deck.yugioh.HttpRequest.Utils;

public interface RequestCallBack<Type> {

    void success(Type response);
    void error();

}
