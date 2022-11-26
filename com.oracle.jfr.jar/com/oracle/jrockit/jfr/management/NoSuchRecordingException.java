package com.oracle.jrockit.jfr.management;

public class NoSuchRecordingException extends Exception {
   private static final long serialVersionUID = -7366386493537248412L;

   public NoSuchRecordingException(long id) {
      this(String.valueOf(id));
   }

   public NoSuchRecordingException() {
   }

   public NoSuchRecordingException(String message) {
      super(message);
   }

   public NoSuchRecordingException(Throwable cause) {
      super(cause);
   }

   public NoSuchRecordingException(String message, Throwable cause) {
      super(message, cause);
   }
}
