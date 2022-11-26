package javax.websocket;

import java.util.List;
import java.util.Map;

public interface EndpointConfig {
   List getEncoders();

   List getDecoders();

   Map getUserProperties();
}
