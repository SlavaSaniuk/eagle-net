package by.bsac.webmvc.dto;

import by.bsac.annotations.Dto;
import by.bsac.models.Account;
import by.bsac.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Dto({User.class, Account.class})
@Getter @Setter
@NoArgsConstructor
@ToString
public class UserWithAccountDto {

    private Integer user_id;
    private Integer account_id;

    private String account_email;

}
