package weblogic.cluster.singleton;

import java.util.List;
import weblogic.management.configuration.ServerMBean;

public interface ServiceLocationSelector {
   ServerMBean chooseServer();

   void setServerList(List var1);

   void setLastHost(ServerMBean var1);

   void migrationSuccessful(ServerMBean var1, boolean var2);
}
