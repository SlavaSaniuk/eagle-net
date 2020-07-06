package by.bsac.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Setter
    private Integer user_id;

    @Column(name = "user_id_alias", unique = true, length = 30)
    @Setter
    private String user_id_alias;

    @OneToOne(mappedBy = "account_user", fetch = FetchType.LAZY)
    private Account user_account;

    @JsonIgnore
    public void setUserAccount(Account user_account) {
        this.user_account = user_account;
    }
}
