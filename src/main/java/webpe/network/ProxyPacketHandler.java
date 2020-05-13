package webpe.network;

import com.nukkitx.protocol.bedrock.BedrockClient;
import webpe.WebPEProxy;
import webpe.utils.ClientPacketFactory;

public class ProxyPacketHandler {

    private final WebPEProxy proxy;

    public ProxyPacketHandler(WebPEProxy proxy) {

        this.proxy = proxy;
    }

    public void handlePacket(String identifier, BedrockClient client, String data) {

        String[] payload = data.split(";");
        switch (payload[0]) {
            default:
                System.out.println(identifier + ": Unknown PacketId: " + payload[0]);
                break;
            case "cReq":

                if (payload.length < 3) {
                    System.out.println(identifier + " Invalid packet.");
                    return;
                }

                // cReq;host;port
                String host = payload[1];
                int port = 19132;
                try {
                    port = Integer.parseInt(payload[2]);
                } catch (NumberFormatException ex) {
                    // ignored
                }

                this.proxy.openSession(identifier, host, port);
                break;
            case "sLogin":

                String playerName = "WebPE";

                if (payload.length > 1) {
                    playerName = payload[1];
                }

                if (client != null) {
                    System.out.println(identifier + " Sending loginPacket with displayName=" + playerName);

                    client.getSession().sendPacket(ClientPacketFactory.randomLoginPacket(playerName));
                }

                break;
        }
    }
}
