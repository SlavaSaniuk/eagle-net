package by.bsac.models.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeignServer {

    private int serverId;
    private String serverName;
    private String serverIp;
    private int serverPort;
    private String serverContextPath;

    public static FeignServer of(FeignServerModel model) {
        return new FeignServer(model.getServerId(), model.getServerName(), model.getServerIp(), model.getServerPort(), model.getServerContext());
    }

    public String getFullServerPath() {
        return String.format("http://%s:%d%s", this.serverIp, this.serverPort, this.serverContextPath);
    }

    @Override
    public String toString() {
        return String.format("Server: id: %d, name: %s, ip: %s, port: %d, context: %s;",
                this.serverId, this.serverName, this.serverIp, this.serverPort, this.serverContextPath);
    }
}
