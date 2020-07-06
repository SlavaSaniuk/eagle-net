package by.bsac.models.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "feignServers")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter @Setter
@NoArgsConstructor
@ToString
public class FeignServersModel {

    @XmlElement(name = "server")
    private List<FeignServerModel> feign_servers;

}
