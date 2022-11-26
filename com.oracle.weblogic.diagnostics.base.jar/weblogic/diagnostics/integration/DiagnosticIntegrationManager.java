package weblogic.diagnostics.integration;

import weblogic.diagnostics.context.CorrelationIntegrationManager;
import weblogic.diagnostics.image.ImageAlreadyCapturedException;
import weblogic.diagnostics.image.ImageCaptureFailedException;
import weblogic.diagnostics.image.ImageCaptureTimeoutException;
import weblogic.diagnostics.image.InvalidDestinationDirectoryException;

public interface DiagnosticIntegrationManager extends CorrelationIntegrationManager {
   String captureImage(String var1, boolean var2, long var3) throws InvalidDestinationDirectoryException, ImageCaptureFailedException, ImageCaptureTimeoutException, ImageAlreadyCapturedException;

   public static final class Factory {
      public static DiagnosticIntegrationManager getInstance() {
         return DiagnosticIntegrationManagerImpl.getInstance();
      }
   }
}
