package javax.websocket;

import java.nio.ByteBuffer;

public interface PongMessage {
   ByteBuffer getApplicationData();
}
