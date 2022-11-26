package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.io.InputStream;

public interface Connection {
   void send(Message var1) throws IOException;

   boolean isDead();

   ServerConfigurationInformation getConfiguration();

   void handleIncomingMessage(InputStream var1) throws IOException;

   void close();

   String getServerId();

   void handleBootStrapMessage(InputStream var1) throws IOException;

   boolean isBootStrapped();
}
