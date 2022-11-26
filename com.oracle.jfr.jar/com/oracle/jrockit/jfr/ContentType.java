package com.oracle.jrockit.jfr;

public enum ContentType {
   None,
   Bytes,
   Timestamp,
   Millis,
   Nanos,
   Ticks,
   Address,
   OSThread(false),
   JavaThread(false),
   StackTrace(false),
   Class(false),
   Percentage;

   private final boolean allowedForUserValue;

   private ContentType(boolean allowedForUserValue) {
      this.allowedForUserValue = allowedForUserValue;
   }

   private ContentType() {
      this(true);
   }

   public boolean isAllowedForUserValue() {
      return this.allowedForUserValue;
   }
}
