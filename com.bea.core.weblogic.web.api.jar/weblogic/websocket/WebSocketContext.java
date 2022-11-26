package weblogic.websocket;

import java.util.Set;
import javax.servlet.ServletContext;

/** @deprecated */
@Deprecated
public interface WebSocketContext {
   ServletContext getServletContext();

   String[] getPathPatterns();

   int getTimeoutSecs();

   void setTimeoutSecs(int var1);

   int getMaxMessageSize();

   void setMaxMessageSize(int var1);

   int getMaxConnections();

   void setMaxConnections(int var1);

   Set getWebSocketConnections();

   Set getSupportedVersions();
}
