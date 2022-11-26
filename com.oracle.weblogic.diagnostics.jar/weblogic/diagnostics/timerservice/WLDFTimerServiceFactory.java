package weblogic.diagnostics.timerservice;

import com.oracle.weblogic.diagnostics.timerservice.TimerService;
import java.lang.annotation.Annotation;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.GlobalServiceLocator;

@Service
@Singleton
public class WLDFTimerServiceFactory {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsTimer");
   private static WLDFTimerServiceImpl timerService;

   @Inject
   private WLDFTimerServiceFactory(WLDFTimerServiceImpl serviceImpl) {
      timerService = serviceImpl;
   }

   public static synchronized TimerService getTimerService() {
      if (timerService == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Initializing WLDFTimerService using HK2");
         }

         timerService = (WLDFTimerServiceImpl)GlobalServiceLocator.getServiceLocator().getService(WLDFTimerServiceImpl.class, new Annotation[0]);
      }

      return timerService;
   }
}
