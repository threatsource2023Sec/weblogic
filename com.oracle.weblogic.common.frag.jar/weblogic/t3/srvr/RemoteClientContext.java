package weblogic.t3.srvr;

import java.rmi.Remote;
import weblogic.common.internal.T3ClientParams;
import weblogic.rmi.RemoteException;

public interface RemoteClientContext extends Remote {
   void setHardDisconnectTimeoutMins(int var1) throws RemoteException;

   void setSoftDisconnectTimeoutMins(int var1) throws RemoteException;

   void setIdleDisconnectTimeoutMins(int var1) throws RemoteException;

   void setVerbose(boolean var1) throws RemoteException;

   T3ClientParams getParams() throws RemoteException;
}
