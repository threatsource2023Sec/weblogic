package weblogic.descriptor;

public class DescriptorPrepareException extends Exception {
   public DescriptorPrepareException() {
   }

   public DescriptorPrepareException(String msg) {
      super(msg);
   }

   public DescriptorPrepareException(String msg, Throwable cause) {
      super(msg);
      this.initCause(cause);
   }
}
