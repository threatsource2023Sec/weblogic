package weblogic.jms.backend;

import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;

public final class BEConfigCompatibility implements ConfigurationProcessor {
   public void updateConfiguration(DomainMBean root) throws UpdateException {
      JMSServerMBean[] jmsServers = root.getJMSServers();
      if (jmsServers != null) {
         for(int lcv = 0; lcv < jmsServers.length; ++lcv) {
            JMSServerMBean jmsServer = jmsServers[lcv];
            jmsServer.useDelegates(root);
         }
      }

   }
}
