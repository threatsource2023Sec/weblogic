package org.python.netty.util.internal.logging;

class FormattingTuple {
   static final FormattingTuple NULL = new FormattingTuple((String)null);
   private final String message;
   private final Throwable throwable;
   private final Object[] argArray;

   FormattingTuple(String message) {
      this(message, (Object[])null, (Throwable)null);
   }

   FormattingTuple(String message, Object[] argArray, Throwable throwable) {
      this.message = message;
      this.throwable = throwable;
      if (throwable == null) {
         this.argArray = argArray;
      } else {
         this.argArray = trimmedCopy(argArray);
      }

   }

   static Object[] trimmedCopy(Object[] argArray) {
      if (argArray != null && argArray.length != 0) {
         int trimmedLen = argArray.length - 1;
         Object[] trimmed = new Object[trimmedLen];
         System.arraycopy(argArray, 0, trimmed, 0, trimmedLen);
         return trimmed;
      } else {
         throw new IllegalStateException("nonsensical empty or null argument array");
      }
   }

   public String getMessage() {
      return this.message;
   }

   public Object[] getArgArray() {
      return this.argArray;
   }

   public Throwable getThrowable() {
      return this.throwable;
   }
}
