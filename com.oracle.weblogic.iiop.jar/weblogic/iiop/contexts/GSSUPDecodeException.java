package weblogic.iiop.contexts;

import weblogic.utils.NestedException;

public class GSSUPDecodeException extends NestedException {
   public GSSUPDecodeException(String s) {
      super(s);
   }

   public GSSUPDecodeException() {
      this((String)null);
   }

   public GSSUPDecodeException(String s, Throwable t) {
      super(s, t);
   }
}
