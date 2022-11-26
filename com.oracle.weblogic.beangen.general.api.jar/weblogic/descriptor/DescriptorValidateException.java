package weblogic.descriptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DescriptorValidateException extends RuntimeException {
   private static final BeangenApiTextFormatter textFormatter = BeangenApiTextFormatter.getInstance();
   private List exceptionList = new ArrayList();

   public DescriptorValidateException() {
   }

   public DescriptorValidateException(String s) {
      super(s);
   }

   public Exception[] getExceptionList() {
      return (Exception[])((Exception[])this.exceptionList.toArray(new Exception[0]));
   }

   public void addException(IllegalArgumentException e) {
      this.exceptionList.add(e);
   }

   public void addException(DescriptorValidateException e) {
      if (e.exceptionList.size() > 0) {
         this.exceptionList.addAll(e.exceptionList);
      } else {
         this.exceptionList.add(e);
      }

   }

   public String getMessage() {
      StringBuffer buf = new StringBuffer();
      String msg = super.getMessage();
      if (msg != null && msg.length() > 0) {
         buf.append(super.getMessage() + "\n");
      }

      if (this.exceptionList.size() > 0) {
         buf.append(textFormatter.getBeanFailuresOccurredString());
         Iterator it = this.exceptionList.iterator();

         while(it.hasNext()) {
            buf.append("-- " + ((Exception)it.next()).getMessage() + "\n");
         }
      }

      return buf.toString();
   }
}
