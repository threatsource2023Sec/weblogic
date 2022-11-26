package weblogic.rjvm;

import java.io.IOException;
import java.rmi.RemoteException;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.spi.EndPoint;
import weblogic.security.acl.internal.AuthenticatedUser;

public interface RJVM extends EndPoint {
   JVMID getID();

   void setUser(String var1, AuthenticatedUser var2);

   AuthenticatedUser getUser(String var1);

   void addPeerGoneListener(PeerGoneListener var1);

   void removePeerGoneListener(PeerGoneListener var1);

   MsgAbbrevOutputStream getRequestStream(ServerChannel var1) throws IOException;

   MsgAbbrevOutputStream getRequestStream(String var1) throws IOException;

   MsgAbbrevOutputStream getRequestStream(ServerChannel var1, String var2, String var3) throws IOException;

   MsgAbbrevOutputStream getResponseStream(ServerChannel var1, byte var2, String var3) throws IOException;

   MsgAbbrevOutputStream getRequestStreamForDefaultUser(Protocol var1, String var2, String var3) throws IOException;

   InvokableFinder getFinder();

   void disconnect();

   void messageReceived();

   Object getColocatedServices() throws RemoteException;

   ResponseImpl removePendingResponse(int var1);

   int getPeerChannelMaxMessageSize();
}
