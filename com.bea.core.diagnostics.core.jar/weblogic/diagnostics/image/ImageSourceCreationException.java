package weblogic.diagnostics.image;

import weblogic.diagnostics.type.DiagnosticException;

public class ImageSourceCreationException extends DiagnosticException {
   public ImageSourceCreationException() {
   }

   public ImageSourceCreationException(String msg) {
      super(msg);
   }

   public ImageSourceCreationException(Throwable t) {
      super(t);
   }

   public ImageSourceCreationException(String msg, Throwable t) {
      super(msg, t);
   }
}
