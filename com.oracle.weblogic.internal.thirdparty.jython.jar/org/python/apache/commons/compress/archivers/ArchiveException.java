package org.python.apache.commons.compress.archivers;

public class ArchiveException extends Exception {
   private static final long serialVersionUID = 2772690708123267100L;

   public ArchiveException(String message) {
      super(message);
   }

   public ArchiveException(String message, Exception cause) {
      super(message);
      this.initCause(cause);
   }
}
