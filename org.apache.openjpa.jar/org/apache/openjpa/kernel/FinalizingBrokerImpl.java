package org.apache.openjpa.kernel;

public class FinalizingBrokerImpl extends BrokerImpl {
   protected void finalize() throws Throwable {
      super.finalize();
      if (!this.isClosed()) {
         this.free();
      }

   }
}
