package weblogic.console.internal;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public class ConsoleMBeanService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      try {
         ConsoleRuntimeImpl.initialize();
      } catch (Exception var2) {
         throw new ServiceFailureException(var2);
      }
   }
}
