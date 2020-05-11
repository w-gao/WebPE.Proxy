package webpe;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.handler.BatchHandler;
import com.nukkitx.protocol.bedrock.v389.Bedrock_v389;
import io.netty.buffer.ByteBuf;

import java.util.Collection;

public class ProxyBatchHandler implements BatchHandler {

    private final WebPEProxy proxy;
    private final String identifier;

    public ProxyBatchHandler(WebPEProxy proxy, String identifier) {

        this.proxy = proxy;
        this.identifier = identifier;
    }

    public void handle(BedrockSession session, ByteBuf compressed, Collection<BedrockPacket> packets) {

        System.out.println("Received BatchPacket (0xfe) from Bedrock server...forwarding to WebSocket client.");

        System.out.println(packets.size());
        if (packets.size() == 0) {
            System.out.println("Received 0 packets!");
            System.out.println(compressed.readableBytes());
            System.out.println("---");
        }

        ByteBuf bb = session.getCompressionHandler().compressPackets(Bedrock_v389.V389_CODEC, packets);

        byte[] packet = new byte[bb.readableBytes() + 1];
        packet[0] = (byte) 0xfe;   // batch packet
        bb.readBytes(packet, 1, bb.readableBytes());

        this.proxy.sendPacket(this.identifier, packet);
    }
}
