package weblogic.management.internal;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public class ConfigurationAuditorService extends AbstractServerService {
   @Inject
   private RuntimeAccess runtimeAccess;

   public void start() throws ServiceFailureException {
      ConfigurationAuditor.getInstance().intialize(this.runtimeAccess.getDomain());
   }
}
