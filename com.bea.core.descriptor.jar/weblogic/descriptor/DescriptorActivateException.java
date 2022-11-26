package weblogic.descriptor;

public class DescriptorActivateException extends Exception {
   public DescriptorActivateException() {
   }

   public DescriptorActivateException(String msg) {
      super(msg);
   }

   public DescriptorActivateException(String msg, Throwable cause) {
      super(msg);
      this.initCause(cause);
   }
}
