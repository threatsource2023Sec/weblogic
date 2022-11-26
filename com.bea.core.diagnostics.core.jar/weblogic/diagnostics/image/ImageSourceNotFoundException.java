package weblogic.diagnostics.image;

import weblogic.diagnostics.type.DiagnosticException;

public class ImageSourceNotFoundException extends DiagnosticException {
   public ImageSourceNotFoundException() {
   }

   public ImageSourceNotFoundException(String msg) {
      super(msg);
   }

   public ImageSourceNotFoundException(Throwable t) {
      super(t);
   }

   public ImageSourceNotFoundException(String msg, Throwable t) {
      super(msg, t);
   }
}
