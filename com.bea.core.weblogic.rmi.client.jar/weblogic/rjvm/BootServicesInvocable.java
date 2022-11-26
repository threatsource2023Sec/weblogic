package weblogic.rjvm;

import java.rmi.RemoteException;

public interface BootServicesInvocable {
   void setConnectionInfo(MsgAbbrevJVMConnection var1);

   void invoke(RemoteRequest var1) throws RemoteException;
}
