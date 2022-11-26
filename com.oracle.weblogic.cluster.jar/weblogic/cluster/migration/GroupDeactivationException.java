package weblogic.cluster.migration;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class GroupDeactivationException extends MigrationException {
   private List causes;

   public GroupDeactivationException() {
      this("");
   }

   public GroupDeactivationException(String message) {
      super(message);
      this.causes = new ArrayList();
   }

   public GroupDeactivationException(Exception nestedEx) {
      super(nestedEx);
      this.causes = new ArrayList();
   }

   public void addCause(Exception e) {
      this.causes.add(e);
   }

   public List getCauses() {
      return this.causes;
   }

   public void printStackTrace(PrintStream s) {
      Iterator it = this.causes.iterator();

      while(it.hasNext()) {
         ((Throwable)it.next()).printStackTrace(s);
      }

   }

   public void printStackTrace(PrintWriter w) {
      Iterator it = this.causes.iterator();

      while(it.hasNext()) {
         ((Throwable)it.next()).printStackTrace(w);
      }

   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public String getMessage() {
      StringBuffer messageBuf = new StringBuffer(super.getMessage());
      Iterator it = this.causes.iterator();

      while(it.hasNext()) {
         messageBuf.append("cause is: " + ((Exception)it.next()).getMessage());
      }

      return messageBuf.toString();
   }
}
