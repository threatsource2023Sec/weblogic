package weblogic.rjvm;

import java.io.IOException;
import java.rmi.RemoteException;
import weblogic.common.WLObjectOutput;

public interface RequestStream extends WLObjectOutput {
   void sendOneWay(int var1) throws RemoteException;

   Response sendRecv(int var1) throws RemoteException;

   void sendAsync(int var1, ResponseListener var2) throws RemoteException;

   void sendOneWay(int var1, byte var2) throws RemoteException;

   Response sendRecv(int var1, byte var2) throws RemoteException;

   void sendAsync(int var1, ResponseListener var2, byte var3) throws RemoteException;

   void setTxContext(Object var1) throws RemoteException;

   void setTimeOut(int var1);

   void marshalCustomCallData() throws IOException;
}
