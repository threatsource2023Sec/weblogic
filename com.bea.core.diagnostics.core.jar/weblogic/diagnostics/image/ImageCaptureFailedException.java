package weblogic.diagnostics.image;

import weblogic.diagnostics.type.DiagnosticException;

public class ImageCaptureFailedException extends DiagnosticException {
   private static final long serialVersionUID = 1L;

   public ImageCaptureFailedException() {
   }

   public ImageCaptureFailedException(String msg) {
      super(msg);
   }

   public ImageCaptureFailedException(Throwable t) {
      super(t);
   }

   public ImageCaptureFailedException(String msg, Throwable t) {
      super(msg, t);
   }
}
