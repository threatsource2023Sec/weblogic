package weblogic.messaging.kernel.internal;

import java.util.Comparator;
import weblogic.messaging.kernel.KernelException;

final class SortingComparator implements Comparator {
   private final Comparator userComparator;
   private final KernelImpl kernel;
   private boolean compareElements;

   SortingComparator(Comparator userComparator, KernelImpl kernel, boolean compareElements) {
      this.userComparator = userComparator;
      this.kernel = kernel;
      this.compareElements = compareElements;
   }

   public int compare(Object o1, Object o2) {
      try {
         MessageReference ref1 = (MessageReference)o1;
         MessageReference ref2 = (MessageReference)o2;
         if (this.compareElements) {
            return this.userComparator.compare(new MessageElementImpl(ref1, this.kernel), new MessageElementImpl(ref2, this.kernel));
         } else {
            int ret = this.userComparator.compare(ref1.getMessage(this.kernel), ref2.getMessage(this.kernel));
            if (ret != 0) {
               return ret;
            } else if (ref1.getSequenceNumber() < ref2.getSequenceNumber()) {
               return -1;
            } else {
               return ref1.getSequenceNumber() > ref2.getSequenceNumber() ? 1 : 0;
            }
         }
      } catch (KernelException var6) {
         return 0;
      }
   }
}
