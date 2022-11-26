package weblogic.iiop;

import java.io.IOException;
import weblogic.iiop.ior.IOR;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.LocatorUtilities;

public class IiopConfigurationFacade {
   static boolean isIiopEnabled() {
      return IiopConfigurationFacade.Singleton.delegate.isIiopEnabled();
   }

   static AuthenticatedSubject getSecureConnectionDefaultSubject(AuthenticatedSubject kernelId) {
      return IiopConfigurationFacade.Singleton.delegate.getSecureConnectionDefaultSubject(kernelId);
   }

   static int getPendingResponseTimeout() {
      return IiopConfigurationFacade.Singleton.delegate.getPendingResponseTimeout();
   }

   static int getKeepAliveTimeout() {
      return IiopConfigurationFacade.Singleton.delegate.getKeepAliveTimeout();
   }

   static int getBackoffInterval() {
      return IiopConfigurationFacade.Singleton.delegate.getBackoffInterval();
   }

   static EndPoint createEndPoint(Connection connection) {
      return IiopConfigurationFacade.Singleton.delegate.createEndPoint(connection);
   }

   static void runAsynchronously(Runnable runnable) {
      IiopConfigurationFacade.Singleton.delegate.runAsynchronously(runnable);
   }

   static ServerChannel getServerChannel(AuthenticatedSubject kernelId, Protocol protocol, String channelName) throws IOException {
      return IiopConfigurationFacade.Singleton.delegate.getServerChannel(kernelId, protocol, channelName);
   }

   static ServerChannel getLocalServerChannel(Protocol protocol) {
      return IiopConfigurationFacade.Singleton.delegate.getLocalServerChannel(protocol);
   }

   static boolean mayLoadRemoteClass(IOR ior) {
      return IiopConfigurationFacade.Singleton.delegate.mayLoadRemoteClass(ior);
   }

   public static boolean isLocal(IOR ior) {
      return IiopConfigurationFacade.Singleton.delegate.isLocal(ior);
   }

   public static int getObjectId(IOR ior) {
      return IiopConfigurationFacade.Singleton.delegate.getObjectId(ior);
   }

   static Object getActivationID(IOR ior) {
      return IiopConfigurationFacade.Singleton.delegate.getActivationID(ior);
   }

   static HostID getHostID(IOR ior) {
      return IiopConfigurationFacade.Singleton.delegate.getHostID(ior);
   }

   public static int getSslListenPort() {
      return IiopConfigurationFacade.Singleton.delegate.getSslListenPort();
   }

   static boolean isSslChannelEnabled() {
      return IiopConfigurationFacade.Singleton.delegate.isSslChannelEnabled();
   }

   public static String[] getCipherSuites() {
      return IiopConfigurationFacade.Singleton.delegate.getCiphersuites();
   }

   public static boolean isClientCertificateEnforced() {
      return IiopConfigurationFacade.Singleton.delegate.isClientCertificateEnforced();
   }

   public static boolean isRemoteAnonymousRMIIIOPEnabled() {
      return IiopConfigurationFacade.Singleton.delegate.isRemoteAnonymousRMIIIOPEnabled();
   }

   private static class Singleton {
      private static IiopConfigurationDelegate delegate = (IiopConfigurationDelegate)LocatorUtilities.getService(IiopConfigurationDelegate.class);
   }
}
