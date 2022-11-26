package weblogic.rjvm;

import weblogic.common.WLObjectInput;

public interface Response {
   Throwable getThrowable();

   WLObjectInput getMsg();

   boolean isAvailable();

   Object getTxContext();
}
