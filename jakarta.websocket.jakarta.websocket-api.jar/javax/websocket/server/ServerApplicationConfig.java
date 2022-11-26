package javax.websocket.server;

import java.util.Set;

public interface ServerApplicationConfig {
   Set getEndpointConfigs(Set var1);

   Set getAnnotatedEndpointClasses(Set var1);
}
