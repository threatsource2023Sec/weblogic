package weblogic.management.descriptors;

import weblogic.utils.NestedException;

public final class DescriptorValidationException extends NestedException {
   public DescriptorValidationException() {
   }

   public DescriptorValidationException(String msg) {
      super(msg);
   }

   public DescriptorValidationException(Throwable th) {
      super(th);
   }

   public DescriptorValidationException(String msg, Throwable th) {
      super(msg, th);
   }
}
