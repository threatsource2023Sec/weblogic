package weblogic.io.common.internal;

import java.rmi.Remote;

public interface OneWayOutputServer extends Remote {
   void write(int var1, byte[] var2);

   void flush(int var1);

   void close();
}
