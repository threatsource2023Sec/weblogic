package weblogic.iiop.server;

import java.io.IOException;
import java.util.Iterator;
import javax.security.auth.login.LoginException;
import org.jvnet.hk2.annotations.Service;
import weblogic.iiop.Connection;
import weblogic.iiop.EndPoint;
import weblogic.iiop.HostIDImpl;
import weblogic.iiop.IiopConfigurationDelegate;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOR;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rmi.client.facades.RmiClientSecurityFacade;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.spi.HostID;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.LocatorUtilities;
import weblogic.work.WorkManagerFactory;

@Service
public class IiopConfigurationServerDelegate implements IiopConfigurationDelegate {
   public int getSslListenPort() {
      return getSSLMbean().getListenPort();
   }

   public boolean isClientCertificateEnforced() {
      return getSSLMbean().isClientCertificateEnforced();
   }

   public String[] getCiphersuites() {
      return getSSLMbean().getCiphersuites();
   }

   private static SSLMBean getSSLMbean() {
      return getRuntimeAccess().getServer().getSSL();
   }

   public boolean isSslChannelEnabled() {
      return Kernel.isServer() && ChannelHelper.isSSLChannelEnabled(getRuntimeAccess().getServer());
   }

   public HostID getHostID(IOR ior) {
      ObjectKey objectKey = ObjectKey.getObjectKey(ior);
      return (HostID)(objectKey.isWLSKey() && objectKey.getTarget() != null ? objectKey.getTarget() : new HostIDImpl(ior.getProfile().getHost(), ior.getProfile().getPort()));
   }

   public Object getActivationID(IOR ior) {
      return ObjectKey.getObjectKey(ior).getActivationID();
   }

   public int getObjectId(IOR ior) {
      ObjectKey key = ObjectKey.getObjectKey(ior);
      return key.isWLSKey() ? key.getObjectID() : -1;
   }

   public boolean mayLoadRemoteClass(IOR ior) {
      return !ior.isRemote() || ior.getTypeId() == null || ior.getTypeId().isIDLType() || ObjectKey.getObjectKey(ior).isRepositoryIdAnInterface();
   }

   public boolean isIiopEnabled() {
      return !Kernel.isServer() || getRuntimeAccess().getServer().isIIOPEnabled();
   }

   private static RuntimeAccess getRuntimeAccess() {
      return (RuntimeAccess)LocatorUtilities.getService(RuntimeAccess.class);
   }

   public AuthenticatedSubject getSecureConnectionDefaultSubject(AuthenticatedSubject kernelId) {
      return this.useAnonymousConnectionDefaultSubject() ? RmiSecurityFacade.getAnonymousSubject() : this.getDefaultIiopSubject(kernelId);
   }

   private boolean useAnonymousConnectionDefaultSubject() {
      return !Kernel.isServer() || getRuntimeAccess().getServer().getDefaultIIOPUser() == null;
   }

   private AuthenticatedSubject getDefaultIiopSubject(AuthenticatedSubject kernelId) {
      try {
         SimpleCallbackHandler handler = new SimpleCallbackHandler(getRuntimeAccess().getServer().getDefaultIIOPUser(), getRuntimeAccess().getServer().getDefaultIIOPPassword());
         return RmiSecurityFacade.getPrincipalAuthenticator(kernelId, "weblogicDEFAULT").authenticate(handler);
      } catch (LoginException var3) {
         return RmiSecurityFacade.getAnonymousSubject();
      }
   }

   public int getPendingResponseTimeout() {
      return Kernel.getConfig().getIdlePeriodsUntilTimeout();
   }

   public int getKeepAliveTimeout() {
      return !Kernel.isServer() ? Kernel.getConfig().getPeriodLength() : 0;
   }

   public int getBackoffInterval() {
      return Kernel.getConfig().getSocketReaderTimeoutMinMillis();
   }

   public void runAsynchronously(Runnable runnable) {
      WorkManagerFactory.getInstance().getSystem().schedule(runnable);
   }

   public EndPoint createEndPoint(Connection connection) {
      return new ServerEndPointImpl(connection);
   }

   public boolean isLocal(IOR ior) {
      return ior.hasIOPProfile() && (ObjectKey.getObjectKey(ior).isLocalKey() || this.containsALocalIor((ClusterComponent)ior.getProfile().getComponent(1111834883)));
   }

   private boolean containsALocalIor(ClusterComponent cc) {
      if (cc == null) {
         return false;
      } else {
         Iterator var2 = cc.getIORs().iterator();

         IOR ior;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            ior = (IOR)var2.next();
         } while(!this.isLocal(ior));

         return true;
      }
   }

   public ServerChannel getServerChannel(AuthenticatedSubject kernelId, Protocol protocol, String channelName) throws IOException {
      if (protocol == null) {
         return null;
      } else if (!inAdminState(kernelId)) {
         return ServerChannelManager.findOutboundServerChannel(protocol, channelName);
      } else if (ProtocolManager.getDefaultAdminProtocol().toByte() != protocol.toByte()) {
         throw new IOException(String.format("Attempted to use %S as the admin protocol, but only %S is supported.", protocol, ProtocolManager.getDefaultAdminProtocol()));
      } else {
         return ServerChannelManager.findOutboundServerChannel(ProtocolHandlerAdmin.PROTOCOL_ADMIN, channelName);
      }
   }

   private static boolean inAdminState(AuthenticatedSubject kernelId) {
      return KernelStatus.isServer() && kernelId.getQOS() == 103 && RmiClientSecurityFacade.getCurrentSubject(kernelId) == kernelId;
   }

   public ServerChannel getLocalServerChannel(Protocol protocol) {
      return ServerChannelManager.findLocalServerChannel(protocol);
   }

   public boolean isRemoteAnonymousRMIIIOPEnabled() {
      if (!Kernel.isServer()) {
         return true;
      } else {
         String enabled = System.getProperty("weblogic.security.remoteAnonymousRMIIIOPEnabled");
         if (enabled != null) {
            return Boolean.parseBoolean(enabled);
         } else {
            DomainMBean domain = getRuntimeAccess().getDomain();
            SecurityConfigurationMBean sec = domain.getSecurityConfiguration();
            return sec.isRemoteAnonymousRMIIIOPEnabled();
         }
      }
   }
}
