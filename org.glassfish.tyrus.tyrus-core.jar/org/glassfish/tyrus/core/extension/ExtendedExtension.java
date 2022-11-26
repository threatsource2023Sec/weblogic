package org.glassfish.tyrus.core.extension;

import java.util.List;
import java.util.Map;
import javax.websocket.Extension;
import org.glassfish.tyrus.core.frame.Frame;

public interface ExtendedExtension extends Extension {
   Frame processIncoming(ExtensionContext var1, Frame var2);

   Frame processOutgoing(ExtensionContext var1, Frame var2);

   List onExtensionNegotiation(ExtensionContext var1, List var2);

   void onHandshakeResponse(ExtensionContext var1, List var2);

   void destroy(ExtensionContext var1);

   public interface ExtensionContext {
      Map getProperties();
   }
}
