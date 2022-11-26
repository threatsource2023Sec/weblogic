package weblogic.servlet.internal;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface Replicator {
   void updateROIDLastAccessTimes(String var1, Serializable[] var2, long[] var3, String var4);

   Serializable lookupROID(String var1, String var2, String var3, String var4) throws RemoteException;

   Serializable lookupROID(String var1, String var2, String var3, String var4, boolean var5) throws RemoteException;

   void putPrimary(String var1, Serializable var2, String var3);

   void putSecondary(String var1, Serializable var2, String var3);

   Serializable getPrimary(String var1);

   Serializable getSecondary(String var1);

   void removePrimary(String var1, String var2);

   void removeSecondary(String var1, String var2);

   Serializable[] getSecondaryIds();

   boolean isAvailableInOtherCtx(String var1, String var2, String var3, String var4, String var5) throws RemoteException;
}
