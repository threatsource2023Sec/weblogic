package weblogic.management.provider.internal;

import java.io.IOException;
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
public class ConfigBackupService extends AbstractServerService {
   @Inject
   private RuntimeAccess runtimeAccess;

   public void start() throws ServiceFailureException {
      RuntimeAccess ra = this.runtimeAccess;
      if (ra.isAdminServer()) {
         try {
            if (ra.getDomain().isConfigBackupEnabled()) {
               ConfigBackup.saveBooted();
            }
         } catch (IOException var3) {
         }
      }

   }
}
