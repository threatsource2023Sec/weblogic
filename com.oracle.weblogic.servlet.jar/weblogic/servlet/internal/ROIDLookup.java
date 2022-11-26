package weblogic.servlet.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.cluster.replication.ROID;

public interface ROIDLookup extends Remote {
   ROID lookupROID(String var1, String var2, String var3) throws RemoteException;

   ROID lookupROID(String var1, String var2, String var3, String var4) throws RemoteException;

   void updateLastAccessTimes(ROID[] var1, long[] var2, long var3, String var5) throws RemoteException;

   void updateLastAccessTimes(String var1, ROID[] var2, long[] var3, long var4, String var6) throws RemoteException;

   void unregister(ROID var1, Object[] var2) throws RemoteException;

   boolean isAvailableInOtherCtx(String var1, String var2, String var3, String var4, String var5) throws RemoteException;
}
