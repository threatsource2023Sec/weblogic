package weblogic.rmi.spi;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.RemoteException;
import weblogic.rmi.cluster.Version;

public interface OutboundRequest {
   void marshalArgs(Object[] var1) throws MarshalException;

   MsgOutput getMsgOutput() throws RemoteException;

   EndPoint getEndPoint();

   void sendOneWay() throws RemoteException;

   InboundResponse sendReceive() throws Throwable;

   void sendAsync(AsyncCallback var1) throws RemoteException;

   void setTimeOut(int var1);

   void transferThreadLocalContext() throws IOException;

   void setTxContext(Object var1) throws RemoteException;

   void setReplicaInfo(Version var1) throws IOException;

   void setActivationID(Object var1) throws IOException;

   void setContext(int var1, Object var2) throws IOException;
}
