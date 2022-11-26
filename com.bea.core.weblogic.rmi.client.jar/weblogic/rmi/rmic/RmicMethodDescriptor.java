package weblogic.rmi.rmic;

public class RmicMethodDescriptor implements Comparable {
   private final String methodSignature;
   private String isIdempotent = null;
   private String timeOut = null;
   private boolean isRequiresTransaction = false;
   private String oneway = null;
   private String asynchronousResult = null;
   private String remoteExceptionWrapperName = null;

   public RmicMethodDescriptor(String signature) {
      this.methodSignature = signature;
   }

   public String getMethodSignature() {
      return this.methodSignature;
   }

   public String isIdempotent() {
      return this.isIdempotent;
   }

   public void setIdempotent(String value) {
      this.isIdempotent = value;
   }

   public String getTimeout() {
      return this.timeOut;
   }

   public void setTimeOut(String value) {
      this.timeOut = value;
   }

   public boolean isRequiresTransaction() {
      return this.isRequiresTransaction;
   }

   public void setRequiresTransaction(boolean requires) {
      this.isRequiresTransaction = requires;
   }

   public String getAsynchronousResult() {
      return this.asynchronousResult;
   }

   public void setAsynchronousResult(String asynch) {
      this.asynchronousResult = asynch;
   }

   public String getRemoteExceptionWrapperClassName() {
      return this.remoteExceptionWrapperName;
   }

   public void setRemoteExceptionWrapperName(String name) {
      this.remoteExceptionWrapperName = name;
   }

   public String getOneway() {
      return this.oneway;
   }

   public void setOneway(String oneway) {
      this.oneway = oneway;
   }

   public int compareTo(RmicMethodDescriptor o) {
      return this.methodSignature.compareTo(o.methodSignature);
   }

   public int hashCode() {
      return this.methodSignature.hashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (!(o instanceof RmicMethodDescriptor)) {
         return false;
      } else {
         RmicMethodDescriptor other = (RmicMethodDescriptor)o;
         return this.methodSignature.equals(other.methodSignature);
      }
   }

   public String toString() {
      return "[RmicMethodDescriptor] signature: " + this.methodSignature + " idempotent: " + this.isIdempotent + "asynchronous: " + this.asynchronousResult + "oneway RMI: " + this.oneway;
   }
}
