package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class NestedRuntimeException extends RuntimeException {
   private static final long serialVersionUID = 5439915454935047936L;

   public NestedRuntimeException(String msg) {
      super(msg);
   }

   public NestedRuntimeException(@Nullable String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }

   @Nullable
   public String getMessage() {
      return NestedExceptionUtils.buildMessage(super.getMessage(), this.getCause());
   }

   @Nullable
   public Throwable getRootCause() {
      return NestedExceptionUtils.getRootCause(this);
   }

   public Throwable getMostSpecificCause() {
      Throwable rootCause = this.getRootCause();
      return (Throwable)(rootCause != null ? rootCause : this);
   }

   public boolean contains(@Nullable Class exType) {
      if (exType == null) {
         return false;
      } else if (exType.isInstance(this)) {
         return true;
      } else {
         Throwable cause = this.getCause();
         if (cause == this) {
            return false;
         } else if (cause instanceof NestedRuntimeException) {
            return ((NestedRuntimeException)cause).contains(exType);
         } else {
            while(cause != null) {
               if (exType.isInstance(cause)) {
                  return true;
               }

               if (cause.getCause() == cause) {
                  break;
               }

               cause = cause.getCause();
            }

            return false;
         }
      }
   }

   static {
      NestedExceptionUtils.class.getName();
   }
}
