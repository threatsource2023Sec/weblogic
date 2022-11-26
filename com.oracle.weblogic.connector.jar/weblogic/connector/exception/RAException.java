package weblogic.connector.exception;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtils;

public class RAException extends ErrorCollectionException {
   static final long serialVersionUID = 1034453971783430698L;
   private String message;
   protected static final String STR_SEPERATOR = " - ";
   private static final String STR_OPEN = " [";
   private static final String STR_END = "]";
   private static final String EOL;

   public RAException() {
   }

   public RAException(String message) {
      super(message);
      this.message = message;
   }

   public RAException(String message, Throwable cause) {
      super(message, cause);
      this.message = message;
   }

   public RAException(Throwable cause) {
      super("", cause);
   }

   public String getMessageNoStacktrace() {
      StringBuffer sb = new StringBuffer();
      String baseMsg = super.getBaseMessage();
      if (baseMsg == null) {
         baseMsg = "";
      }

      baseMsg = baseMsg.trim();
      sb.append(baseMsg);
      Collection exs = this.getExceptions();
      boolean noOpenEndStr = false;
      if (baseMsg.length() == 0 && exs.size() == 1) {
         noOpenEndStr = true;
      }

      if (baseMsg.length() > 0 && exs.size() > 0) {
         sb.append(" - ");
      }

      Iterator iter = exs.iterator();

      while(iter.hasNext()) {
         Throwable e = (Throwable)iter.next();
         String emsg = this.getMessageOnlyFromCause(e);
         if (noOpenEndStr) {
            sb.append(emsg);
         } else {
            sb.append(addOpenEndStr(emsg));
         }
      }

      return sb.toString();
   }

   protected static String addOpenEndStr(String emsg) {
      return " [" + emsg + "]";
   }

   private String getMessageOnlyFromCause(Throwable t) {
      String emsg;
      if (t instanceof RAException) {
         emsg = ((RAException)t).getMessageNoStacktrace();
      } else {
         emsg = t.getMessage();
      }

      if (emsg == null) {
         return t.getCause() == null ? StackTraceUtils.throwable2StackTrace(t) : this.getMessageOnlyFromCause(t.getCause());
      } else {
         return emsg;
      }
   }

   public String getMessage() {
      return this.getMessageNoStacktrace();
   }

   public void printStackTrace(PrintWriter w) {
      if (this.getExceptions() != null && !this.getExceptions().isEmpty()) {
         w.println("");
         w.println(this.getClass().getName() + (this.message != null && !"".equals(this.message) ? ":" + this.message : ""));
         w.println("There are " + this.getExceptions().size() + " nested errors:");
         w.println("");
         String sep = "";
         Iterator var3 = this.getExceptions().iterator();

         while(var3.hasNext()) {
            Throwable t = (Throwable)var3.next();
            w.print(sep);
            sep = "and" + EOL + EOL;
            t.printStackTrace(w);
            w.println("");
         }

      } else {
         super.printStackTrace(w);
      }
   }

   public void add(Throwable th) {
      this.addError(th);
   }

   static {
      EOL = PlatformConstants.EOL;
   }
}
