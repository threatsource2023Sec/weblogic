package weblogic.ejb.container.cmp11.rdbms.finders;

import java.lang.reflect.Method;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.logging.Loggable;

public final class FinderNotFoundException extends Exception {
   private static final long serialVersionUID = -3377809480870557550L;
   private Method failedMethod;
   private String ejbName = null;
   private String fileName = null;

   public FinderNotFoundException(Method failedMethod, String ejbName, String fileName) {
      this.failedMethod = failedMethod;
      this.ejbName = ejbName;
      this.fileName = fileName;
   }

   public String getMessage() {
      String methodSig = DDUtils.getMethodSignature(this.failedMethod);
      Loggable l = EJBLogger.logfinderNotFound11MessageLoggable(methodSig, this.ejbName, this.fileName);
      return l.getMessageText();
   }
}
