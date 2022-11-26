package weblogic.protocol;

import java.lang.annotation.Annotation;
import java.util.List;
import javax.naming.NamingException;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.rmi.extensions.ConnectMonitor;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.channels.api.ChannelRegistrationService;

public class ConnectMonitorFactory {
   public static ConnectMonitor getConnectMonitor() {
      return ConnectMonitorFactory.ConnectMonitorInstance.INSTANCE;
   }

   public static void registerForever(ServerEnvironment env) throws NamingException {
      ConnectMonitorFactory.ChannelRegistrationServiceInstance.INSTANCE.registerEnvironmentForever(env);
   }

   public static void registerForever(List envList) throws NamingException {
      ConnectMonitorFactory.ChannelRegistrationServiceInstance.INSTANCE.registerEnvironmentForever(envList);
   }

   public static void unregister() throws NamingException {
      ConnectMonitorFactory.ChannelRegistrationServiceInstance.INSTANCE.setShutdownFlag();
   }

   private static class ChannelRegistrationServiceInstance {
      private static final ChannelRegistrationService INSTANCE = (ChannelRegistrationService)GlobalServiceLocator.getServiceLocator().getService(ChannelRegistrationService.class, new Annotation[0]);
   }

   private static class ConnectMonitorInstance {
      private static final ConnectMonitor INSTANCE = (ConnectMonitor)GlobalServiceLocator.getServiceLocator().getService(ConnectMonitor.class, new Annotation[0]);
   }
}
