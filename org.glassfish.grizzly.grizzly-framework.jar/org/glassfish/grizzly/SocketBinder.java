package org.glassfish.grizzly;

import java.io.IOException;
import java.net.SocketAddress;

public interface SocketBinder {
   Connection bind(int var1) throws IOException;

   Connection bind(String var1, int var2) throws IOException;

   Connection bind(String var1, int var2, int var3) throws IOException;

   Connection bind(String var1, PortRange var2, int var3) throws IOException;

   Connection bind(SocketAddress var1) throws IOException;

   Connection bind(SocketAddress var1, int var2) throws IOException;

   Connection bindToInherited() throws IOException;

   void unbind(Connection var1);

   void unbindAll();
}
