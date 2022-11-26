package weblogic.security.internal;

import weblogic.utils.NestedRuntimeException;

public final class FileUtilsException extends NestedRuntimeException {
   private static final long serialVersionUID = -718853226921436528L;

   public FileUtilsException(String msg) {
      super(msg);
   }

   public FileUtilsException(String msg, Throwable t) {
      super(msg, t);
   }
}
