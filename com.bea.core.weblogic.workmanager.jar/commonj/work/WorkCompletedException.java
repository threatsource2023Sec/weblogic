package commonj.work;

import java.util.ArrayList;
import java.util.List;

public class WorkCompletedException extends WorkException {
   List exceptionList;

   public WorkCompletedException() {
   }

   public WorkCompletedException(String message) {
      super(message);
   }

   public WorkCompletedException(String message, Throwable cause) {
      super(message, cause);
   }

   public WorkCompletedException(Throwable cause) {
      super(cause);
   }

   public WorkCompletedException(String message, List list) {
      super(message);
      this.exceptionList = list;
   }

   public List getExceptionList() {
      if (this.exceptionList == null && this.getCause() != null) {
         ArrayList a = new ArrayList();
         a.add(this.getCause());
         return a;
      } else {
         return this.exceptionList;
      }
   }
}
