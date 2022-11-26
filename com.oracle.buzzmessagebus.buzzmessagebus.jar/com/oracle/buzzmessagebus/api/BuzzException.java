package com.oracle.buzzmessagebus.api;

public class BuzzException extends RuntimeException {
   final ExceptionType exceptionType;

   private BuzzException() {
      this.exceptionType = null;
   }

   public BuzzException(ExceptionType exceptionType) {
      super(exceptionType.name());
      this.exceptionType = exceptionType;
   }

   public BuzzException(ExceptionType exceptionType, Throwable cause) {
      super(exceptionType.name(), cause);
      this.exceptionType = exceptionType;
   }

   public BuzzException(ExceptionType exceptionType, String info) {
      super(exceptionType.name() + " " + info);
      this.exceptionType = exceptionType;
   }

   public ExceptionType getExceptionType() {
      return this.exceptionType;
   }

   public static enum ExceptionType {
      DATA_ON_CLOSED_ID,
      ILLEGAL_ENDPOINT_STATE,
      ILLEGAL_FLAGS,
      ILLEGAL_FRAME_TYPE,
      ILLEGAL_MESSAGE_ID,
      IO_EXCEPTION,
      NO_SUBPROTOCOL_RECEIVER_REGISTERED,
      START_ON_NON_CLOSED_ID,
      SUBPROTOCOL_MISMATCH,
      UNKNOWN_FRAME_TYPE;
   }
}
