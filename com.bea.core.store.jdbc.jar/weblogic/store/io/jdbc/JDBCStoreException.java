package weblogic.store.io.jdbc;

import weblogic.logging.Loggable;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreLogger;

public class JDBCStoreException extends PersistentStoreException {
   static final long serialVersionUID = 3085999836640006726L;

   private static String decorate(JDBCStoreIO j, String s, Throwable rootCause) {
      for(Throwable l = rootCause; l != null; l = l.getCause()) {
         if (l instanceof JDBCStoreException) {
            return s;
         }
      }

      if (rootCause == null) {
         return StoreLogger.logJDBCStoreExceptionLoggable(s, j.getServerName(), j.getStoreName(), j.getTableRef()).getMessage();
      } else {
         return StoreLogger.logWrappedJDBCStoreExceptionLoggable(s, j.getServerName(), j.getStoreName(), j.getTableRef(), "" + rootCause).getMessage();
      }
   }

   JDBCStoreException(JDBCStoreIO j, String s) {
      super(decorate(j, s, (Throwable)null));
   }

   JDBCStoreException(JDBCStoreIO j, String s, Throwable t) {
      super(decorate(j, s, t), t);
   }

   JDBCStoreException(JDBCStoreIO j, Loggable l) {
      super(decorate(j, l.getMessage(), (Throwable)null));
   }

   JDBCStoreException(JDBCStoreIO j, Loggable l, Throwable t) {
      super(decorate(j, l.getMessage(), t), t);
   }
}
