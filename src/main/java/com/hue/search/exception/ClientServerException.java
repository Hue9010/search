package com.hue.search.exception;

import feign.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@NoArgsConstructor
public class ClientServerException extends RuntimeException {
    private HttpStatus status;
    private String detailMessage;
    private String path;
    private  String body;

    public ClientServerException(String methodKey, Response response) {
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
