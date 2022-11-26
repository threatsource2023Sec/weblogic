package weblogic.security.acl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.security.acl.internal.AuthenticatedUser;

public interface SecurityService extends Remote {
   String name = "weblogic/security/SecurityManager";

   AuthenticatedUser authenticate(UserInfo var1) throws RemoteException;
}
