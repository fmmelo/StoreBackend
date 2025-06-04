package com.myproject.myproject.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.myproject.myproject.api.DTO.restclientDTO.TokenObjDTO;
import com.myproject.myproject.exception.InvalidTokenException;

@Service
public class AuthRESTClientService {
    private RestClient fakeStoreClient;
    private RestClient authServerClient;

    public void build(RestClient.Builder restClientBuilder) {
        this.fakeStoreClient = restClientBuilder.baseUrl("https://fakestoreapi.com/").build();
        this.authServerClient = restClientBuilder.baseUrl("http://node-auth:5050/auth/").build();
    }

    public String getProduct(int id) {
        return this.fakeStoreClient.get().uri("/products/{id}", id).retrieve().body(String.class);
    }

    public String getAllProducts() {
        return this.fakeStoreClient.get().uri("/products").retrieve().body(String.class);
    }

    public TokenObjDTO validateToken(String token) throws InvalidTokenException {
        TokenObjDTO obj = this.authServerClient.post().uri("/token_verify").header("authorization", token)
                .exchange((request, response) -> {
                    System.out.println(response.getStatusCode());
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new InvalidTokenException("The provided token is not valid");
                    } else if (response.getStatusCode().is5xxServerError()) {
                        throw new InvalidTokenException("The provided token is not valid");
                    } else {
                        return response.bodyTo(TokenObjDTO.class);
                    }
                });
        System.out.println(obj);
        return obj;
    }
}
