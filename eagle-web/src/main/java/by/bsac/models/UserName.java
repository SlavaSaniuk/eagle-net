package by.bsac.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
public class UserName {

    private String first_name;

    private String last_name;

    public UserName(String fname, String lname) {
        this.first_name = fname;
        this.last_name = lname;
    }

    public String getFullName() {
        return this.first_name +" " +this.last_name;
    }

}
