package weblogic.diagnostics.image;

import java.lang.annotation.Annotation;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.server.GlobalServiceLocator;

public class FirstFailure {
   private static ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticImage");

   public static TaskRuntimeMBean fail() {
      TaskRuntimeMBean imageWatcher = null;

      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("FirstFailure invoked.");
         }

         imageWatcher = imageManager.captureImage((String)null, (String)null);
      } catch (ImageAlreadyCapturedException var2) {
         DiagnosticsLogger.logDiagnosticImageAlreadyCaptured();
      } catch (InvalidDestinationDirectoryException var3) {
         DiagnosticsLogger.logDiagnosticImageDirectoryAccessError(var3.getMessage());
      }

      return imageWatcher;
   }
}
