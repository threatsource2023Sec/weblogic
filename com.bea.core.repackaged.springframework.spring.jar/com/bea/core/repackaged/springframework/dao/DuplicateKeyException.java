package com.bea.core.repackaged.springframework.dao;

public class DuplicateKeyException extends DataIntegrityViolationException {
   public DuplicateKeyException(String msg) {
      super(msg);
   }

   public DuplicateKeyException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
