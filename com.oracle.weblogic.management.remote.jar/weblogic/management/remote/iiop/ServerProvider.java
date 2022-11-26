package weblogic.management.remote.iiop;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerProvider;
import javax.management.remote.JMXServiceURL;

public class ServerProvider implements JMXConnectorServerProvider {
   public JMXConnectorServer newJMXConnectorServer(JMXServiceURL serviceURL, Map environment, MBeanServer mbeanServer) throws IOException {
      if (!serviceURL.getProtocol().equals("iiop")) {
         throw new MalformedURLException("Protocol not iiop: " + serviceURL.getProtocol());
      } else {
         return new IIOPConnectorServer(serviceURL, environment, mbeanServer);
      }
   }
}
