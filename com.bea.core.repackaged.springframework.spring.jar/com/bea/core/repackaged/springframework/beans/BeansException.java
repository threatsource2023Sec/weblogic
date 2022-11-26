package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class BeansException extends NestedRuntimeException {
   public BeansException(String msg) {
      super(msg);
   }

   public BeansException(@Nullable String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
