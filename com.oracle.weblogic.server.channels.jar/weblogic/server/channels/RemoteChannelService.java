package weblogic.server.channels;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.protocol.ChannelList;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;

@Contract
public interface RemoteChannelService extends Remote {
   ServerChannel getServerChannel(String var1) throws RemoteException;

   String getDefaultURL() throws RemoteException;

   String getAdministrationURL() throws RemoteException;

   String getURL(String var1) throws RemoteException;

   ServerIdentity getServerIdentity() throws RemoteException;

   String registerServer(String var1, ChannelList var2) throws RemoteException;

   void updateServer(String var1, ChannelList var2) throws RemoteException;

   ChannelList getChannelList(ServerIdentity var1) throws RemoteException;

   void removeChannelList(ServerIdentity var1) throws RemoteException;

   String[] getConnectedServers() throws RemoteException;
}
