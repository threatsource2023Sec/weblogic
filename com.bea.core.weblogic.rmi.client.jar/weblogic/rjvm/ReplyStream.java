package weblogic.rjvm;

import java.rmi.RemoteException;
import weblogic.common.WLObjectOutput;

public interface ReplyStream extends WLObjectOutput {
   void send() throws RemoteException;

   void sendThrowable(Throwable var1);

   void setTxContext(Object var1) throws RemoteException;
}
