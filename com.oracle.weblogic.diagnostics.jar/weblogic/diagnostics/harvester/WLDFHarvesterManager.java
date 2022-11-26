package weblogic.diagnostics.harvester;

import weblogic.diagnostics.harvester.internal.WLDFHarvesterImpl;
import weblogic.diagnostics.utils.SecurityHelper;

public class WLDFHarvesterManager implements WLDFHarvesterLauncher {
   private static WLDFHarvesterImpl wldfHarvesterSingleton;
   private static WLDFHarvesterManager mgr = new WLDFHarvesterManager();

   private WLDFHarvesterManager() {
   }

   public static WLDFHarvesterLauncher getInstance() {
      SecurityHelper.checkAnyAdminRole();
      return mgr;
   }

   public WLDFHarvester getHarvesterSingleton() {
      SecurityHelper.checkAnyAdminRole();
      synchronized(this) {
         if (wldfHarvesterSingleton == null) {
            wldfHarvesterSingleton = WLDFHarvesterImpl.getInstance();
            wldfHarvesterSingleton.prepare();
            wldfHarvesterSingleton.activate();
         }
      }

      return wldfHarvesterSingleton;
   }
}
