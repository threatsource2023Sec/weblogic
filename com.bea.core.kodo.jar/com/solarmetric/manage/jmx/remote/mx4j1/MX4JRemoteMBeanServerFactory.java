package com.solarmetric.manage.jmx.remote.mx4j1;

import com.solarmetric.manage.jmx.remote.RemoteMBeanServerFactory;
import java.util.Hashtable;
import javax.management.MBeanServer;
import javax.naming.NamingException;
import mx4j.connector.RemoteMBeanServer;
import mx4j.connector.rmi.jrmp.JRMPConnector;
import org.apache.openjpa.lib.log.Log;

public class MX4JRemoteMBeanServerFactory implements RemoteMBeanServerFactory {
   public static MBeanServer createMX4JRemoteMBeanServer(String jndiName, Hashtable propsHash, Log log) throws NamingException {
      JRMPConnector connector = new JRMPConnector();
      connector.connect(jndiName, propsHash);
      RemoteMBeanServer server = connector.getRemoteMBeanServer();
      return new MX4JRemoteMBeanServerProxy(server, log);
   }

   public MBeanServer createRemoteMBeanServer(String jndiName, String jndiHost, int jndiPort, Log log) throws Exception {
      Hashtable propsHash = new Hashtable();
      propsHash.put("java.naming.factory.initial", "com.sun.jndi.rmi.registry.RegistryContextFactory");
      propsHash.put("java.naming.provider.url", "rmi://" + jndiHost + ":" + jndiPort);
      return createMX4JRemoteMBeanServer(jndiName, propsHash, log);
   }

   public MBeanServer createDefaultRemoteMBeanServer(String jndiHost, int jndiPort, Log log) throws Exception {
      return this.createRemoteMBeanServer("jrmp", jndiHost, jndiPort, log);
   }
}
