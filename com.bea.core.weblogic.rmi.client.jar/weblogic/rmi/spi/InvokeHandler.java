package weblogic.rmi.spi;

import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;

public interface InvokeHandler {
   void invoke(RuntimeMethodDescriptor var1, InboundRequest var2, OutboundResponse var3) throws Exception;
}
