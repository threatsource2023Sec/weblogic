package org.mozilla.javascript;

import java.lang.reflect.InvocationTargetException;

public class WrappedException extends EvaluatorException implements Wrapper {
   private Throwable exception;

   public WrappedException(Throwable var1) {
      super(var1.getMessage());
      this.exception = var1.fillInStackTrace();
   }

   public String getLocalizedMessage() {
      return "WrappedException of " + this.exception.getLocalizedMessage();
   }

   public String getMessage() {
      return "WrappedException of " + this.exception.toString();
   }

   public Throwable getWrappedException() {
      return this.exception;
   }

   public Object unwrap() {
      return this.exception;
   }

   public static EvaluatorException wrapException(Throwable var0) {
      if (var0 instanceof InvocationTargetException) {
         var0 = ((InvocationTargetException)var0).getTargetException();
      }

      return (EvaluatorException)(var0 instanceof EvaluatorException ? (EvaluatorException)var0 : new WrappedException(var0));
   }
}
