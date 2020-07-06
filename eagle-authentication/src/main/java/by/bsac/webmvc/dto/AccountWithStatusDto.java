package by.bsac.webmvc.dto;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoProperty;
import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import by.bsac.models.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Dto({Account.class, AccountStatus.class})
@Getter @Setter
@ToString
public class AccountWithStatusDto implements Serializable {

    private Integer account_id;

    private String account_email;

    @DtoProperty(entityProperty = "status")
    private Status account_status;

}
