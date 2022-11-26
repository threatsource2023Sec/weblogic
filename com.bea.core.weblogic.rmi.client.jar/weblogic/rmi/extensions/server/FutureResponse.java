package weblogic.rmi.extensions.server;

import java.rmi.RemoteException;
import weblogic.rmi.spi.MsgOutput;

public interface FutureResponse {
   MsgOutput getMsgOutput() throws RemoteException;

   void send() throws RemoteException;

   void sendThrowable(Throwable var1);
}
