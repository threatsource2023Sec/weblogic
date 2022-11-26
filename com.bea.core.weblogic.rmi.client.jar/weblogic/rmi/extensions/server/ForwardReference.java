package weblogic.rmi.extensions.server;

import weblogic.rmi.spi.InboundResponse;

public interface ForwardReference extends RemoteReference {
   void handleRedirect(InboundResponse var1) throws Exception;
}
