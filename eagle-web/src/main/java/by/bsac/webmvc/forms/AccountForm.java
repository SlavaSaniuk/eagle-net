package by.bsac.webmvc.forms;

import by.bsac.models.Account;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountForm {

    private String accountEmail;

    private String accountPassword;

    public Account toAccountEntity() {
        Account account = new Account();

        account.setAccountEmail(this.accountEmail);
        account.setAccountPassword(this.accountPassword);

        return account;
    }
}
