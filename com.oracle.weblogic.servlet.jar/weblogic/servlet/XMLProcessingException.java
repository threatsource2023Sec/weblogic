package weblogic.servlet;

import weblogic.utils.NestedRuntimeException;

public final class XMLProcessingException extends NestedRuntimeException {
   private static final long serialVersionUID = -2396918057516907699L;

   public XMLProcessingException(Throwable e) {
      super(e);
   }

   public XMLProcessingException(String msg, Throwable e) {
      super(msg, e);
   }
}
