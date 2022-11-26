package javax.jdo;

import java.io.PrintStream;
import java.io.PrintWriter;
import javax.jdo.spi.I18NHelper;

public class JDOException extends RuntimeException {
   Throwable[] nested;
   Object failed;
   private static I18NHelper msg = I18NHelper.getInstance("javax.jdo.Bundle");
   private boolean inPrintStackTrace = false;

   public JDOException() {
   }

   public JDOException(String msg) {
      super(msg);
   }

   public JDOException(String msg, Throwable[] nested) {
      super(msg);
      this.nested = nested;
   }

   public JDOException(String msg, Throwable nested) {
      super(msg);
      this.nested = new Throwable[]{nested};
   }

   public JDOException(String msg, Object failed) {
      super(msg);
      this.failed = failed;
   }

   public JDOException(String msg, Throwable[] nested, Object failed) {
      super(msg);
      this.nested = nested;
      this.failed = failed;
   }

   public JDOException(String msg, Throwable nested, Object failed) {
      super(msg);
      this.nested = new Throwable[]{nested};
      this.failed = failed;
   }

   public Object getFailedObject() {
      return this.failed;
   }

   public Throwable[] getNestedExceptions() {
      return this.nested;
   }

   public synchronized Throwable getCause() {
      return this.nested != null && this.nested.length != 0 && !this.inPrintStackTrace ? this.nested[0] : null;
   }

   public Throwable initCause(Throwable cause) {
      throw new JDOFatalInternalException(msg.msg("ERR_CannotInitCause"));
   }

   public synchronized String toString() {
      int len = this.nested == null ? 0 : this.nested.length;
      StringBuffer sb = new StringBuffer(10 + 100 * len);
      sb.append(super.toString());
      if (this.failed != null) {
         sb.append("\n").append(msg.msg("MSG_FailedObject"));
         String failedToString = null;

         try {
            failedToString = this.failed.toString();
         } catch (Exception var9) {
            Object objectId = JDOHelper.getObjectId(this.failed);
            if (objectId == null) {
               failedToString = msg.msg("MSG_ExceptionGettingFailedToString", (Object)exceptionToString(var9));
            } else {
               String objectIdToString = null;

               try {
                  objectIdToString = objectId.toString();
               } catch (Exception var8) {
                  objectIdToString = exceptionToString(var8);
               }

               failedToString = msg.msg("MSG_ExceptionGettingFailedToStringObjectId", exceptionToString(var9), objectIdToString);
            }
         }

         sb.append(failedToString);
      }

      if (len > 0 && !this.inPrintStackTrace) {
         sb.append("\n").append(msg.msg("MSG_NestedThrowables")).append("\n");
         Throwable exception = this.nested[0];
         sb.append(exception == null ? "null" : exception.toString());

         for(int i = 1; i < len; ++i) {
            sb.append("\n");
            exception = this.nested[i];
            sb.append(exception == null ? "null" : exception.toString());
         }
      }

      return sb.toString();
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public synchronized void printStackTrace(PrintStream s) {
      int len = this.nested == null ? 0 : this.nested.length;
      synchronized(s) {
         this.inPrintStackTrace = true;
         super.printStackTrace(s);
         if (len > 0) {
            s.println(msg.msg("MSG_NestedThrowablesStackTrace"));

            for(int i = 0; i < len; ++i) {
               Throwable exception = this.nested[i];
               if (exception != null) {
                  exception.printStackTrace(s);
               }
            }
         }

         this.inPrintStackTrace = false;
      }
   }

   public synchronized void printStackTrace(PrintWriter s) {
      int len = this.nested == null ? 0 : this.nested.length;
      synchronized(s) {
         this.inPrintStackTrace = true;
         super.printStackTrace(s);
         if (len > 0) {
            s.println(msg.msg("MSG_NestedThrowablesStackTrace"));

            for(int i = 0; i < len; ++i) {
               Throwable exception = this.nested[i];
               if (exception != null) {
                  exception.printStackTrace(s);
               }
            }
         }

         this.inPrintStackTrace = false;
      }
   }

   private static String exceptionToString(Exception ex) {
      if (ex == null) {
         return null;
      } else {
         String s = ex.getClass().getName();
         String message = ex.getMessage();
         return message != null ? s + ": " + message : s;
      }
   }
}
