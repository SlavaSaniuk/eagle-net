package by.bsac.models.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "server", propOrder = {"server_id", "server_name", "server_ip", "server_port", "server_context"})
@XmlAccessorType(XmlAccessType.FIELD)
@Getter @Setter
@NoArgsConstructor
@ToString
public class FeignServerModel {

    @XmlElement(name = "serverId")
    private int server_id;

    @XmlElement(name = "serverName")
    private String server_name;

    @XmlElement(name = "serverIp")
    private String server_ip;

    @XmlElement(name = "serverPort")
    private int server_port;

    @XmlElement(name = "serverContext")
    private String server_context;

}

