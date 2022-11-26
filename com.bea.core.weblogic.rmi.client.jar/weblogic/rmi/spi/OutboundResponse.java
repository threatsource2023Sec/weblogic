package weblogic.rmi.spi;

import java.io.IOException;
import java.rmi.RemoteException;
import weblogic.rmi.cluster.PiggybackResponse;

public interface OutboundResponse {
   MsgOutput getMsgOutput() throws RemoteException;

   void send() throws RemoteException;

   void sendThrowable(Throwable var1);

   void close() throws IOException;

   void transferThreadLocalContext(InboundRequest var1) throws IOException;

   void setTxContext(Object var1) throws RemoteException;

   void setPiggybackResponse(PiggybackResponse var1) throws IOException;

   void setContext(int var1, Object var2) throws IOException;
}
