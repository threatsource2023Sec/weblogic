package com.bea.core.repackaged.springframework.remoting.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class RemoteInvocationResult implements Serializable {
   private static final long serialVersionUID = 2138555143707773549L;
   @Nullable
   private Object value;
   @Nullable
   private Throwable exception;

   public RemoteInvocationResult(@Nullable Object value) {
      this.value = value;
   }

   public RemoteInvocationResult(@Nullable Throwable exception) {
      this.exception = exception;
   }

   public RemoteInvocationResult() {
   }

   public void setValue(@Nullable Object value) {
      this.value = value;
   }

   @Nullable
   public Object getValue() {
      return this.value;
   }

   public void setException(@Nullable Throwable exception) {
      this.exception = exception;
   }

   @Nullable
   public Throwable getException() {
      return this.exception;
   }

   public boolean hasException() {
      return this.exception != null;
   }

   public boolean hasInvocationTargetException() {
      return this.exception instanceof InvocationTargetException;
   }

   @Nullable
   public Object recreate() throws Throwable {
      if (this.exception != null) {
         Throwable exToThrow = this.exception;
         if (this.exception instanceof InvocationTargetException) {
            exToThrow = ((InvocationTargetException)this.exception).getTargetException();
         }

         RemoteInvocationUtils.fillInClientStackTraceIfPossible(exToThrow);
         throw exToThrow;
      } else {
         return this.value;
      }
   }
}
