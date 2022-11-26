package com.bea.core.repackaged.springframework.dao;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class NonTransientDataAccessResourceException extends NonTransientDataAccessException {
   public NonTransientDataAccessResourceException(String msg) {
      super(msg);
   }

   public NonTransientDataAccessResourceException(String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
