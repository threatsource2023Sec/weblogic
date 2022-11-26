package org.apache.velocity.exception;

public class MethodInvocationException extends VelocityException {
   private String methodName = "";
   private String referenceName = "";
   private Throwable wrapped = null;

   public MethodInvocationException(String message, Throwable e, String methodName) {
      super(message);
      this.wrapped = e;
      this.methodName = methodName;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public Throwable getWrappedThrowable() {
      return this.wrapped;
   }

   public void setReferenceName(String ref) {
      this.referenceName = ref;
   }

   public String getReferenceName() {
      return this.referenceName;
   }
}
