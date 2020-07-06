package by.bsac.webmvc.forms;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoEmbedded;
import by.bsac.annotations.DtoProperty;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@Dto(UserDetails.class)
@ToString
public class UserDetailsForm {

    @DtoEmbedded(UserName.class)
    @DtoProperty(entityProperty = "first_name")
    private String userFname;

    @DtoEmbedded(UserName.class)
    @DtoProperty(entityProperty = "last_name")
    private String userLname;

    private char sex;

}
