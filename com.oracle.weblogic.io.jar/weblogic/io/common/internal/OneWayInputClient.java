package weblogic.io.common.internal;

import java.rmi.Remote;

public interface OneWayInputClient extends Remote {
   void readResult(int var1, byte[] var2);

   void skipResult(long var1);

   void error(Exception var1);
}
