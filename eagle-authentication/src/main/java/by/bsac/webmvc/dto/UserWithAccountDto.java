package by.bsac.webmvc.dto;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoProperty;
import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import by.bsac.models.Status;
import by.bsac.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Dto({User.class, Account.class, AccountStatus.class})
@Getter @Setter
@NoArgsConstructor
@ToString
public class UserWithAccountDto implements Serializable {

    private Integer user_id;
    private Integer account_id;
    private Integer status_id;

    @DtoProperty(entityProperty = "account_email")
    private String account_email;

    @DtoProperty(entityProperty = "account_password")
    private String account_password;

    @DtoProperty(entityProperty = "status")
    private Status account_status;

}
