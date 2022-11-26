package weblogic.security;

import java.security.NoSuchAlgorithmException;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public class EarlySecurityInitializationService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      try {
         HMAC.init();
      } catch (NoSuchAlgorithmException var2) {
         throw new ServiceFailureException(var2);
      }
   }
}
