package weblogic.io.common.internal;

import java.rmi.Remote;

public interface OneWayInputServer extends Remote {
   void read(int var1);

   void skip(long var1);

   void close();
}
