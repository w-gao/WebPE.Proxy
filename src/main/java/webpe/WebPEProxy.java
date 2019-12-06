package webpe;

import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.v361.Bedrock_v361;
import io.netty.buffer.ByteBuf;
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
        client.connect(new InetSocketAddress("play.lbsg.net", 19132)).whenComplete((session, throwable) -> {

            if (throwable != null) {
                System.err.println("Unable to connect to downstream server");
                throwable.printStackTrace();
                return;
            }

            session.setPacketCodec(Bedrock_v361.V361_CODEC);
            session.setPacketHandler(new WebPEPacketHandler());
            session.setBatchedHandler(new ProxyBatchHandler(this, identifier));


            this.clients.put(identifier, client);

            System.out.println(clientID + " connected! ");
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

        System.out.println("handlePacket!");
        if (this.clients.containsKey(identifier)) {
            BedrockClient client = this.clients.get(identifier);

            System.out.println(identifier + " --]-- packet: " + (buffer[0] & 0xff));

//            client.getSession().sendPacket(Bedrock_v361.V361_CODEC.tryDecode(Unpooled.wrappedBuffer(buffer)));
            client.getSession().sendWrapped(Unpooled.wrappedBuffer(buffer), false);
        }
    }


    /**
     * Calls when packet is received from BedrockClient. Forward to WebSocket
     */
    public void sendWrapped(String identifier, ByteBuf packet, boolean encrypt) {

        this.webSocketServer.sendPacket(identifier, packet.array());
    }





    public BedrockClient newClient() {
        InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", ThreadLocalRandom.current().nextInt(20000, 60000));
        BedrockClient client = new BedrockClient(bindAddress);
//        this.clients.add(client);
        client.bind().join();
        return client;
    }

    public void shutdown() {

        try {
            this.webSocketServer.stop();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // todo: remove clients
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
