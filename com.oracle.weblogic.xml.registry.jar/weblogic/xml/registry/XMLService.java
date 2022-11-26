package weblogic.xml.registry;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.xml.XMLLogger;

@Service
@Named
@RunLevel(10)
public class XMLService extends AbstractServerService {
   public String getVersion() {
      return "XML 1.1";
   }

   public void start() throws ServiceFailureException {
      try {
         XMLRegistry.init();
         XMLLogger.logIntializingXMLRegistry();
      } catch (XMLRegistryException var2) {
         throw new ServiceFailureException("Failed to initialize XMLRegistry: ", var2);
      }
   }

   public static void main(String[] argv) {
   }
}
