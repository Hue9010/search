package com.hue.search.client.configuration;

import com.hue.search.exception.ClientErrorException;
import com.hue.search.exception.ClientNotFoundException;
import com.hue.search.exception.ClientServerException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class ClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        var httpStatus = HttpStatus.valueOf(response.status());

        if (httpStatus.is5xxServerError()) {
            return new ClientServerException(methodKey, response);
        } else if (HttpStatus.NOT_FOUND.equals(httpStatus)){
            return new ClientNotFoundException(methodKey, response);
        }else if (httpStatus.is4xxClientError()) {
            return new ClientErrorException(methodKey, response);
        } else {
            return new ClientServerException(methodKey, response);
        }
    }
}
