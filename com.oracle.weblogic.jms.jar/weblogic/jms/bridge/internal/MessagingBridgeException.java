package weblogic.jms.bridge.internal;

import java.io.PrintStream;
import java.io.PrintWriter;
import weblogic.jms.JMSLogger;

public class MessagingBridgeException extends Exception {
   static final long serialVersionUID = 896851532291231868L;
   private String reason;
   private Throwable throwable;

   public MessagingBridgeException(String reason) {
      this.reason = reason;
      this.throwable = null;
   }

   public MessagingBridgeException(String reason, Throwable thrown) {
      this.reason = reason;
      this.throwable = thrown;
   }

   public synchronized void setLinkedException(Exception exception) {
      this.throwable = exception;
   }

   public synchronized void setLinkedThrowable(Throwable thrown) {
      this.throwable = thrown;
   }

   public Exception getLinkedException() {
      if (this.throwable != null && this.throwable instanceof Exception) {
         return (Exception)this.throwable;
      } else {
         Exception e = new Exception("Linked Exception");
         return e;
      }
   }

   public void printStackTrace() {
      this.printWLJMSStackTrace(this.throwable);
   }

   public void printStackTrace(PrintStream s) {
      this.printWLJMSStackTrace(this.throwable, s);
   }

   public void printStackTrace(PrintWriter s) {
      this.printWLJMSStackTrace(this.throwable, s);
   }

   void printWLJMSStackTrace(Throwable thrown) {
      System.out.println("MessagingBridgeException: " + this.reason);
      if (thrown != null) {
         JMSLogger.logStackTraceLinked(thrown);
      }

   }

   void printWLJMSStackTrace(Throwable thrown, PrintStream s) {
      s.println("MessagingBridgeException: " + this.reason);
      if (thrown != null) {
         JMSLogger.logStackTraceLinked(thrown);
      }

   }

   void printWLJMSStackTrace(Throwable thrown, PrintWriter s) {
      s.println("MessagingBridgeException: " + this.reason);
      if (thrown != null) {
         JMSLogger.logStackTraceLinked(thrown);
      }

   }
}
