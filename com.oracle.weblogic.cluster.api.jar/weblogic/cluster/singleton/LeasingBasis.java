package weblogic.cluster.singleton;

import java.io.IOException;
import java.util.Set;

public interface LeasingBasis {
   boolean acquire(String var1, String var2, int var3) throws IOException, LeasingException;

   void release(String var1, String var2) throws IOException;

   String findOwner(String var1) throws IOException;

   String findPreviousOwner(String var1) throws IOException;

   int renewLeases(String var1, Set var2, int var3) throws IOException, MissedHeartbeatException;

   int renewAllLeases(int var1, String var2) throws IOException, MissedHeartbeatException;

   String[] findExpiredLeases(int var1) throws IOException;
}
