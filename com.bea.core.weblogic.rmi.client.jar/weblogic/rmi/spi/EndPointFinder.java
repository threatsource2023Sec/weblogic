package weblogic.rmi.spi;

import java.io.IOException;

public interface EndPointFinder {
   boolean claimHostID(HostID var1);

   boolean claimServerURL(String var1);

   EndPoint findOrCreateEndPoint(HostID var1);

   EndPoint findEndPoint(HostID var1);

   EndPoint findOrCreateEndPoint(String var1) throws IOException;

   EndPoint findEndPoint(String var1) throws IOException;
}
