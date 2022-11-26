package weblogic.rmi.utils.enumerations;

import java.util.Enumeration;

public final class BatchingEnumerationWrapper extends BatchingEnumerationBase {
   private Enumeration delegate;

   public BatchingEnumerationWrapper(Enumeration delegate, int batchSize) {
      this.delegate = delegate;
   }

   public Object getSmartStub(Object remoteDelegate) {
      return new BatchingEnumerationStub(remoteDelegate);
   }

   public boolean hasMoreElements() {
      return this.delegate.hasMoreElements();
   }

   public Object nextElement() {
      return this.delegate.nextElement();
   }
}
