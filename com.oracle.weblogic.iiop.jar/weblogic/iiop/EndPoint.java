package weblogic.iiop;

import java.rmi.RemoteException;
import org.omg.SendingContext.RunTime;
import weblogic.common.internal.PeerInfo;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.csi.ClientSecurity;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.messages.Message;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.RequestMessage;
import weblogic.iiop.messages.SequencedRequestMessage;
import weblogic.iiop.messages.ServiceContextMessage;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.io.Chunk;
import weblogic.workarea.WorkContextOutput;

public interface EndPoint extends weblogic.rmi.spi.EndPoint {
   int getNextRequestID();

   void dispatch(Chunk var1);

   CorbaOutputStream createOutputStream();

   void send(CorbaOutputStream var1) throws RemoteException;

   Message sendReceive(SequencedRequestMessage var1, int var2) throws RemoteException;

   Message sendReceive(SequencedRequestMessage var1) throws RemoteException;

   void sendRequest(SequencedRequestMessage var1, int var2) throws RemoteException;

   RequestMessage createRequest(IOR var1, String var2, boolean var3);

   void cleanupPendingResponses(Throwable var1);

   ClientSecurity getClientSecurity();

   SequencedRequestMessage getPendingResponse(int var1);

   SequencedRequestMessage removePendingResponse(int var1);

   void registerPendingResponse(SequencedRequestMessage var1);

   boolean hasPendingResponses();

   void incrementPendingRequests();

   void decrementPendingRequests();

   void handleProtocolException(Message var1, Throwable var2);

   boolean isSecure();

   boolean supportsForwarding();

   int getMinorVersion();

   IOR getCurrentIor(IOR var1, long var2) throws RemoteException;

   WorkContextOutput createWorkContextOutput();

   Connection getConnection();

   void setCodeSets(int var1, int var2);

   int getWcharCodeSet();

   int getCharCodeSet();

   RunTime getRemoteCodeBase();

   void setRemoteCodeBaseIOR(IOR var1);

   PeerInfo getPeerInfo();

   void setPeerInfo(PeerInfo var1);

   void setFlag(int var1);

   boolean getFlag(int var1);

   void setCredentials(RequestMessage var1, AuthenticatedSubject var2);

   void setMessageServiceContext(ServiceContextMessage var1, ServiceContext var2);

   ServiceContext getMessageServiceContext(ServiceContextMessage var1, int var2);

   void setOutboundRequestTxContext(RequestMessage var1, Object var2);

   Object getInboundResponseTxContext(ReplyMessage var1);

   IOR getCodeBaseIor();
}
