package weblogic.management.deploy.internal;

public class ExceptionTranslator {
   static RuntimeException translateException(Throwable ex) {
      RuntimeException rtEx = null;
      if (ex.getCause() != null) {
         rtEx = new RuntimeException(ex.getMessage(), translateException(ex.getCause()));
      } else {
         rtEx = new RuntimeException(ex.getMessage());
      }

      rtEx.setStackTrace(ex.getStackTrace());
      return rtEx;
   }
}
