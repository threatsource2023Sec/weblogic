package weblogic.iiop.server;

import org.omg.CORBA.portable.ResponseHandler;

public interface OutboundResponse extends weblogic.rmi.spi.OutboundResponse {
   ResponseHandler createResponseHandler(InboundRequest var1);
}
