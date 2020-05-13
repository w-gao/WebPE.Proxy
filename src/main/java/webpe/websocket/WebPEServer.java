/*
 * Copyright (c) 2019-2020 w-gao
 * All Rights Reserved.
 */

package webpe.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import webpe.WebPEProxy;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * WebPEServer
 *
 * WebSocket connection interface. Implementation of the WebSocketServer to handle all
 * bi-directional communication between the server and clients.
 *
 * @author William Gao
 */
public class WebPEServer extends WebSocketServer {

    protected final String ip;
    protected final int port;

//    protected ConcurrentLinkedQueue<byte[]> externalQueue;
//    protected ConcurrentLinkedQueue<byte[]> internalQueue;

    private final WebPEProxy proxy;
    private final Map<String, WebSocket> connections = new ConcurrentHashMap<>();

    public WebPEServer(WebPEProxy proxy, int port) {
        this(proxy, port, "0.0.0.0");
    }

    public WebPEServer(WebPEProxy proxy, int port, String ip) {

        super(new InetSocketAddress(ip, port));

        this.proxy = proxy;

        this.port = port;
        if (port < 1 || port > 65536) {
            throw new IllegalArgumentException("Invalid port range");
        }

        this.ip = ip;

        // calls super.start()
        this.start();
    }


    /** Called after an opening handshake has been performed and the given websocket is ready to be written on.
     * @param conn The <tt>WebSocket</tt> instance this event is occuring on.
     * @param handshake The handshake of the websocket instance
     */
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

        System.out.println("[WebPE] " + conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

        this.connections.put(this.getIdentifier(conn), conn);
    }


    /**
     * Called after the websocket connection has been closed.
     *
     * @param conn The <tt>WebSocket</tt> instance this event is occuring on.
     * @param code
     *            The codes can be looked up here: {@link CloseFrame}
     * @param reason
     *            Additional information string
     * @param remote
     *            Returns whether or not the closing of the connection was initiated by the remote host.
     **/
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

        System.out.println("[WebPE] " + conn + " disconnected");

        this.proxy.closeSession(this.getIdentifier(conn), "Websocket close" + (reason.equals("") ? "" : " : " + reason));
        this.connections.remove(this.getIdentifier(conn));
    }


    /**
     * Callback for string messages received from the remote host
     *
     * @see #onMessage(WebSocket, ByteBuffer)
     * @param conn The <tt>WebSocket</tt> instance this event is occuring on.
     * @param message The UTF-8 decoded message that was received.
     **/
    public void onMessage(WebSocket conn, String message) {

        System.out.println("[WebPE] New ProxyMessage from " + this.getIdentifier(conn)
                + ": " + message);

        this.proxy.handlePacket(this.getIdentifier(conn), message);
    }

    /**
     * Callback for binary messages received from the remote host
     *
     * @see #onMessage(WebSocket, ByteBuffer)
     *
     *  @param conn
     *            The <tt>WebSocket</tt> instance this event is occurring on.
     * @param message
     *            The binary message that was received.
     **/
    public void onMessage(WebSocket conn, ByteBuffer message) {

//        this.logger.notice(Binary.bytesToHexString(message.array()));
//        this.logger.notice("Received message ^");

        System.out.println("New message from : " + this.getIdentifier(conn));
        this.proxy.handlePacket(this.getIdentifier(conn), message.array());
    }


    /**
     * Called when errors occurs. If an error causes the websocket connection to fail {@link #onClose(WebSocket, int, String, boolean)} will be called additionally.<br>
     * This method will be called primarily because of IO or protocol errors.<br>
     * If the given exception is an RuntimeException that probably means that you encountered a bug.<br>
     *
     * @param conn Can be null if there error does not belong to one specific websocket. For example if the servers port could not be bound.
     * @param ex The exception causing this error
     **/
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            System.err.println("[WebPE] WebSocket client connection exception ");
        } else {
            // some errors like port binding failed may not be assignable to a specific websocket

            System.err.println("[WebPE] WebSocket server exception ");
        }
        ex.printStackTrace();
    }


    /**
     * Called when the server started up successfully.
     *
     * If any error occured, onError is called instead.
     */
    public void onStart() {
        System.out.println("[WebPE] WebSocket Server started on port " + this.port + "!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }


    public void sendMessage(String identifier, String payload) {

        WebSocket ws = this.connections.get(identifier);

        if (ws != null) {
            System.out.println("[WebPE] sending " + payload + " to " + identifier);
            ws.send(payload);
        } else {
            System.out.println("[WebPE] sendPacket NULL identifier!");
        }
    }

    /**
     * Send a payload to the specific identifier connection
     */
    public void sendPacket(String identifier, byte[] payload) {

        WebSocket ws = this.connections.get(identifier);

        if (ws != null) {

//            byte[][] fragmentedPackets = Binary.splitBytes(payload, 1492);
//            for (int i = 0; i < fragmentedPackets.length; i++) {
//                ws.sendFragmentedFrame(Opcode.BINARY, ByteBuffer.wrap(fragmentedPackets[i]), i == fragmentedPackets.length - 1);
//            }

            System.out.println("[WebPE] sending " + payload.length + " bytes to " + identifier);
            ws.send(payload);

        } else {
            System.out.println("[WebPE] sendPacket NULL identifier!");
        }
    }

    /**
     * Close the connection due to server initiated closure
     */
    public void closeWebSocket(String identifier, String reason) {

        WebSocket ws = this.connections.get(identifier);

        if (ws != null) {

            ws.close(CloseFrame.NORMAL, reason);

        } else {
            System.out.println("[WebPE] closeWebSocket NULL identifier!");
        }
    }

    private String getIdentifier(WebSocket conn) {
//        return conn.getRemoteSocketAddress().getAddress() + ":" + conn.getRemoteSocketAddress().getPort();
        return conn.getRemoteSocketAddress().hashCode() + "";
    }
}
