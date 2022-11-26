package weblogic.rmi.utils.enumerations;

import java.util.Enumeration;
import weblogic.rmi.extensions.server.SmartStubInfo;

public abstract class BatchingEnumerationBase implements Enumeration, RemoteBatchingEnumeration, SmartStubInfo {
   private Object[] batch = null;
   private RuntimeException pendingException;
   private int defaultBatchSize;

   public abstract Object getSmartStub(Object var1);

   public abstract boolean hasMoreElements();

   public abstract Object nextElement();

   public Enumeration nextBatch(int batchSize) {
      if (this.pendingException != null) {
         throw this.pendingException;
      } else {
         int index = 0;
         if (this.batch == null || this.batch.length < batchSize) {
            this.batch = new Object[batchSize];
         }

         try {
            while(this.hasMoreElements() && index < batchSize) {
               this.batch[index] = this.nextElement();
               ++index;
            }
         } catch (RuntimeException var4) {
            if (index <= 0) {
               throw var4;
            }

            this.pendingException = var4;
         }

         for(batchSize = index; index < this.batch.length; ++index) {
            this.batch[index] = null;
         }

         return new Batch(this.batch, batchSize);
      }
   }
}
