package webpe;

import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.v390.Bedrock_v390;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webpe.network.ProxyPacketHandler;
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

    private static final Logger LOG = LoggerFactory.getLogger(WebPEProxy.class);

    private final WebPEServer webSocketServer;
    private final ProxyPacketHandler proxyPacketHandler;

    private final Map<String, BedrockClient> clients = new ConcurrentHashMap<>();

    private final AtomicBoolean running = new AtomicBoolean(true);

    public WebPEProxy() {

        webSocketServer = new WebPEServer(this, 19133);
        proxyPacketHandler = new ProxyPacketHandler(this);
    }

    /**
     * Connect to a Bedrock server.
     */
    public void openSession(String identifier, String host, int port) {

        BedrockClient client = this.newClient();
        client.connect(new InetSocketAddress(host, port)).whenComplete((session, throwable) -> {

            if (throwable != null) {
                System.err.println("Unable to connect to downstream server: " + throwable.getMessage());
                // throwable.printStackTrace();
                // notify client that the connection failed
                this.webSocketServer.sendMessage(identifier, "cRes;error;" + throwable.getMessage());
                return;
            }

            session.setPacketCodec(Bedrock_v390.V390_CODEC);
            session.setPacketHandler(new WebPEPacketHandler(this, identifier));
            session.setBatchedHandler(new ProxyBatchHandler(this, identifier));

            this.webSocketServer.sendMessage(identifier, "cRes;success");

            this.clients.put(identifier, client);

            System.out.println(identifier + " connected to Bedrock server.");
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
     * Calls when a ProxyMessage is received. Handle the message here.
     */
    public void handlePacket(String identifier, String data) {

        BedrockClient client = null;
        if (this.clients.containsKey(identifier)) {
            client = this.clients.get(identifier);
        }

        this.proxyPacketHandler.handlePacket(identifier, client, data);
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
            LOG.info("Closing Bedrock client: " + s);
            client.close();
        }));
        this.clients.clear();
    }


    public static void main(String[] args) {

        LOG.info(" - WebPeProxy 01 - ");
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
