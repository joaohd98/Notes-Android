package com.deck.yugioh.HttpRequest.Utils;

public interface RequestWithResponse<TypeRequest, TypeResponse> {

    void callRequest(TypeRequest object, RequestWithResponseCallback<TypeResponse> callback);

}
