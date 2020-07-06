package by.bsac.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Account {

    private Integer account_id;

    private String account_email;

    private String account_password_hash;

    private String account_password_salt;

    private  String account_password;
}
