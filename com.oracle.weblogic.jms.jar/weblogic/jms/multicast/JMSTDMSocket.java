package weblogic.jms.multicast;

import java.io.IOException;
import java.net.InetAddress;

public interface JMSTDMSocket {
   void send(byte[] var1, int var2, InetAddress var3, int var4, byte var5) throws IOException;

   void joinGroup(InetAddress var1) throws IOException;

   void leaveGroup(InetAddress var1) throws IOException;

   void setSoTimeout(int var1) throws IOException;

   void setTTL(byte var1) throws IOException;

   int receive(byte[] var1) throws IOException;

   void close();
}
