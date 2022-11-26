package weblogic.diagnostics.image;

import weblogic.diagnostics.type.DiagnosticException;

public class ImageAlreadyCapturedException extends DiagnosticException {
   public ImageAlreadyCapturedException() {
   }

   public ImageAlreadyCapturedException(String msg) {
      super(msg);
   }

   public ImageAlreadyCapturedException(Throwable t) {
      super(t);
   }

   public ImageAlreadyCapturedException(String msg, Throwable t) {
      super(msg, t);
   }
}
