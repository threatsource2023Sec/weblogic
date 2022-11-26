package com.solarmetric.manage.jmx.remote.jboss322;

import com.solarmetric.manage.jmx.remote.RemoteMBeanServerFactory;
import java.util.Hashtable;
import javax.management.MBeanServer;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.openjpa.lib.log.Log;
import org.jboss.jmx.adaptor.rmi.RMIAdaptor;

public class JBossRemoteMBeanServerFactory implements RemoteMBeanServerFactory {
   public static MBeanServer createRemoteJBossMBeanServer(String jndiName, Hashtable propsHash) throws NamingException {
      InitialContext context = new InitialContext(propsHash);
      RMIAdaptor rmiAdaptor = (RMIAdaptor)context.lookup(jndiName);
      System.out.println("rmiAdaptor: (" + rmiAdaptor + ")");
      return new JBossRemoteMBeanServerProxy(rmiAdaptor);
   }

   public MBeanServer createRemoteMBeanServer(String jndiName, String jndiHost, int jndiPort, Log log) throws Exception {
      Hashtable propsHash = new Hashtable();
      propsHash.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
      propsHash.put("java.naming.provider.url", "jnp://" + jndiHost + ":" + jndiPort);
      propsHash.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
      return createRemoteJBossMBeanServer(jndiName, propsHash);
   }

   public MBeanServer createDefaultRemoteMBeanServer(String jndiHost, int jndiPort, Log log) throws Exception {
      return this.createRemoteMBeanServer("jmx/rmi/RMIAdaptor", jndiHost, jndiPort, log);
   }
}
