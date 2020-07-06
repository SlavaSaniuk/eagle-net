package by.bsac.webmvc.dto;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoEmbedded;
import by.bsac.annotations.DtoProperty;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Dto({User.class, UserDetails.class})
@Getter @Setter
@ToString
public class UserWithDetailsDto {

    private Integer user_id;

    @DtoProperty(entityProperty = "user_id_alias")
    private String user_alias;

    private Integer detail_id;

    @DtoEmbedded(UserName.class)
    private String first_name;

    @DtoEmbedded(UserName.class)
    private String last_name;
}
