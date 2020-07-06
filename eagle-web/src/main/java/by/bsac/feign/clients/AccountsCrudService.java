package by.bsac.feign.clients;

import by.bsac.exceptions.commons.BadRequestException;
import by.bsac.webmvc.dto.UserWithAccountDto;
import feign.Headers;
import feign.RequestLine;

public interface AccountsCrudService {

    @Headers({"Content-Type: application/json","Charset: utf-8"})
    @RequestLine("POST /get_by_email")
    UserWithAccountDto getAccountByEmail(UserWithAccountDto dto) throws BadRequestException;

}
