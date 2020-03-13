package dev.nestedvar.Discord.Websockets;


import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/quiver/update/{guild}/{settings}")
public class WebsocketServer {

    public Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("guild") String guild) throws IOException {
        this.session = session;
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

    }

}
