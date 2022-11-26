package weblogic.iiop.server;

import java.io.InvalidClassException;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.Service;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import weblogic.corba.utils.CorbaUtilsDelegate;
import weblogic.iiop.EndPoint;
import weblogic.iiop.IIOPInputStream;
import weblogic.iiop.IIOPOutputStream;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.ProtocolHandlerIIOP;
import weblogic.iiop.Utils;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.utils.Utilities;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.oif.WebLogicObjectInputFilter;

@Service
@Rank(10)
public class ServerIiopProtocolFacade extends IiopProtocolFacade implements CorbaUtilsDelegate {
   public Class loadClass(String className, String remoteCodebase, ClassLoader loadingContext) throws ClassNotFoundException {
      return Utilities.loadClass(className, remoteCodebase, loadingContext);
   }

   protected boolean doIsServer() {
      return Kernel.isServer();
   }

   protected boolean doFastEquals(Object key1, Object key2) {
      return ((ObjectKey)key1).isLocalKey() && !Kernel.isServer() && key1.equals(key2);
   }

   protected Object doConvertToKey(Object storedKey) {
      if (storedKey instanceof ObjectKey) {
         return storedKey;
      } else if (storedKey instanceof byte[]) {
         return ObjectKey.create((byte[])((byte[])storedKey));
      } else {
         throw new IllegalArgumentException("doConvertKey passed " + storedKey);
      }
   }

   protected ServerIdentity doGetTargetForRead(Object key) {
      return ((ObjectKey)key).getTarget();
   }

   protected boolean doIsServerLocalObject(Object key) {
      return ((ObjectKey)key).isLocalKey() && Kernel.isServer();
   }

   protected void doWriteListenPoint(CorbaOutputStream out, Object key, ListenPoint address) {
      if (((ObjectKey)key).getTarget() != null) {
         address.writeForChannel(out, ((ObjectKey)key).getTarget());
      } else {
         address.write(out);
      }

   }

   protected boolean doMustReplaceAddress(Object key) {
      return ((ObjectKey)key).isLocalKey();
   }

   protected ListenPoint doGetReplacement(ListenPoint listenPoint, InputStream in) {
      return KernelStatus.isServer() ? listenPoint : this.getClientReplacement(listenPoint, in);
   }

   private ListenPoint getClientReplacement(ListenPoint listenPoint, InputStream in) {
      if (listenPoint.getPort() == this.getConnectedPort(in)) {
         listenPoint = new ListenPoint(listenPoint.getAddress(), -1);
      }

      return listenPoint;
   }

   private int getConnectedPort(InputStream in) {
      return this.getConnectedPort(((IIOPInputStream)in).getEndPoint());
   }

   private int getConnectedPort(EndPoint ep) {
      return ep == null ? -1 : ep.getConnection().getListenPoint().getConnectedPort();
   }

   protected ListenPoint doGetReplacement(ListenPoint listenPoint, OutputStream out, ServerIdentity target) {
      return KernelStatus.isServer() ? this.getServerReplacement(listenPoint, out, target) : this.getClientReplacement(listenPoint, out);
   }

   private ListenPoint getServerReplacement(ListenPoint listenPoint, OutputStream out, ServerIdentity target) {
      EndPoint endPoint = ((IIOPOutputStream)out).getEndPoint();
      ServerChannel outputChannel = this.getServerChannel(endPoint);
      if (endPoint != null && endPoint.getConnection() != null && target.isLocal()) {
         if (outputChannel != null) {
            listenPoint = this.createForChannel(listenPoint, outputChannel);
         }
      } else {
         ServerChannel serverChannel;
         if (this.isSendingNonLocalPointWithChannel(target, outputChannel)) {
            serverChannel = ServerChannelManager.findServerChannel(target, outputChannel.getProtocol(), outputChannel.getChannelName());
            if (serverChannel != null) {
               listenPoint = this.createForChannel(listenPoint, serverChannel);
            }
         } else if (this.hasNoAddressAndLocalTarget(listenPoint, target)) {
            serverChannel = ServerChannelManager.findLocalServerChannel(ProtocolHandlerIIOP.PROTOCOL_IIOP);
            listenPoint = serverChannel != null ? this.createForChannel(listenPoint, serverChannel) : this.createDisabledListenPoint();
         }
      }

      return listenPoint;
   }

   private ServerChannel getServerChannel(EndPoint endPoint) {
      return endPoint == null ? null : endPoint.getServerChannel();
   }

   private boolean isSendingNonLocalPointWithChannel(ServerIdentity target, ServerChannel serverChannel) {
      return serverChannel != null && target != null && !target.isLocal();
   }

   private ListenPoint createDisabledListenPoint() {
      return new ListenPoint("", 0);
   }

   private ListenPoint createForChannel(ListenPoint listenPoint, ServerChannel ch) {
      return new ListenPoint(ch.getPublicAddress(), getReplacementPort(listenPoint.getPort(), ch));
   }

   private boolean hasNoAddressAndLocalTarget(ListenPoint listenPoint, ServerIdentity target) {
      return listenPoint.getAddress() == null && target != null && target.isLocal();
   }

   private static int getReplacementPort(int port, ServerChannel serverChannel) {
      return port <= 0 && port != -1 ? 0 : serverChannel.getPublicPort();
   }

   private ListenPoint getClientReplacement(ListenPoint listenPoint, OutputStream out) {
      EndPoint ep = ((IIOPOutputStream)out).getEndPoint();
      if (ep != null && listenPoint.getPort() == -1) {
         listenPoint = new ListenPoint(listenPoint.getAddress(), ep.getConnection().getListenPoint().getConnectedPort());
      }

      return listenPoint;
   }

   public String getAnnotation(ClassLoader loadingContext) {
      return Utils.getAnnotation(loadingContext);
   }

   public void verifyClassPermitted(Class clz) {
      if (!WebLogicObjectInputFilter.isClassAllowed(clz)) {
         throw new BlacklistedClassException(clz.getName());
      }
   }

   public void checkLegacyBlacklistIfNeeded(String className) {
      try {
         WebLogicObjectInputFilter.checkLegacyBlacklistIfNeeded(className);
      } catch (InvalidClassException var3) {
         throw new BlacklistedClassException(className);
      }
   }

   protected CorbaInputStream doCreateInputStream(byte[] buffer) {
      return new IIOPInputStream(buffer);
   }

   protected CorbaInputStream doCreateInputStream(EndPoint endPoint, byte[] buffer) {
      return new IIOPInputStream(buffer, endPoint);
   }

   protected CorbaInputStream doCreateInputStream(Chunk head) {
      return new IIOPInputStream(head, false, (EndPoint)null);
   }

   protected CorbaOutputStream doCreateOutputStream() {
      return new IIOPOutputStream();
   }
}
