package weblogic.connector.common;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.ServerService;
import weblogic.server.ServiceActivator;

@Service
@Named
@RunLevel(10)
public class ConnectorServiceActivator extends ServiceActivator {
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   private static ConnectorServiceActivator INSTANCE;

   public static synchronized ConnectorServiceActivator getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new ConnectorServiceActivator();
      }

      return INSTANCE;
   }

   private ConnectorServiceActivator() {
      super("weblogic.connector.common.ConnectorService");
   }

   public String getVersion() {
      return "1.7";
   }

   public String getName() {
      return "J2EE Connector";
   }
}
