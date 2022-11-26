package weblogic.xml.registry;

import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;

public class XMLConfigUpdater implements ConfigurationProcessor {
   public void updateConfiguration(DomainMBean domain) throws UpdateException {
      ServerMBean[] server = domain.getServers();

      for(int i = 0; i < server.length; ++i) {
         if (server[i].getXMLRegistry() == null) {
         }

         if (server[i].getXMLEntityCache() == null) {
         }
      }

   }
}
