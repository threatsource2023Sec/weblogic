package weblogic.diagnostics.image;

import weblogic.diagnostics.type.DiagnosticException;

public class ImageCaptureTimeoutException extends DiagnosticException {
   private static final long serialVersionUID = 1L;

   public ImageCaptureTimeoutException() {
   }

   public ImageCaptureTimeoutException(String msg) {
      super(msg);
   }

   public ImageCaptureTimeoutException(Throwable t) {
      super(t);
   }

   public ImageCaptureTimeoutException(String msg, Throwable t) {
      super(msg, t);
   }
}
