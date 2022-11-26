package kodo.remote;

import java.io.Serializable;
import org.apache.openjpa.kernel.Broker;

class BrokerFlags implements Serializable {
   private boolean _nontransRead = false;
   private boolean _nontransWrite = false;
   private boolean _retainState = false;
   private int _restoreState = 0;
   private int _autoClear = 0;
   private boolean _optimistic = false;
   private boolean _multithreaded = false;

   public boolean latest(Broker broker) {
      boolean ret = false;
      boolean val = broker.getNontransactionalRead();
      if (val != this._nontransRead) {
         ret = true;
      }

      this._nontransRead = val;
      val = broker.getNontransactionalWrite();
      if (val != this._nontransWrite) {
         ret = true;
      }

      this._nontransWrite = val;
      val = broker.getRetainState();
      if (val != this._retainState) {
         ret = true;
      }

      this._retainState = val;
      int i = broker.getRestoreState();
      if (i != this._restoreState) {
         ret = true;
      }

      this._restoreState = i;
      i = broker.getAutoClear();
      if (i != this._autoClear) {
         ret = true;
      }

      this._autoClear = i;
      val = broker.getOptimistic();
      if (val != this._optimistic) {
         ret = true;
      }

      this._optimistic = val;
      val = broker.getMultithreaded();
      if (val != this._multithreaded) {
         ret = true;
      }

      this._multithreaded = val;
      return ret;
   }

   public void sync(Broker broker) {
      broker.setNontransactionalRead(this._nontransRead);
      broker.setNontransactionalWrite(this._nontransWrite);
      broker.setRetainState(this._retainState);
      broker.setRestoreState(this._restoreState);
      broker.setAutoClear(this._autoClear);
      broker.setOptimistic(this._optimistic);
      broker.setMultithreaded(this._multithreaded);
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("BrokerFlags:");
      buf.append("\n\tNontransactionalRead:").append(this._nontransRead);
      buf.append("\n\tNontransactionalWrite:").append(this._nontransWrite);
      buf.append("\n\tRetainState:").append(this._retainState);
      buf.append("\n\tRestoreState:").append(this._restoreState);
      buf.append("\n\tAutoClear:").append(this._autoClear);
      buf.append("\n\tOptimistic:").append(this._optimistic);
      buf.append("\n\tMultithreaded:").append(this._multithreaded);
      return buf.toString();
   }
}
