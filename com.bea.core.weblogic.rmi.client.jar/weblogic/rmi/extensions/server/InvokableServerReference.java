package weblogic.rmi.extensions.server;

import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.InvokeHandler;

public interface InvokableServerReference extends ServerReference {
   void dispatch(InboundRequest var1, InvokeHandler var2);
}
