package weblogic.application.library;

import weblogic.logging.Loggable;

public class LoggableLibraryProcessingException extends LibraryProcessingException {
   private final Loggable loggable;

   public LoggableLibraryProcessingException(Loggable loggable) {
      this(loggable, (Throwable)null);
   }

   public LoggableLibraryProcessingException(Loggable loggable, Throwable th) {
      super(loggable.getMessage(), th);
      this.loggable = loggable;
   }

   public Loggable getLoggable() {
      return this.loggable;
   }
}
