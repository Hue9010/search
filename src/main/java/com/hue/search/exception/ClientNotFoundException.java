package com.hue.search.exception;

import feign.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
public class ClientNotFoundException extends RuntimeException {
    private String detailMessage;
    private String path;
    private String body;

    public ClientNotFoundException(String methodKey, Response response) {
        super(String.format("%s Call이 실패하였습니다.", methodKey));
        this.detailMessage = response.body().toString();

        var request = response.request();
        this.path = request.url();
        if (request.length() > 0) {
            this.body = Arrays.toString(request.body());
        } else {
            this.body = null;
        }
    }
}
