package weblogic.management.provider.internal.situationalconfig;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.utils.situationalconfig.SituationalConfigManager;
import weblogic.server.ServiceFailureException;
import weblogic.utils.Debug;
import weblogic.utils.LocatorUtilities;

@Service
@RunLevel(10)
@Named
public class SituationalServiceImpl implements SituationalService {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSituationalConfig");
   private SituationalConfigManager situationalConfigManager;

   public SituationalServiceImpl() {
      if (debugLogger.isDebugEnabled()) {
         Debug.say("[SitConfig] SituationalService Loading ");
      }

      try {
         this.start();
      } catch (ServiceFailureException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] Exception in SituationalService ", var2);
         }

         throw new RuntimeException(var2);
      } catch (Exception var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] Exception in SituationalService ");
            var3.printStackTrace();
         }
      }

   }

   public void start() throws Exception {
      this.situationalConfigManager = (SituationalConfigManager)LocatorUtilities.getService(SituationalConfigManager.class);
      this.situationalConfigManager.setupTimers();
   }
}
