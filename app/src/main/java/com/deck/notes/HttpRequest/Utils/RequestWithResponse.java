package com.deck.notes.HttpRequest.Utils;

public interface RequestWithResponse<TypeRequest, TypeResponse> {

    void callRequest(TypeRequest object, RequestWithResponseCallback<TypeResponse> callback);

}
