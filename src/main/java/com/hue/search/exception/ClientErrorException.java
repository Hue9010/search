package com.hue.search.exception;

import feign.Response;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public class ClientErrorException extends RuntimeException {
    private final HttpStatus status;
    private final String detailMessage;
    private final String path;
    private final String body;

    public ClientErrorException(String methodKey, Response response) {
        super(String.format("%s Call이 실패하였습니다.", methodKey));
        this.status = HttpStatus.valueOf(response.status());
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
