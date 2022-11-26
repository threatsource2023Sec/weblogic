package weblogic.descriptor;

public class DescriptorUpdateRejectedException extends Exception {
   public DescriptorUpdateRejectedException() {
   }

   public DescriptorUpdateRejectedException(String msg) {
      super(msg);
   }

   public DescriptorUpdateRejectedException(String msg, Throwable cause) {
      super(msg);
      this.initCause(cause);
   }
}
