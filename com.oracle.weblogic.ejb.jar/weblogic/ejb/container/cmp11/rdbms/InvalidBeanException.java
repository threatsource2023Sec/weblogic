package weblogic.ejb.container.cmp11.rdbms;

import java.util.Iterator;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.PlatformConstants;

public final class InvalidBeanException extends ErrorCollectionException implements PlatformConstants {
   private static final long serialVersionUID = -4596225274545228810L;

   public InvalidBeanException() {
      super("Invalid specifications for a WebLogic RDBMS CMP EJB.");
   }

   public String getMessage() {
      StringBuffer sb = new StringBuffer();
      sb.append(" Bean provided to WebLogic RDBMS CMP system is invalid." + EOL);
      sb.append("Please examine the following exceptions for specific problems: " + EOL);
      Iterator exceptions = this.getExceptions().iterator();

      for(int iException = 0; exceptions.hasNext(); ++iException) {
         Exception exception = (Exception)exceptions.next();
         sb.append("" + iException + ":");
         String message = exception.getMessage();
         if (message != null) {
            sb.append("  " + message + EOL);
         } else {
            sb.append(" " + exception.toString() + EOL);
         }
      }

      return sb.toString();
   }
}
