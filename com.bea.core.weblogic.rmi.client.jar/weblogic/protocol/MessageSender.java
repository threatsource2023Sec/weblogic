package weblogic.protocol;

import java.io.IOException;

public interface MessageSender {
   void send(OutgoingMessage var1) throws IOException;
}
