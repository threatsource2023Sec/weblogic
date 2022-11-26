package weblogic.rjvm;

import java.rmi.RemoteException;

public interface RemoteInvokable {
   void invoke(RemoteRequest var1) throws RemoteException;
}
