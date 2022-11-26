package weblogic.descriptor;

public class BeanUpdateFailedException extends DescriptorUpdateFailedException {
   public BeanUpdateFailedException() {
   }

   public BeanUpdateFailedException(String msg) {
      super(msg);
   }

   public BeanUpdateFailedException(String msg, Throwable cause) {
      super(msg);
      this.initCause(cause);
   }
}
