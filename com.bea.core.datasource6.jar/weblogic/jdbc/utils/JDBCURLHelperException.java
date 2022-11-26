package weblogic.jdbc.utils;

import weblogic.utils.NestedException;

public class JDBCURLHelperException extends NestedException {
   public JDBCURLHelperException() {
   }

   public JDBCURLHelperException(String msg) {
      super(msg);
   }

   public JDBCURLHelperException(Throwable th) {
      super(th);
   }

   public JDBCURLHelperException(String msg, Throwable th) {
      super(msg, th);
   }
}
