package weblogic.io.common.internal;

import java.rmi.Remote;

public interface OneWayOutputClient extends Remote {
   void writeResult(int var1);

   void flushResult();

   void closeResult();

   void error(Exception var1);
}
