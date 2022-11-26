package weblogic.servlet.internal;

public final class ServletNestedRuntimeException extends RuntimeException {
   static final long serialVersionUID = -2396918057516907699L;

   public ServletNestedRuntimeException(Throwable e) {
      super(e);
   }

   public ServletNestedRuntimeException(String msg, Throwable e) {
      super(msg, e);
   }
}
