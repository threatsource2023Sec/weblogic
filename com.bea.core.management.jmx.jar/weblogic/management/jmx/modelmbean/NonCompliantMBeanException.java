package weblogic.management.jmx.modelmbean;

import javax.management.OperationsException;

public class NonCompliantMBeanException extends OperationsException {
   public NonCompliantMBeanException() {
   }

   public NonCompliantMBeanException(String s) {
      super(s);
   }
}
