package weblogic.jms.bridge;

import java.io.PrintStream;
import java.io.PrintWriter;
import javax.resource.ResourceException;
import weblogic.jms.JMSLogger;

public final class ResourceTransactionRolledBackException extends ResourceException {
   static final long serialVersionUID = -4349407695017988608L;
   private Exception le;

   public ResourceTransactionRolledBackException(String reason) {
      super(reason);
   }

   public ResourceTransactionRolledBackException(String reason, String errorCode) {
      super(reason, errorCode);
   }

   public void setLinkedException(Exception exception) {
      this.le = exception;
   }

   public Exception getLinkedException() {
      return this.le == null ? super.getLinkedException() : this.le;
   }

   public void printStackTrace() {
      JMSLogger.logStackTrace(this);
      printWLJMSStackTrace(this.le);
   }

   public void printStackTrace(PrintStream s) {
      JMSLogger.logStackTrace(this);
      printWLJMSStackTrace(this.le, s);
   }

   public void printStackTrace(PrintWriter s) {
      JMSLogger.logStackTrace(this);
      printWLJMSStackTrace(this.le, s);
   }

   static void printWLJMSStackTrace(Exception e) {
      if (e != null) {
         JMSLogger.logStackTraceLinked(e);
      }

   }

   static void printWLJMSStackTrace(Exception e, PrintStream s) {
      if (e != null) {
         JMSLogger.logStackTraceLinked(e);
      }

   }

   static void printWLJMSStackTrace(Exception e, PrintWriter s) {
      if (e != null) {
         JMSLogger.logStackTraceLinked(e);
      }

   }
}
