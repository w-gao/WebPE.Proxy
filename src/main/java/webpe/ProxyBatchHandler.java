package webpe;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.handler.BatchHandler;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProxyBatchHandler implements BatchHandler {

    private final WebPEProxy proxy;
    private final String identifier;

    public ProxyBatchHandler(WebPEProxy proxy, String identifier) {

        this.proxy = proxy;
        this.identifier = identifier;
    }

    @Override
    public void handle(BedrockSession session, ByteBuf compressed, Collection<BedrockPacket> packets) {

        System.out.println("batch!");


        boolean wrapperHandled = false;
        List<BedrockPacket> unhandled = new ArrayList<>();
        for (BedrockPacket packet : packets) {

            BedrockPacketHandler handler = session.getPacketHandler();

            if (handler != null && packet.handle(handler)) {
                wrapperHandled = true;
            } else {
                unhandled.add(packet);
            }
        }

        if (!wrapperHandled) {
            compressed.resetReaderIndex();
            this.proxy.sendWrapped(this.identifier, compressed, true);
        } else if (!unhandled.isEmpty()) {
//            this.proxy.sendWrapped(unhandled, true);
        }
    }
}
