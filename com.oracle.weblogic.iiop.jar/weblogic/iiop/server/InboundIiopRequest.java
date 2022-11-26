package weblogic.iiop.server;

import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.RuntimeDescriptor;

public interface InboundIiopRequest extends InboundRequest {
   void registerAsPending();

   RuntimeMethodDescriptor getRuntimeMethodDescriptor(RuntimeDescriptor var1);

   weblogic.rmi.spi.OutboundResponse getOutboundResponse();

   boolean isResponseExpected();
}
