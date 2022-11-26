package org.apache.openjpa.lib.conf;

public class BootstrapException extends RuntimeException {
   private boolean _fatal = false;

   public BootstrapException() {
   }

   public BootstrapException(String message) {
      super(message);
   }

   public BootstrapException(Throwable cause) {
      super(cause);
   }

   public BootstrapException(String message, Throwable cause) {
      super(message, cause);
   }

   public BootstrapException setFatal(boolean fatal) {
      this._fatal = fatal;
      return this;
   }

   public boolean isFatal() {
      return this._fatal;
   }
}
