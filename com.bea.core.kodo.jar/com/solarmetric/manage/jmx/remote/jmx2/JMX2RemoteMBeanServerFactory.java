package com.solarmetric.manage.jmx.remote.jmx2;

import com.solarmetric.manage.jmx.remote.RemoteMBeanServerFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.openjpa.lib.log.Log;

public class JMX2RemoteMBeanServerFactory implements RemoteMBeanServerFactory {
   public static MBeanServer createJMX2RemoteMBeanServer(String serviceUrl, Hashtable propsHash, Log log) throws MalformedURLException, IOException {
      JMXServiceURL address = new JMXServiceURL(serviceUrl);
      JMXConnector connector = JMXConnectorFactory.newJMXConnector(address, propsHash);
      connector.connect(propsHash);
      MBeanServerConnection server = connector.getMBeanServerConnection();
      return new JMX2RemoteMBeanServerProxy(server, log);
   }

   public MBeanServer createRemoteMBeanServer(String serviceUrl, String host, int port, Log log) throws Exception {
      Hashtable environment = new Hashtable();
      environment.put("java.naming.factory.initial", "com.sun.jndi.rmi.registry.RegistryContextFactory");
      environment.put("java.naming.provider.url", "rmi://" + host + ":" + port);
      return createJMX2RemoteMBeanServer(serviceUrl, environment, log);
   }

   public MBeanServer createDefaultRemoteMBeanServer(String host, int port, Log log) throws Exception {
      String serviceUrl = "service:jmx:rmi://" + host + "/jndi/jmxservice";
      return this.createRemoteMBeanServer(serviceUrl, host, port, log);
   }
}
