package webpe;

import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.ClientToServerHandshakePacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ServerToClientHandshakePacket;

public class WebPEPacketHandler implements BedrockPacketHandler {


    @Override
    public boolean handle(PlayStatusPacket packet) {
        System.out.println(packet);
        return false;
    }

    @Override
    public boolean handle(ServerToClientHandshakePacket packet) {
        System.out.println(packet);
        return false;
    }

    public boolean handle(ClientToServerHandshakePacket packet) {

        System.out.println(packet);

        return false;
    }
}
