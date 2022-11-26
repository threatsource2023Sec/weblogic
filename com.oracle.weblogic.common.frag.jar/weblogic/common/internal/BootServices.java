package weblogic.common.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.security.acl.UserInfo;

public interface BootServices extends Remote {
   T3ClientParams findOrCreateClientContext(String var1, UserInfo var2, int var3) throws RemoteException;
}
