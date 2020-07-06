package by.bsac.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Account status entity represent a state od account. Account can be:
 * 'CREATED' - when new account is register, account status automatically set to {@link Status#CREATED};
 * 'CONFIRMED' - when account user fills user details information.
 */
@Entity
@Table(name = "account_status")
@Getter
public class AccountStatus implements Serializable {

    @Id
    @Setter
    private Integer status_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    @Setter
    private Status status;

    @OneToOne
    @JoinColumn(name = "status_id")
    @MapsId
    private Account account;

    /**
     * Construct new AccountStatus
     * object with status {@link Status#CREATED}
     */
    public AccountStatus() {
        this.status = Status.CREATED;
    }

    @JsonIgnore
    public void setAccount(Account account) {
        this.account = account;
    }
}
