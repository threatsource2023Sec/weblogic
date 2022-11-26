package com.bea.core.repackaged.springframework.dao;

public class IncorrectUpdateSemanticsDataAccessException extends InvalidDataAccessResourceUsageException {
   public IncorrectUpdateSemanticsDataAccessException(String msg) {
      super(msg);
   }

   public IncorrectUpdateSemanticsDataAccessException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public boolean wasDataUpdated() {
      return true;
   }
}
