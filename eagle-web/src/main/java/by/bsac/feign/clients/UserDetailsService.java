package by.bsac.feign.clients;

import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import feign.Headers;
import feign.RequestLine;

public interface UserDetailsService {

    @Headers({"Content-Type: application/json","Charset: utf-8"})
    @RequestLine("POST /details_create")
    UserWithDetailsDto createDetails(UserWithDetailsDto dto);

    @Headers({"Content-Type: application/json","Charset: utf-8"})
    @RequestLine("POST /details_get")
    UserWithDetailsDto getDetails(UserWithDetailsDto dto) throws NoCreatedDetailsException;

}
