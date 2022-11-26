package javax.faces.push;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface PushContext extends Serializable {
   String ENABLE_WEBSOCKET_ENDPOINT_PARAM_NAME = "javax.faces.ENABLE_WEBSOCKET_ENDPOINT";
   String WEBSOCKET_ENDPOINT_PORT_PARAM_NAME = "javax.faces.WEBSOCKET_ENDPOINT_PORT";
   String URI_PREFIX = "/javax.faces.push";

   Set send(Object var1);

   Set send(Object var1, Serializable var2);

   Map send(Object var1, Collection var2);
}
