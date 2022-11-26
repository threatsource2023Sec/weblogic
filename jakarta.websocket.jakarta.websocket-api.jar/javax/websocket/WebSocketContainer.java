package javax.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

public interface WebSocketContainer {
   long getDefaultAsyncSendTimeout();

   void setAsyncSendTimeout(long var1);

   Session connectToServer(Object var1, URI var2) throws DeploymentException, IOException;

   Session connectToServer(Class var1, URI var2) throws DeploymentException, IOException;

   Session connectToServer(Endpoint var1, ClientEndpointConfig var2, URI var3) throws DeploymentException, IOException;

   Session connectToServer(Class var1, ClientEndpointConfig var2, URI var3) throws DeploymentException, IOException;

   long getDefaultMaxSessionIdleTimeout();

   void setDefaultMaxSessionIdleTimeout(long var1);

   int getDefaultMaxBinaryMessageBufferSize();

   void setDefaultMaxBinaryMessageBufferSize(int var1);

   int getDefaultMaxTextMessageBufferSize();

   void setDefaultMaxTextMessageBufferSize(int var1);

   Set getInstalledExtensions();
}
