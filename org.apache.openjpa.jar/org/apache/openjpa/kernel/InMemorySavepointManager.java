package org.apache.openjpa.kernel;

public class InMemorySavepointManager implements SavepointManager {
   private boolean _preFlush = true;

   public boolean getPreFlush() {
      return this._preFlush;
   }

   public void setPreFlush(boolean preFlush) {
      this._preFlush = preFlush;
   }

   public OpenJPASavepoint newSavepoint(String name, Broker broker) {
      OpenJPASavepoint save = new OpenJPASavepoint(broker, name, true);
      if (this._preFlush) {
         broker.preFlush();
      }

      return save;
   }

   public boolean supportsIncrementalFlush() {
      return false;
   }
}
