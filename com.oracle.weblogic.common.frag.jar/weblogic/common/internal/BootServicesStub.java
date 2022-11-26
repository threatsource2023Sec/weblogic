package weblogic.common.internal;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import weblogic.common.CommonLogger;
import weblogic.common.WLObjectInput;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RequestStream;
import weblogic.rjvm.Response;
import weblogic.security.acl.SecurityService;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedUser;

public class BootServicesStub implements BootServices, SecurityService {
   private static int ID = 1;
   private final String partitionName;
   /** @deprecated */
   @Deprecated
   private final String partitionURL;
   private final ServerChannel serverChannel;
   private RJVM rjvm;
   private Protocol protocol;

   public static WLObjectInput getMsg(Response r) throws RemoteException {
      Throwable problem = r.getThrowable();
      if (problem == null) {
         return r.getMsg();
      } else if (problem instanceof RemoteException) {
         throw (RemoteException)problem;
      } else if (problem instanceof RuntimeException) {
         throw (RuntimeException)problem;
      } else if (problem instanceof Error) {
         throw (Error)problem;
      } else {
         CommonLogger.logUnexpectedProblem(problem);
         throw new ClassCastException("Expected RemoteException, RuntimeException, or Error but received: '" + problem.toString() + '\'');
      }
   }

   public BootServicesStub(RJVM rjvm, Protocol protocol) {
      this(rjvm, protocol, "DOMAIN");
   }

   public BootServicesStub(RJVM rjvm, Protocol protocol, String partitionName) {
      this(rjvm, protocol, (String)null, partitionName, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public BootServicesStub(RJVM rjvm, Protocol protocol, String channelName, String partitionName, String partitionURL) {
      this.rjvm = rjvm;
      this.protocol = protocol;
      this.serverChannel = channelName == null ? ServerChannelManager.findOutboundServerChannel(protocol) : ServerChannelManager.findLocalServerChannel(channelName);
      this.partitionName = partitionName;
      this.partitionURL = partitionURL;
   }

   public AuthenticatedUser authenticate(UserInfo ui) throws RemoteException {
      try {
         RequestStream msg = this.rjvm.getRequestStreamForDefaultUser(this.protocol, this.partitionName, this.partitionURL);
         msg.marshalCustomCallData();
         msg.writeByte(1);
         msg.writeObject(ui);
         return (AuthenticatedUser)getMsg(msg.sendRecv(ID, this.protocol.getQOS())).readObjectWLValidated(AuthenticatedUser.class);
      } catch (IOException var4) {
         throw new UnexpectedException("Marshalling: ", var4);
      } catch (ClassNotFoundException var5) {
         throw new UnexpectedException("Marshalling: ", var5);
      }
   }

   public T3ClientParams findOrCreateClientContext(String workspace, UserInfo userInfo, int idleCallbackID) throws RemoteException {
      try {
         RequestStream msg = this.rjvm.getRequestStream(this.serverChannel, this.partitionName, this.partitionURL);
         msg.marshalCustomCallData();
         msg.writeByte(2);
         msg.writeString(workspace);
         msg.writeObjectWL(userInfo);
         msg.writeInt(idleCallbackID);
         msg.writeByte(this.protocol.getQOS());
         return (T3ClientParams)getMsg(msg.sendRecv(ID, this.protocol.getQOS())).readObjectWLValidated(T3ClientParams.class);
      } catch (IOException var6) {
         throw new UnexpectedException("Marshalling: ", var6);
      } catch (ClassNotFoundException var7) {
         throw new UnexpectedException("Marshalling: ", var7);
      }
   }
}
