package weblogic.descriptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DescriptorUpdateFailedException extends Exception {
   private List exceptionList = new ArrayList();

   public DescriptorUpdateFailedException() {
   }

   public DescriptorUpdateFailedException(String msg) {
      super(msg);
   }

   public DescriptorUpdateFailedException(String msg, Throwable cause) {
      super(msg);
      this.initCause(cause);
   }

   public Exception[] getExceptionList() {
      return (Exception[])((Exception[])this.exceptionList.toArray(new Exception[0]));
   }

   public void addException(IllegalArgumentException e) {
      this.exceptionList.add(e);
   }

   public void addException(Exception e) {
      this.exceptionList.add(e);
   }

   public String getMessage() {
      StringBuffer buf = new StringBuffer();
      String msg = super.getMessage();
      if (msg != null && msg.length() > 0) {
         buf.append(super.getMessage() + "\n");
      }

      if (this.exceptionList.size() > 0) {
         buf.append("The following failures occurred:\n");
         Iterator it = this.exceptionList.iterator();

         while(it.hasNext()) {
            Exception exc = (Exception)it.next();
            buf.append("-- " + exc.getMessage() + "\n");
            buf.append(this.getTrace(exc) + "\n");
         }
      }

      return buf.toString();
   }

   private String getTrace(Throwable t) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      t.printStackTrace(pw);
      return sw.toString();
   }
}
