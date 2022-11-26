package weblogic.coherence.service.internal;

import com.tangosol.net.management.MBeanServerFinder;
import javax.management.MBeanServer;
import javax.management.remote.JMXServiceURL;

public class WLSMBeanServerFinder implements MBeanServerFinder {
   private static MBeanServer server;
   private static JMXServiceURL jmxServiceURL;

   public MBeanServer findMBeanServer(String arg0) {
      return server;
   }

   public JMXServiceURL findJMXServiceUrl(String sDefaultDomain) {
      return jmxServiceURL;
   }

   protected static void setMBeanServer(MBeanServer server) {
      WLSMBeanServerFinder.server = server;
   }

   protected static void setJMXServiceUrl(JMXServiceURL jmxServiceURL) {
      WLSMBeanServerFinder.jmxServiceURL = jmxServiceURL;
   }
}
