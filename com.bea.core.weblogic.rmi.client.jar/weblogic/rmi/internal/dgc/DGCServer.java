package weblogic.rmi.internal.dgc;

import java.rmi.Remote;

public interface DGCServer extends Remote {
   int DEFAULT_PERIOD_LENGTH_MILLIS = 60000;
   int DISABLE_HEARTBEATS = 0;

   void enroll(int[] var1);

   void unenroll(int[] var1);

   void renewLease(int[] var1);
}
