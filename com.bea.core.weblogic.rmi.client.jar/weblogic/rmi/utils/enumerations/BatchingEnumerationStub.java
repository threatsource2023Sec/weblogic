package weblogic.rmi.utils.enumerations;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Enumeration;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.rmi.extensions.server.SmartStub;

public class BatchingEnumerationStub extends SmartStub implements Enumeration, Externalizable {
   private static final boolean debug = true;
   private final int DEFAULT_BATCH_SIZE = 20;
   private transient RemoteBatchingEnumeration server;
   private Enumeration batch;
   private int batchSize;

   public BatchingEnumerationStub(Object delegate) {
      super(delegate);

      try {
         this.server = (RemoteBatchingEnumeration)delegate;
         this.batchSize = 20;
         this.batch = new EmptyEnumerator();
      } catch (ClassCastException var3) {
         throw new AssertionError(delegate.getClass().getName() + " does not implement RemoteBatchingEnumeration", var3);
      }
   }

   public boolean hasMoreElements() {
      return this.batch.hasMoreElements() || this.server.hasMoreElements();
   }

   public Object nextElement() {
      if (!this.batch.hasMoreElements()) {
         this.batch = this.nextBatch();
      }

      return this.batch.nextElement();
   }

   protected Enumeration nextBatch() {
      return this.server.nextBatch(this.batchSize);
   }

   public BatchingEnumerationStub() {
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      if (in instanceof WLObjectInput) {
         WLObjectInput wlIn = (WLObjectInput)in;
         this.batch = (Enumeration)wlIn.readObjectWL();
      } else {
         this.batch = (Enumeration)in.readObject();
      }

      if (this.batch == null) {
         this.batch = new EmptyEnumerator();
      }

      this.batchSize = in.readInt();
      super.readExternal(in);
      this.server = (RemoteBatchingEnumeration)this.getStubDelegate();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof WLObjectOutput) {
         WLObjectOutput wlOut = (WLObjectOutput)out;
         wlOut.writeObjectWL(this.batch);
      } else {
         out.writeObject(this.batch);
      }

      out.writeInt(this.batchSize);
      super.writeExternal(out);
   }
}
