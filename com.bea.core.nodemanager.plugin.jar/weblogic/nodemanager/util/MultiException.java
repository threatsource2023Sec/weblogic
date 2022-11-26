package weblogic.nodemanager.util;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiException extends Exception {
   private final List exceptions = new ArrayList();

   public MultiException(String msg) {
      super(msg);
   }

   public void add(Exception exception) {
      this.exceptions.add(exception);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public void printStackTrace(PrintStream p) {
      if (this.exceptions != null && !this.exceptions.isEmpty()) {
         PrintWriter pw = new PrintWriter(new OutputStreamWriter(p));
         this.printStackTrace(pw);
         pw.flush();
      } else {
         super.printStackTrace(p);
      }
   }

   public void printStackTrace(PrintWriter w) {
      if (this.exceptions != null && !this.exceptions.isEmpty()) {
         w.println("");
         w.println("");
         w.println("There are " + this.exceptions.size() + " nested errors:");
         w.println("");
         String sep = "";
         Iterator it = this.exceptions.iterator();

         while(it.hasNext()) {
            w.print(sep);
            sep = "and" + System.getProperty("line.separator");
            Throwable t = (Throwable)it.next();
            t.printStackTrace(w);
            w.println("");
         }

      } else {
         super.printStackTrace(w);
      }
   }

   public String getMessage() {
      if (this.exceptions.isEmpty()) {
         return super.getMessage();
      } else {
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         this.printStackTrace(pw);
         pw.flush();
         return sw.toString();
      }
   }

   public String getBaseMessage() {
      return super.getMessage();
   }
}
