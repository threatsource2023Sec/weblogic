package weblogic.jdbc.utils;

import weblogic.utils.NestedException;

public class ParseException extends NestedException {
   public ParseException() {
   }

   public ParseException(String msg) {
      super(msg);
   }

   public ParseException(Throwable th) {
      super(th);
   }

   public ParseException(String msg, Throwable th) {
      super(msg, th);
   }
}
