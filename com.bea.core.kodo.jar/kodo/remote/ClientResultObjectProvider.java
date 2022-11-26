package kodo.remote;

import com.solarmetric.remote.Command;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.ObjectNotFoundException;

abstract class ClientResultObjectProvider implements ResultObjectProvider {
   static final int DEFAULT_FETCH_SIZE = 50;
   private static final int NEW = 0;
   private static final int OPENED = 1;
   private static final int FREED = 2;
   private final ClientStoreManager _store;
   private final FetchConfiguration _fetch;
   private LinkedMap _pcdatas = null;
   private Object[] _window = null;
   private BitSet _oids = null;
   private int _pos = -1;
   private int _size = -1;
   private int _idx = -1;
   private int _status = 0;
   private long _id = -1L;

   public ClientResultObjectProvider(ClientStoreManager store, FetchConfiguration fetch) {
      this._store = store;
      this._fetch = fetch;
   }

   public ClientStoreManager getStoreManager() {
      return this._store;
   }

   public FetchConfiguration getFetchConfiguration() {
      return this._fetch;
   }

   public boolean supportsRandomAccess() {
      return true;
   }

   public void open() throws Exception {
      if (this._status == 0) {
         this._status = 1;
         this._store.openChannel();
         this.cacheResults(0, true);
      }

   }

   public Object getResultObject() throws Exception {
      Object res = this._window[this._idx - this._pos];
      if (res instanceof Object[]) {
         Object[] orig = (Object[])((Object[])res);
         Object[] copy = null;

         for(int i = 0; i < orig.length; ++i) {
            if (orig[i] != null && this._oids != null && this._oids.get(i)) {
               if (copy == null) {
                  copy = new Object[orig.length];
                  System.arraycopy(orig, 0, copy, 0, orig.length);
               }

               copy[i] = this.find(orig[i]);
            }
         }

         if (copy != null) {
            res = copy;
         }
      } else if (res != null && this._oids != null && this._oids.get(0)) {
         res = this.find(res);
      }

      return res;
   }

   private Object find(Object oid) {
      Object obj = this._store.getBroker().find(oid, this._fetch, (BitSet)null, this._pcdatas, 4);
      if (obj == null) {
         throw new ObjectNotFoundException(oid);
      } else {
         return obj;
      }
   }

   public boolean next() throws Exception {
      return this.absolute(this._idx + 1);
   }

   public boolean absolute(int index) throws Exception {
      if (index < 0) {
         return false;
      } else if (this._size != -1 && index >= this._size) {
         return false;
      } else {
         int windowSize = this._window == null ? 0 : this._window.length;
         if (index < this._pos || index >= this._pos + windowSize) {
            this.cacheResults(index, false);
         }

         if (this._size != -1 && index >= this._size) {
            return false;
         } else {
            this._idx = index;
            return true;
         }
      }
   }

   private void cacheResults(int index, boolean initialize) {
      ResultCommand cmd = this.newResultCommand();
      cmd.setInitialize(initialize);
      cmd.setStartIndex(index);
      int numResults = this._fetch.getFetchBatchSize();
      if (numResults == 0) {
         numResults = 50;
      }

      cmd.setNumResults(numResults);
      cmd.setInitializeOnly(numResults != -1 && initialize && !this.getResultsOnInitialize());
      this.send(cmd);
      if (cmd.getInitialize()) {
         this._id = cmd.getClientId();
      }

      this._window = cmd.getResults();
      this._pcdatas = cmd.getPCDatas();
      Collection transferListeners = this._store.getTransferListeners();
      if (this._pcdatas != null && !transferListeners.isEmpty()) {
         byte[] buf = cmd.getExternalTransferBuffer();
         Broker broker = this._store.getBroker();
         Collection oids = this._pcdatas.asList();
         List sms = RemoteTransfers.getStateManagersFromPCDatas(broker, this._pcdatas, this._fetch, oids, this._store.getLog());
         RemoteTransfers.readExternalBuffer(sms, transferListeners, buf, this._store.getLog(), true);
      }

      if (this._window != null && this._window.length > 0) {
         this._oids = cmd.resultsAreObjectIds();
      }

      if (this._size == -1) {
         this._size = cmd.size();
      }

      this.setResponseState(cmd);
      if (cmd.isAllResults()) {
         this._pos = 0;
         this.free();
      } else {
         this._pos = index;
      }

   }

   public int size() throws Exception {
      if (this._size == -1) {
         ResultSizeCommand cmd = new ResultSizeCommand();
         this.send(cmd);
         this._size = cmd.size();
      }

      return this._size;
   }

   public void reset() throws Exception {
      this._idx = -1;
   }

   public void close() throws Exception {
      if (this._id != -1L && this._status == 1) {
         this.send(new CloseCommand());
      }

      this.free();
   }

   private void free() {
      if (this._status == 1) {
         try {
            this._store.closeChannel();
         } finally {
            this._status = 2;
         }
      }

   }

   public void handleCheckedException(Exception e) {
      throw new GeneralException(e);
   }

   private void send(Command cmd) {
      cmd.setClientId(this._id);
      this._store.send(cmd);
   }

   protected void setResponseState(ResultCommand cmd) {
   }

   protected abstract ResultCommand newResultCommand();

   protected abstract boolean getResultsOnInitialize();
}
