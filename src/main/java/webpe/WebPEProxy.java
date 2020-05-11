package webpe;

import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.v389.Bedrock_v389;
import io.netty.buffer.Unpooled;
import webpe.websocket.WebPEServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * WebPEPlugin
 *
 * @author William Gao
 */
public class WebPEProxy {

    private final AtomicBoolean running = new AtomicBoolean(true);

    private final WebPEServer webSocketServer;

    private final Map<String, BedrockClient> clients = new ConcurrentHashMap<>();


    public WebPEProxy() {

        webSocketServer = new WebPEServer(this, 19133);

    }

    /**
     * Calls when WebSocket connection establishes. Connects to a Bedrock server.
     */
    public void openSession(String identifier, String address, int port, long clientID) {

        BedrockClient client = this.newClient();
        client.connect(new InetSocketAddress("0.0.0.0", 19132)).whenComplete((session, throwable) -> {

            if (throwable != null) {
                System.err.println("Unable to connect to downstream server: " + throwable.getMessage());
//                throwable.printStackTrace();
                return;
            }

            session.setPacketCodec(Bedrock_v389.V389_CODEC);
            session.setPacketHandler(new WebPEPacketHandler(this, identifier));
            session.setBatchedHandler(new ProxyBatchHandler(this, identifier));

//            session.sendPacket(ClientPacketFactory.randomLoginPacket());

            this.clients.put(identifier, client);

            System.out.println(clientID + " connected to Bedrock server.");
        });
    }

    /**
     * Calls when WebSocket requests to close connection. Disconnect from Bedrock client as well.
     */
    public void closeSession(String identifier, String reason) {

        this.webSocketServer.closeWebSocket(identifier, reason);
        if (this.clients.containsKey(identifier)) {
            BedrockClient client = this.clients.get(identifier);
            this.clients.remove(identifier);
            client.close();
        }
    }

    /**
     * Calls when a WebSocket packet is received. Forward to BedrockClient
     */
    public void handlePacket(String identifier, byte[] buffer) {

        System.out.println(identifier + " handlePacket from WebSocket");
        if (this.clients.containsKey(identifier)) {
            BedrockClient client = this.clients.get(identifier);

            System.out.println(identifier + " --]-- packet: " + (buffer[0] & 0xff));

            // todo: check for batch packet.

            BedrockPacket pk = client.getSession().getPacketCodec().tryDecode(Unpooled.copiedBuffer(buffer));
            System.out.println(pk);
            client.getSession().sendPacket(pk);
        }
    }


    /**
     * Calls when packet is received from BedrockClient. Forward to WebSocket
     */
    public void sendPacket(String identifier, byte[] packet) {

        this.webSocketServer.sendPacket(identifier, packet);
    }


    public BedrockClient newClient() {
        InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", ThreadLocalRandom.current().nextInt(20000, 60000));
        BedrockClient client = new BedrockClient(bindAddress);
        client.bind().join();
        return client;
    }

    public void shutdown() {

        try {
            this.webSocketServer.stop();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        this.clients.forEach(((s, client) -> {
            System.out.println("Closing Bedrock client: " + s);
            client.close();
        }));
        this.clients.clear();
    }


    public static void main(String[] args) {

        System.out.println(" - WebPeProxy 01 - ");
        WebPEProxy proxy = new WebPEProxy();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
        } catch (Exception ex) {
            // ignored
        }

        proxy.shutdown();
        System.out.println("cya");
    }

}
