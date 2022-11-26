package weblogic.descriptor;

public class BeanUpdateRejectedException extends DescriptorUpdateRejectedException {
   public BeanUpdateRejectedException() {
   }

   public BeanUpdateRejectedException(String msg) {
      super(msg);
   }

   public BeanUpdateRejectedException(String msg, Throwable cause) {
      super(msg);
      this.initCause(cause);
   }
}
