package org.glassfish.hk2.extras.operation;

import java.io.Closeable;
import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface OperationHandle extends Closeable {
   OperationIdentifier getIdentifier();

   OperationState getState();

   Set getActiveThreads();

   void suspend(long var1);

   void suspend();

   void resume(long var1) throws IllegalStateException;

   void resume() throws IllegalStateException;

   /** @deprecated */
   default void closeOperation() {
      this.close();
   }

   void close();

   Object getOperationData();

   void setOperationData(Object var1);
}
