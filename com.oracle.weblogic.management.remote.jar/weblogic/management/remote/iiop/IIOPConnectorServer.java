package weblogic.management.remote.iiop;

import java.io.IOException;
import java.util.Map;
import javax.management.MBeanServer;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnectorServer;

public class IIOPConnectorServer extends RMIConnectorServer {
   public IIOPConnectorServer(JMXServiceURL serviceURL, Map environment, MBeanServer mbeanServer) throws IOException {
      super(serviceURL, environment, new IIOPServerImpl(environment), mbeanServer);
   }
}
