package weblogic.descriptor.conflict;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;

public class ExceptionDiffConflict extends DiffConflict {
   private final Exception exception;

   public ExceptionDiffConflict(DescriptorBean editBean, Exception exception) {
      super((BeanUpdateEvent)null, (BeanUpdateEvent.PropertyUpdate)null, editBean);
      this.exception = exception;
   }

   public String toString() {
      return textFormatter.getExceptionDiffConflictString(this.getBeanFullName(), this.exception);
   }

   public ConflictDescriptorDiff resolve(ConflictDescriptorDiff orig2CurrDiff) {
      return orig2CurrDiff;
   }

   public boolean isResolvable() {
      return false;
   }

   public String getResolveDescription() {
      return textFormatter.getExceptionDiffConflictResolve();
   }

   public Exception getException() {
      return this.exception;
   }
}
