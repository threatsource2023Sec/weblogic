package weblogic.management;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class DistributedManagementException extends ManagementException {
   private static final int MAX_EXCEPTIONS = 3;
   private static final long serialVersionUID = -1567833008917372777L;
   private List exceptions;

   public DistributedManagementException(List exceptionsArg) {
      super("Distributed Management [" + exceptionsArg.size() + " exceptions]");
      this.exceptions = Collections.unmodifiableList(new ArrayList(exceptionsArg));
   }

   public List getExceptions() {
      return this.exceptions;
   }

   public String getMessage() {
      int i = 0;
      String message = "";
      message = super.getMessage();

      for(Iterator it = this.exceptions.iterator(); it.hasNext() && i++ != 3; message = message + ((Throwable)it.next()).getMessage()) {
         message = message + " \n";
      }

      return message;
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public void printStackTrace(PrintStream stream) {
      super.printStackTrace(stream);
      int i = 0;
      Iterator it = this.exceptions.iterator();

      while(it.hasNext() && i++ != 3) {
         ((Throwable)it.next()).printStackTrace(stream);
      }

   }

   public void printStackTrace(PrintWriter writer) {
      super.printStackTrace(writer);
      int i = 0;
      Iterator it = this.exceptions.iterator();

      while(it.hasNext() && i++ != 3) {
         ((Throwable)it.next()).printStackTrace(writer);
      }

   }
}
