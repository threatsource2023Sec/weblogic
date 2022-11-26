package com.bea.core.repackaged.springframework.dao;

public class IncorrectResultSizeDataAccessException extends DataRetrievalFailureException {
   private final int expectedSize;
   private final int actualSize;

   public IncorrectResultSizeDataAccessException(int expectedSize) {
      super("Incorrect result size: expected " + expectedSize);
      this.expectedSize = expectedSize;
      this.actualSize = -1;
   }

   public IncorrectResultSizeDataAccessException(int expectedSize, int actualSize) {
      super("Incorrect result size: expected " + expectedSize + ", actual " + actualSize);
      this.expectedSize = expectedSize;
      this.actualSize = actualSize;
   }

   public IncorrectResultSizeDataAccessException(String msg, int expectedSize) {
      super(msg);
      this.expectedSize = expectedSize;
      this.actualSize = -1;
   }

   public IncorrectResultSizeDataAccessException(String msg, int expectedSize, Throwable ex) {
      super(msg, ex);
      this.expectedSize = expectedSize;
      this.actualSize = -1;
   }

   public IncorrectResultSizeDataAccessException(String msg, int expectedSize, int actualSize) {
      super(msg);
      this.expectedSize = expectedSize;
      this.actualSize = actualSize;
   }

   public IncorrectResultSizeDataAccessException(String msg, int expectedSize, int actualSize, Throwable ex) {
      super(msg, ex);
      this.expectedSize = expectedSize;
      this.actualSize = actualSize;
   }

   public int getExpectedSize() {
      return this.expectedSize;
   }

   public int getActualSize() {
      return this.actualSize;
   }
}
