package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.beans.FatalBeanException;

public class ApplicationContextException extends FatalBeanException {
   public ApplicationContextException(String msg) {
      super(msg);
   }

   public ApplicationContextException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
