/*
 * This class belongs to BotClient created by wode490390
 * https://github.com/wode490390/BotClient
 */
package webpe.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.packet.*;
import com.nukkitx.protocol.bedrock.util.EncryptionUtils;
import com.nukkitx.protocol.bedrock.v390.Bedrock_v390;
import io.netty.util.AsciiString;
import net.minidev.json.JSONObject;

import java.net.URI;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ClientPacketFactory {

    public static final BedrockPacketCodec CODEC = Bedrock_v390.V390_CODEC;

    private static final ThreadLocalRandom rand = ThreadLocalRandom.current();
    private static final ObjectMapper jsonMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static final String skinGeometry = "ew0KICAiZ2VvbWV0cnkuaHVtYW5vaWQiOiB7DQogICAgImJvbmVzIjogWw0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJib2R5IiwNCiAgICAgICAgInBpdm90IjogWyAwLjAsIDI0LjAsIDAuMCBdLA0KICAgICAgICAiY3ViZXMiOiBbDQogICAgICAgICAgew0KICAgICAgICAgICAgIm9yaWdpbiI6IFsgLTQuMCwgMTIuMCwgLTIuMCBdLA0KICAgICAgICAgICAgInNpemUiOiBbIDgsIDEyLCA0IF0sDQogICAgICAgICAgICAidXYiOiBbIDE2LCAxNiBdDQogICAgICAgICAgfQ0KICAgICAgICBdDQogICAgICB9LA0KDQogICAgICB7DQogICAgICAgICJuYW1lIjogIndhaXN0IiwNCiAgICAgICAgIm5ldmVyUmVuZGVyIjogdHJ1ZSwNCiAgICAgICAgInBpdm90IjogWyAwLjAsIDEyLjAsIDAuMCBdDQogICAgICB9LA0KDQogICAgICB7DQogICAgICAgICJuYW1lIjogImhlYWQiLA0KICAgICAgICAicGl2b3QiOiBbIDAuMCwgMjQuMCwgMC4wIF0sDQogICAgICAgICJjdWJlcyI6IFsNCiAgICAgICAgICB7DQogICAgICAgICAgICAib3JpZ2luIjogWyAtNC4wLCAyNC4wLCAtNC4wIF0sDQogICAgICAgICAgICAic2l6ZSI6IFsgOCwgOCwgOCBdLA0KICAgICAgICAgICAgInV2IjogWyAwLCAwIF0NCiAgICAgICAgICB9DQogICAgICAgIF0NCiAgICAgIH0sDQoNCiAgICAgIHsNCiAgICAgICAgIm5hbWUiOiAiaGF0IiwNCiAgICAgICAgInBpdm90IjogWyAwLjAsIDI0LjAsIDAuMCBdLA0KICAgICAgICAiY3ViZXMiOiBbDQogICAgICAgICAgew0KICAgICAgICAgICAgIm9yaWdpbiI6IFsgLTQuMCwgMjQuMCwgLTQuMCBdLA0KICAgICAgICAgICAgInNpemUiOiBbIDgsIDgsIDggXSwNCiAgICAgICAgICAgICJ1diI6IFsgMzIsIDAgXSwNCiAgICAgICAgICAgICJpbmZsYXRlIjogMC41DQogICAgICAgICAgfQ0KICAgICAgICBdLA0KICAgICAgICAibmV2ZXJSZW5kZXIiOiB0cnVlDQogICAgICB9LA0KDQogICAgICB7DQogICAgICAgICJuYW1lIjogInJpZ2h0QXJtIiwNCiAgICAgICAgInBpdm90IjogWyAtNS4wLCAyMi4wLCAwLjAgXSwNCiAgICAgICAgImN1YmVzIjogWw0KICAgICAgICAgIHsNCiAgICAgICAgICAgICJvcmlnaW4iOiBbIC04LjAsIDEyLjAsIC0yLjAgXSwNCiAgICAgICAgICAgICJzaXplIjogWyA0LCAxMiwgNCBdLA0KICAgICAgICAgICAgInV2IjogWyA0MCwgMTYgXQ0KICAgICAgICAgIH0NCiAgICAgICAgXQ0KICAgICAgfSwNCg0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJsZWZ0QXJtIiwNCiAgICAgICAgInBpdm90IjogWyA1LjAsIDIyLjAsIDAuMCBdLA0KICAgICAgICAiY3ViZXMiOiBbDQogICAgICAgICAgew0KICAgICAgICAgICAgIm9yaWdpbiI6IFsgNC4wLCAxMi4wLCAtMi4wIF0sDQogICAgICAgICAgICAic2l6ZSI6IFsgNCwgMTIsIDQgXSwNCiAgICAgICAgICAgICJ1diI6IFsgNDAsIDE2IF0NCiAgICAgICAgICB9DQogICAgICAgIF0sDQogICAgICAgICJtaXJyb3IiOiB0cnVlDQogICAgICB9LA0KDQogICAgICB7DQogICAgICAgICJuYW1lIjogInJpZ2h0TGVnIiwNCiAgICAgICAgInBpdm90IjogWyAtMS45LCAxMi4wLCAwLjAgXSwNCiAgICAgICAgImN1YmVzIjogWw0KICAgICAgICAgIHsNCiAgICAgICAgICAgICJvcmlnaW4iOiBbIC0zLjksIDAuMCwgLTIuMCBdLA0KICAgICAgICAgICAgInNpemUiOiBbIDQsIDEyLCA0IF0sDQogICAgICAgICAgICAidXYiOiBbIDAsIDE2IF0NCiAgICAgICAgICB9DQogICAgICAgIF0NCiAgICAgIH0sDQoNCiAgICAgIHsNCiAgICAgICAgIm5hbWUiOiAibGVmdExlZyIsDQogICAgICAgICJwaXZvdCI6IFsgMS45LCAxMi4wLCAwLjAgXSwNCiAgICAgICAgImN1YmVzIjogWw0KICAgICAgICAgIHsNCiAgICAgICAgICAgICJvcmlnaW4iOiBbIC0wLjEsIDAuMCwgLTIuMCBdLA0KICAgICAgICAgICAgInNpemUiOiBbIDQsIDEyLCA0IF0sDQogICAgICAgICAgICAidXYiOiBbIDAsIDE2IF0NCiAgICAgICAgICB9DQogICAgICAgIF0sDQogICAgICAgICJtaXJyb3IiOiB0cnVlDQogICAgICB9DQogICAgXQ0KICB9LA0KDQogICJnZW9tZXRyeS5jYXBlIjogew0KICAgICJ0ZXh0dXJld2lkdGgiOiA2NCwNCiAgICAidGV4dHVyZWhlaWdodCI6IDMyLA0KDQogICAgImJvbmVzIjogWw0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJjYXBlIiwNCiAgICAgICAgInBpdm90IjogWyAwLjAsIDI0LjAsIC0zLjAgXSwNCiAgICAgICAgImN1YmVzIjogWw0KICAgICAgICAgIHsNCiAgICAgICAgICAgICJvcmlnaW4iOiBbIC01LjAsIDguMCwgLTMuMCBdLA0KICAgICAgICAgICAgInNpemUiOiBbIDEwLCAxNiwgMSBdLA0KICAgICAgICAgICAgInV2IjogWyAwLCAwIF0NCiAgICAgICAgICB9DQogICAgICAgIF0sDQogICAgICAgICJtYXRlcmlhbCI6ICJhbHBoYSINCiAgICAgIH0NCiAgICBdDQogIH0sDQogICJnZW9tZXRyeS5odW1hbm9pZC5jdXN0b206Z2VvbWV0cnkuaHVtYW5vaWQiOiB7DQogICAgImJvbmVzIjogWw0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJoYXQiLA0KICAgICAgICAibmV2ZXJSZW5kZXIiOiBmYWxzZSwNCiAgICAgICAgIm1hdGVyaWFsIjogImFscGhhIiwNCiAgICAgICAgInBpdm90IjogWyAwLjAsIDI0LjAsIDAuMCBdDQogICAgICB9LA0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJsZWZ0QXJtIiwNCiAgICAgICAgInJlc2V0IjogdHJ1ZSwNCiAgICAgICAgIm1pcnJvciI6IGZhbHNlLA0KICAgICAgICAicGl2b3QiOiBbIDUuMCwgMjIuMCwgMC4wIF0sDQogICAgICAgICJjdWJlcyI6IFsNCiAgICAgICAgICB7DQogICAgICAgICAgICAib3JpZ2luIjogWyA0LjAsIDEyLjAsIC0yLjAgXSwNCiAgICAgICAgICAgICJzaXplIjogWyA0LCAxMiwgNCBdLA0KICAgICAgICAgICAgInV2IjogWyAzMiwgNDggXQ0KICAgICAgICAgIH0NCiAgICAgICAgXQ0KICAgICAgfSwNCg0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJyaWdodEFybSIsDQogICAgICAgICJyZXNldCI6IHRydWUsDQogICAgICAgICJwaXZvdCI6IFsgLTUuMCwgMjIuMCwgMC4wIF0sDQogICAgICAgICJjdWJlcyI6IFsNCiAgICAgICAgICB7DQogICAgICAgICAgICAib3JpZ2luIjogWyAtOC4wLCAxMi4wLCAtMi4wIF0sDQogICAgICAgICAgICAic2l6ZSI6IFsgNCwgMTIsIDQgXSwNCiAgICAgICAgICAgICJ1diI6IFsgNDAsIDE2IF0NCiAgICAgICAgICB9DQogICAgICAgIF0NCiAgICAgIH0sDQoNCiAgICAgIHsNCiAgICAgICAgIm5hbWUiOiAicmlnaHRJdGVtIiwNCiAgICAgICAgInBpdm90IjogWyAtNiwgMTUsIDEgXSwNCiAgICAgICAgIm5ldmVyUmVuZGVyIjogdHJ1ZSwNCiAgICAgICAgInBhcmVudCI6ICJyaWdodEFybSINCiAgICAgIH0sDQoNCiAgICAgIHsNCiAgICAgICAgIm5hbWUiOiAibGVmdFNsZWV2ZSIsDQogICAgICAgICJwaXZvdCI6IFsgNS4wLCAyMi4wLCAwLjAgXSwNCiAgICAgICAgImN1YmVzIjogWw0KICAgICAgICAgIHsNCiAgICAgICAgICAgICJvcmlnaW4iOiBbIDQuMCwgMTIuMCwgLTIuMCBdLA0KICAgICAgICAgICAgInNpemUiOiBbIDQsIDEyLCA0IF0sDQogICAgICAgICAgICAidXYiOiBbIDQ4LCA0OCBdLA0KICAgICAgICAgICAgImluZmxhdGUiOiAwLjI1DQogICAgICAgICAgfQ0KICAgICAgICBdLA0KICAgICAgICAibWF0ZXJpYWwiOiAiYWxwaGEiDQogICAgICB9LA0KDQogICAgICB7DQogICAgICAgICJuYW1lIjogInJpZ2h0U2xlZXZlIiwNCiAgICAgICAgInBpdm90IjogWyAtNS4wLCAyMi4wLCAwLjAgXSwNCiAgICAgICAgImN1YmVzIjogWw0KICAgICAgICAgIHsNCiAgICAgICAgICAgICJvcmlnaW4iOiBbIC04LjAsIDEyLjAsIC0yLjAgXSwNCiAgICAgICAgICAgICJzaXplIjogWyA0LCAxMiwgNCBdLA0KICAgICAgICAgICAgInV2IjogWyA0MCwgMzIgXSwNCiAgICAgICAgICAgICJpbmZsYXRlIjogMC4yNQ0KICAgICAgICAgIH0NCiAgICAgICAgXSwNCiAgICAgICAgIm1hdGVyaWFsIjogImFscGhhIg0KICAgICAgfSwNCg0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJsZWZ0TGVnIiwNCiAgICAgICAgInJlc2V0IjogdHJ1ZSwNCiAgICAgICAgIm1pcnJvciI6IGZhbHNlLA0KICAgICAgICAicGl2b3QiOiBbIDEuOSwgMTIuMCwgMC4wIF0sDQogICAgICAgICJjdWJlcyI6IFsNCiAgICAgICAgICB7DQogICAgICAgICAgICAib3JpZ2luIjogWyAtMC4xLCAwLjAsIC0yLjAgXSwNCiAgICAgICAgICAgICJzaXplIjogWyA0LCAxMiwgNCBdLA0KICAgICAgICAgICAgInV2IjogWyAxNiwgNDggXQ0KICAgICAgICAgIH0NCiAgICAgICAgXQ0KICAgICAgfSwNCg0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJsZWZ0UGFudHMiLA0KICAgICAgICAicGl2b3QiOiBbIDEuOSwgMTIuMCwgMC4wIF0sDQogICAgICAgICJjdWJlcyI6IFsNCiAgICAgICAgICB7DQogICAgICAgICAgICAib3JpZ2luIjogWyAtMC4xLCAwLjAsIC0yLjAgXSwNCiAgICAgICAgICAgICJzaXplIjogWyA0LCAxMiwgNCBdLA0KICAgICAgICAgICAgInV2IjogWyAwLCA0OCBdLA0KICAgICAgICAgICAgImluZmxhdGUiOiAwLjI1DQogICAgICAgICAgfQ0KICAgICAgICBdLA0KICAgICAgICAicG9zIjogWyAxLjksIDEyLCAwIF0sDQogICAgICAgICJtYXRlcmlhbCI6ICJhbHBoYSINCiAgICAgIH0sDQoNCiAgICAgIHsNCiAgICAgICAgIm5hbWUiOiAicmlnaHRQYW50cyIsDQogICAgICAgICJwaXZvdCI6IFsgLTEuOSwgMTIuMCwgMC4wIF0sDQogICAgICAgICJjdWJlcyI6IFsNCiAgICAgICAgICB7DQogICAgICAgICAgICAib3JpZ2luIjogWyAtMy45LCAwLjAsIC0yLjAgXSwNCiAgICAgICAgICAgICJzaXplIjogWyA0LCAxMiwgNCBdLA0KICAgICAgICAgICAgInV2IjogWyAwLCAzMiBdLA0KICAgICAgICAgICAgImluZmxhdGUiOiAwLjI1DQogICAgICAgICAgfQ0KICAgICAgICBdLA0KICAgICAgICAicG9zIjogWyAtMS45LCAxMiwgMCBdLA0KICAgICAgICAibWF0ZXJpYWwiOiAiYWxwaGEiDQogICAgICB9LA0KDQogICAgICB7DQogICAgICAgICJuYW1lIjogImphY2tldCIsDQogICAgICAgICJwaXZvdCI6IFsgMC4wLCAyNC4wLCAwLjAgXSwNCiAgICAgICAgImN1YmVzIjogWw0KICAgICAgICAgIHsNCiAgICAgICAgICAgICJvcmlnaW4iOiBbIC00LjAsIDEyLjAsIC0yLjAgXSwNCiAgICAgICAgICAgICJzaXplIjogWyA4LCAxMiwgNCBdLA0KICAgICAgICAgICAgInV2IjogWyAxNiwgMzIgXSwNCiAgICAgICAgICAgICJpbmZsYXRlIjogMC4yNQ0KICAgICAgICAgIH0NCiAgICAgICAgXSwNCiAgICAgICAgIm1hdGVyaWFsIjogImFscGhhIg0KICAgICAgfQ0KICAgIF0NCiAgfSwNCiAgImdlb21ldHJ5Lmh1bWFub2lkLmN1c3RvbVNsaW06Z2VvbWV0cnkuaHVtYW5vaWQiOiB7DQoNCiAgICAiYm9uZXMiOiBbDQogICAgICB7DQogICAgICAgICJuYW1lIjogImhhdCIsDQogICAgICAgICJuZXZlclJlbmRlciI6IGZhbHNlLA0KICAgICAgICAibWF0ZXJpYWwiOiAiYWxwaGEiDQogICAgICB9LA0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJsZWZ0QXJtIiwNCiAgICAgICAgInJlc2V0IjogdHJ1ZSwNCiAgICAgICAgIm1pcnJvciI6IGZhbHNlLA0KICAgICAgICAicGl2b3QiOiBbIDUuMCwgMjEuNSwgMC4wIF0sDQogICAgICAgICJjdWJlcyI6IFsNCiAgICAgICAgICB7DQogICAgICAgICAgICAib3JpZ2luIjogWyA0LjAsIDExLjUsIC0yLjAgXSwNCiAgICAgICAgICAgICJzaXplIjogWyAzLCAxMiwgNCBdLA0KICAgICAgICAgICAgInV2IjogWyAzMiwgNDggXQ0KICAgICAgICAgIH0NCiAgICAgICAgXQ0KICAgICAgfSwNCg0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJyaWdodEFybSIsDQogICAgICAgICJyZXNldCI6IHRydWUsDQogICAgICAgICJwaXZvdCI6IFsgLTUuMCwgMjEuNSwgMC4wIF0sDQogICAgICAgICJjdWJlcyI6IFsNCiAgICAgICAgICB7DQogICAgICAgICAgICAib3JpZ2luIjogWyAtNy4wLCAxMS41LCAtMi4wIF0sDQogICAgICAgICAgICAic2l6ZSI6IFsgMywgMTIsIDQgXSwNCiAgICAgICAgICAgICJ1diI6IFsgNDAsIDE2IF0NCiAgICAgICAgICB9DQogICAgICAgIF0NCiAgICAgIH0sDQoNCiAgICAgIHsNCiAgICAgICAgInBpdm90IjogWyAtNiwgMTQuNSwgMSBdLA0KICAgICAgICAibmV2ZXJSZW5kZXIiOiB0cnVlLA0KICAgICAgICAibmFtZSI6ICJyaWdodEl0ZW0iLA0KICAgICAgICAicGFyZW50IjogInJpZ2h0QXJtIg0KICAgICAgfSwNCg0KICAgICAgew0KICAgICAgICAibmFtZSI6ICJsZWZ0U2xlZXZlIiwNCiAgICAgICAgInBpdm90IjogWyA1LjAsIDIxLjUsIDAuMCBdLA0KICAgICAgICAiY3ViZXMiOiBbDQogICAgICAgICAgew0KICAgICAgICAgICAgIm9yaWdpbiI6IFsgNC4wLCAxMS41LCAtMi4wIF0sDQogICAgICAgICAgICAic2l6ZSI6IFsgMywgMTIsIDQgXSwNCiAgICAgICAgICAgICJ1diI6IFsgNDgsIDQ4IF0sDQogICAgICAgICAgICAiaW5mbGF0ZSI6IDAuMjUNCiAgICAgICAgICB9DQogICAgICAgIF0sDQogICAgICAgICJtYXRlcmlhbCI6ICJhbHBoYSINCiAgICAgIH0sDQoNCiAgICAgIHsNCiAgICAgICAgIm5hbWUiOiAicmlnaHRTbGVldmUiLA0KICAgICAgICAicGl2b3QiOiBbIC01LjAsIDIxLjUsIDAuMCBdLA0KICAgICAgICAiY3ViZXMiOiBbDQogICAgICAgICAgew0KICAgICAgICAgICAgIm9yaWdpbiI6IFsgLTcuMCwgMTEuNSwgLTIuMCBdLA0KICAgICAgICAgICAgInNpemUiOiBbIDMsIDEyLCA0IF0sDQogICAgICAgICAgICAidXYiOiBbIDQwLCAzMiBdLA0KICAgICAgICAgICAgImluZmxhdGUiOiAwLjI1DQogICAgICAgICAgfQ0KICAgICAgICBdLA0KICAgICAgICAibWF0ZXJpYWwiOiAiYWxwaGEiDQogICAgICB9LA0KDQogICAgICB7DQogICAgICAgICJuYW1lIjogImxlZnRMZWciLA0KICAgICAgICAicmVzZXQiOiB0cnVlLA0KICAgICAgICAibWlycm9yIjogZmFsc2UsDQogICAgICAgICJwaXZvdCI6IFsgMS45LCAxMi4wLCAwLjAgXSwNCiAgICAgICAgImN1YmVzIjogWw0KICAgICAgICAgIHsNCiAgICAgICAgICAgICJvcmlnaW4iOiBbIC0wLjEsIDAuMCwgLTIuMCBdLA0KICAgICAgICAgICAgInNpemUiOiBbIDQsIDEyLCA0IF0sDQogICAgICAgICAgICAidXYiOiBbIDE2LCA0OCBdDQogICAgICAgICAgfQ0KICAgICAgICBdDQogICAgICB9LA0KDQogICAgICB7DQogICAgICAgICJuYW1lIjogImxlZnRQYW50cyIsDQogICAgICAgICJwaXZvdCI6IFsgMS45LCAxMi4wLCAwLjAgXSwNCiAgICAgICAgImN1YmVzIjogWw0KICAgICAgICAgIHsNCiAgICAgICAgICAgICJvcmlnaW4iOiBbIC0wLjEsIDAuMCwgLTIuMCBdLA0KICAgICAgICAgICAgInNpemUiOiBbIDQsIDEyLCA0IF0sDQogICAgICAgICAgICAidXYiOiBbIDAsIDQ4IF0sDQogICAgICAgICAgICAiaW5mbGF0ZSI6IDAuMjUNCiAgICAgICAgICB9DQogICAgICAgIF0sDQogICAgICAgICJtYXRlcmlhbCI6ICJhbHBoYSINCiAgICAgIH0sDQoNCiAgICAgIHsNCiAgICAgICAgIm5hbWUiOiAicmlnaHRQYW50cyIsDQogICAgICAgICJwaXZvdCI6IFsgLTEuOSwgMTIuMCwgMC4wIF0sDQogICAgICAgICJjdWJlcyI6IFsNCiAgICAgICAgICB7DQogICAgICAgICAgICAib3JpZ2luIjogWyAtMy45LCAwLjAsIC0yLjAgXSwNCiAgICAgICAgICAgICJzaXplIjogWyA0LCAxMiwgNCBdLA0KICAgICAgICAgICAgInV2IjogWyAwLCAzMiBdLA0KICAgICAgICAgICAgImluZmxhdGUiOiAwLjI1DQogICAgICAgICAgfQ0KICAgICAgICBdLA0KICAgICAgICAibWF0ZXJpYWwiOiAiYWxwaGEiDQogICAgICB9LA0KDQogICAgICB7DQogICAgICAgICJuYW1lIjogImphY2tldCIsDQogICAgICAgICJwaXZvdCI6IFsgMC4wLCAyNC4wLCAwLjAgXSwNCiAgICAgICAgImN1YmVzIjogWw0KICAgICAgICAgIHsNCiAgICAgICAgICAgICJvcmlnaW4iOiBbIC00LjAsIDEyLjAsIC0yLjAgXSwNCiAgICAgICAgICAgICJzaXplIjogWyA4LCAxMiwgNCBdLA0KICAgICAgICAgICAgInV2IjogWyAxNiwgMzIgXSwNCiAgICAgICAgICAgICJpbmZsYXRlIjogMC4yNQ0KICAgICAgICAgIH0NCiAgICAgICAgXSwNCiAgICAgICAgIm1hdGVyaWFsIjogImFscGhhIg0KICAgICAgfQ0KICAgIF0NCiAgfQ0KDQp9DQo=";
    private static final String skinData = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==";

    private static final KeyPair keyPair = EncryptionUtils.createKeyPair();

    private static SignedJWT forgeAuthData(JSONObject extraData) {
        String publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        URI x5u = URI.create(publicKeyBase64);

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES384).x509CertURL(x5u).build();

        Date nbf = new Date(0);
        Date exp = new Date(Long.MAX_VALUE);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .notBeforeTime(nbf)
                .expirationTime(exp)
                .issueTime(exp)
                .issuer("Mojang")
                .claim("certificateAuthority", true)
                .claim("extraData", extraData)
                .claim("identityPublicKey", publicKeyBase64)
                .claim("randomNonce", ThreadLocalRandom.current().nextLong() & 0x7fffffff)
                .build();

        SignedJWT jwt = new SignedJWT(header, claimsSet);

        try {
            EncryptionUtils.signJwt(jwt, (ECPrivateKey) keyPair.getPrivate());
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return jwt;
    }

    private static JWSObject forgeSkinData(JSONObject skinData) {
        URI x5u = URI.create(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES384).x509CertURL(x5u).build();

        JWSObject jws = new JWSObject(header, new Payload(skinData));

        try {
            EncryptionUtils.signJwt(jws, (ECPrivateKey) keyPair.getPrivate());
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return jws;
    }

    private static final List<String> langs = new ArrayList<String>() {
        {
            add("en_US");
            add("en_GB");
            add("de_DE");
            add("es_ES");
            add("es_MX");
            add("fr_FR");
            add("fr_CA");
            add("it_IT");
            add("ja_JP");
            add("ko_KR");
            add("pt_BR");
            add("pt_PT");
            add("ru_RU");
            add("zh_CN");
            add("zh_TW");
            add("nl_NL");
            add("bg_BG");
            add("cs_CZ");
            add("da_DK");
            add("el_GR");
            add("fi_FI");
            add("hu_HU");
            add("id_ID");
            add("nb_NO");
            add("pl_PL");
            add("sk_SK");
            add("sv_SE");
            add("tr_TR");
            add("uk_UA");
        }
    };

    public static LoginPacket randomLoginPacket(String playerName) {
        String svrAddr = "127.0.0.1:19132";
        String mcVer = "1.14." + rand.nextInt(4);
        String lang = langs.get(rand.nextInt(langs.size() - 1));
        String geometryName = "geometry.humanoid.custom";
        String skinGeometry = "_Custom";
        if (rand.nextBoolean()) {
            geometryName += "Slim";
            skinGeometry += "Slim";
        }

        JSONObject extraDataJSON = new JSONObject();
        extraDataJSON.put("displayName", playerName);
        extraDataJSON.put("identity", UUID.randomUUID().toString());
        extraDataJSON.put("XUID", rand.nextLong());
        SignedJWT authData = forgeAuthData(extraDataJSON);

        JSONObject skinDataJSON = new JSONObject();
        skinDataJSON.put("ThirdPartyName", playerName);
        skinDataJSON.put("ServerAddress", svrAddr);
        skinDataJSON.put("GameVersion", mcVer);
        skinDataJSON.put("LanguageCode", lang);
        skinDataJSON.put("CurrentInputMode", 1);
        skinDataJSON.put("DefaultInputMode", 1);
        skinDataJSON.put("UIProfile", 0);
        skinDataJSON.put("GuiScale", 0);
        skinDataJSON.put("PlatformOfflineId", "");
        skinDataJSON.put("PlatformOnlineId", "");
        skinDataJSON.put("DeviceOS", rand.nextInt(1, 12));
        skinDataJSON.put("DeviceModel", " ");
        skinDataJSON.put("DeviceId", UUID.randomUUID().toString());
        skinDataJSON.put("SelfSignedId", UUID.randomUUID().toString());
        skinDataJSON.put("ClientRandomId", rand.nextLong() & 0x7fffffff);
        skinDataJSON.put("PremiumSkin", rand.nextBoolean());
        skinDataJSON.put("SkinGeometryName", geometryName);
        skinDataJSON.put("SkinGeometry", ClientPacketFactory.skinGeometry);
        skinDataJSON.put("SkinId", UUID.randomUUID().toString() + skinGeometry);
        skinDataJSON.put("SkinData", skinData);
        skinDataJSON.put("CapeData", "");
        skinDataJSON.put("CapeImageWidth", 0);
        skinDataJSON.put("CapeImageHeight", 0);
        JWSObject skinData = forgeSkinData(skinDataJSON);

        AsciiString chainData;
        try {
            chainData = new AsciiString(jsonMapper.writeValueAsBytes(jsonMapper.createObjectNode().set("chain", jsonMapper.createArrayNode().add(authData.serialize()))));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        LoginPacket loginPacket = new LoginPacket();
        loginPacket.setChainData(chainData);
        loginPacket.setSkinData(AsciiString.of(skinData.serialize()));
        loginPacket.setProtocolVersion(CODEC.getProtocolVersion());

        return loginPacket;
    }

}