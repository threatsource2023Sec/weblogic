package weblogic.iiop.server;

import org.omg.CORBA.portable.InputStream;

public interface InboundRequest extends weblogic.rmi.spi.InboundRequest {
   InputStream getInputStream();

   String getMethod();
}
