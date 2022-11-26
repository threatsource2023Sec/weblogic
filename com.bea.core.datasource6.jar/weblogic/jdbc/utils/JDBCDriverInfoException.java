package weblogic.jdbc.utils;

import weblogic.utils.NestedException;

public class JDBCDriverInfoException extends NestedException {
   public JDBCDriverInfoException() {
   }

   public JDBCDriverInfoException(String msg) {
      super(msg);
   }

   public JDBCDriverInfoException(Throwable th) {
      super(th);
   }

   public JDBCDriverInfoException(String msg, Throwable th) {
      super(msg, th);
   }
}
