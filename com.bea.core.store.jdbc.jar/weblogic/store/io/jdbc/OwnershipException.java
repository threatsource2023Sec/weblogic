package weblogic.store.io.jdbc;

import weblogic.logging.Loggable;

public class OwnershipException extends JDBCStoreException {
   static final long serialVersionUID = 1L;

   OwnershipException(JDBCStoreIO j, String s) {
      super(j, s);
   }

   OwnershipException(JDBCStoreIO j, String s, Throwable t) {
      super(j, s, t);
   }

   OwnershipException(JDBCStoreIO j, Loggable l) {
      super(j, l);
   }

   OwnershipException(JDBCStoreIO j, Loggable l, Throwable t) {
      super(j, l, t);
   }
}
