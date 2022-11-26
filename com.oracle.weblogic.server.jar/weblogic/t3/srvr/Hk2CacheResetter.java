package weblogic.t3.srvr;

import javax.inject.Inject;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.runlevel.ChangeableRunLevelFuture;
import org.glassfish.hk2.runlevel.ErrorInformation;
import org.glassfish.hk2.runlevel.RunLevelFuture;
import org.glassfish.hk2.runlevel.RunLevelListener;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.external.runtime.ServiceLocatorRuntimeBean;
import weblogic.diagnostics.debug.DebugLogger;

@Service
@PerLookup
@Rank(-100)
public class Hk2CacheResetter implements RunLevelListener {
   private static final DebugLogger debugSLCWLDF = DebugLogger.getDebugLogger("DebugServerLifeCycle");
   @Inject
   private ServiceLocatorRuntimeBean runtimeBean;

   public void onProgress(ChangeableRunLevelFuture currentJob, int levelAchieved) {
      if (levelAchieved == 20) {
         if (currentJob.isUp()) {
            if (debugSLCWLDF.isDebugEnabled()) {
               debugSLCWLDF.debug("Resetting HK2 service cache which is currently at size " + this.runtimeBean.getServiceCacheSize());
               debugSLCWLDF.debug("Resetting HK2 reflection cache which is currently at size " + this.runtimeBean.getReflectionCacheSize());
            }

            this.runtimeBean.clearServiceCache();
            this.runtimeBean.clearReflectionCache();
         }
      }
   }

   public void onCancelled(RunLevelFuture currentJob, int levelAchieved) {
   }

   public void onError(RunLevelFuture currentJob, ErrorInformation errorInformation) {
   }
}
