package weblogic.management.jmx.modelmbean;

import javax.management.OperationsException;

public class MBeanVersionMismatchException extends OperationsException {
   public MBeanVersionMismatchException() {
   }

   public MBeanVersionMismatchException(String s) {
      super(s);
   }
}
