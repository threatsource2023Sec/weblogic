package weblogic.management.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.utils.StackTraceUtils;

public class ErrorCollectionException extends Exception {
   public static final long serialVersionUID = -6671291145364224060L;
   private List exceptionList;

   public ErrorCollectionException() {
      this("");
   }

   public ErrorCollectionException(String baseMessage) {
      super(baseMessage);
      this.exceptionList = new LinkedList();
   }

   public ErrorCollectionException(Throwable exception) {
      this("");
      this.add(exception);
   }

   public Collection getExceptions() {
      return this.exceptionList;
   }

   public boolean isEmpty() {
      return this.exceptionList.isEmpty();
   }

   public String getBaseMessage() {
      return super.getMessage();
   }

   public String getMessage() {
      StringBuffer sb = new StringBuffer();
      sb.append(super.getMessage());

      String emsg;
      for(Iterator iter = this.getExceptions().iterator(); iter.hasNext(); sb.append("\n\t" + emsg)) {
         Throwable e = (Throwable)iter.next();
         emsg = e.getMessage();
         if (emsg == null) {
            emsg = StackTraceUtils.throwable2StackTrace(e);
         }
      }

      return sb.toString();
   }

   public void add(Throwable exception) {
      this.exceptionList.add(exception);
   }
}
