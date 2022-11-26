package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.ImplHelper;

abstract class ResultCommand extends KodoCommand {
   private static final LinkedMap EMPTY_MAP = new LinkedMap(1);
   private boolean _initialize;
   private boolean _initOnly;
   private int _num;
   private int _size;
   private int _index;
   private Object[] _results;
   private BitSet _oids;
   private LinkedMap _pcdatas;
   private byte[] _buf;
   private long _bid;

   ResultCommand(byte code) {
      super(code);
      this._initialize = false;
      this._initOnly = false;
      this._num = -1;
      this._size = -1;
      this._index = 0;
      this._results = null;
      this._oids = null;
      this._pcdatas = null;
      this._buf = null;
      this._bid = 0L;
   }

   public ResultCommand(byte code, long brokerId) {
      this(code);
      this._bid = brokerId;
   }

   public boolean getInitialize() {
      return this._initialize;
   }

   public void setInitialize(boolean init) {
      this._initialize = init;
   }

   public boolean getInitializeOnly() {
      return this._initOnly;
   }

   public void setInitializeOnly(boolean init) {
      this._initOnly = init;
   }

   public void setNumResults(int num) {
      this._num = num;
   }

   public void setStartIndex(int index) {
      this._index = index;
   }

   public Object[] getResults() {
      return this._results;
   }

   public BitSet resultsAreObjectIds() {
      return this._oids;
   }

   public LinkedMap getPCDatas() {
      return this._pcdatas == null ? EMPTY_MAP : this._pcdatas;
   }

   public boolean isAllResults() {
      return this._index == 0 && this._size != -1;
   }

   public int size() {
      return this._size;
   }

   public byte[] getExternalTransferBuffer() {
      return this._buf;
   }

   protected void execute(KodoContextFactory context) {
      Object obj = context.getContext(this.getClientId());
      Broker broker = null;
      ContextualResult ctx;
      Result res;
      if (this._initialize) {
         broker = (Broker)obj;
         res = this.initialize(broker);
         ctx = new ContextualResult();
         ctx.contextId = this.getClientId();
         ctx.result = res;
      } else {
         ctx = (ContextualResult)obj;
         broker = (Broker)context.getContext(ctx.contextId);
         res = ctx.result;
      }

      RemotePCDataGenerator gen = context.getPCDataGenerator();
      if (!this._initOnly && this._num != 0) {
         FetchConfiguration fetch = this.getFetchConfiguration(res);
         if (this._num == -1) {
            this._index = 0;
         }

         Iterator itr = this.iterator(res, this._index);
         List results = null;
         Object[] copy = null;

         int idx;
         for(idx = 0; itr.hasNext() && (this._num == -1 || idx < this._num); ++idx) {
            obj = itr.next();
            Object add;
            if (!(obj instanceof Object[])) {
               add = this.replacePC(gen, obj, 0, fetch, broker);
            } else {
               Object[] orig = (Object[])((Object[])obj);
               copy = null;

               for(int i = 0; i < orig.length; ++i) {
                  add = this.replacePC(gen, orig[i], i, fetch, broker);
                  if (add != orig[i]) {
                     if (copy == null) {
                        copy = new Object[orig.length];
                        System.arraycopy(orig, 0, copy, 0, orig.length);
                     }

                     copy[i] = add;
                  }
               }

               add = copy == null ? orig : copy;
            }

            if (results == null) {
               results = new ArrayList();
            }

            results.add(add);
         }

         if (!itr.hasNext()) {
            this._size = this._index + idx;
         }

         if (results != null) {
            this._results = results.toArray();
         }
      }

      Collection listeners = context.getTransferListeners(this._bid);
      if (this._pcdatas != null && !listeners.isEmpty()) {
         List sms = RemoteTransfers.getStateManagersFromPCDatas(broker, (LinkedMap)null, (FetchConfiguration)null, this._pcdatas.asList(), context.getLog());
         this._buf = RemoteTransfers.writeExternalBuffer(sms, listeners, context.getLog(), true);
      }

      if (this._index == 0 && this._size != -1) {
         if (!this._initialize) {
            context.removeContext(this.getClientId());
         }

         ImplHelper.close(res);
      } else if (this._initialize) {
         this.setClientId(context.newClientId());
         context.setContext(this.getClientId(), ctx);
      }

   }

   private Object replacePC(RemotePCDataGenerator gen, Object res, int index, FetchConfiguration fetch, Broker broker) {
      Object oid = broker.getObjectId(res);
      if (oid == null) {
         return res;
      } else {
         if (this._oids == null) {
            this._oids = new BitSet();
         }

         this._oids.set(index);
         this.addPCData(broker, gen, res, oid, fetch);
         return oid;
      }
   }

   private void addPCData(Broker broker, RemotePCDataGenerator gen, Object pc, Object oid, FetchConfiguration fetch) {
      if (oid != null) {
         if (this._pcdatas == null) {
            this._pcdatas = new LinkedMap();
         } else if (this._pcdatas.containsKey(oid)) {
            return;
         }

         OpenJPAStateManager sm = getStateManager(broker, pc, oid, true);
         FieldMetaData[] fmds = sm.getMetaData().getFields();
         BitSet fields = null;

         for(int i = 0; i < fmds.length; ++i) {
            if (fetch.requiresFetch(fmds[i]) != 0) {
               if (fields == null) {
                  fields = new BitSet(fmds.length);
               }

               fields.set(i);
            }
         }

         RemotePCData pcdata = null;
         if (gen == null) {
            pcdata = new RemotePCDataImpl(oid, sm.getMetaData());
         } else {
            pcdata = (RemotePCData)gen.generatePCData(oid, sm.getMetaData());
         }

         ((RemotePCData)pcdata).store(sm, fields);
         this._pcdatas.put(oid, pcdata);
         if (fields != null) {
            int i = 0;

            for(int len = fields.length(); i < len; ++i) {
               if (fields.get(i)) {
                  Object val = ((RemotePCData)pcdata).getData(i);
                  if (val != null) {
                     if (fmds[i].isDeclaredTypePC() && !fmds[i].isEmbedded()) {
                        this.addPCData(broker, gen, (Object)null, val, fetch);
                     } else if ((fmds[i].getDeclaredTypeCode() == 11 || fmds[i].getDeclaredTypeCode() == 12) && fmds[i].getElement().isDeclaredTypePC() && !fmds[i].getElement().isEmbedded()) {
                        this.addPCDatas(broker, gen, (Collection)val, fetch);
                     } else if (fmds[i].getDeclaredTypeCode() == 13) {
                        if (fmds[i].getKey().isDeclaredTypePC() && !fmds[i].getKey().isEmbedded()) {
                           this.addPCDatas(broker, gen, ((Map)val).keySet(), fetch);
                        }

                        if (fmds[i].getElement().isDeclaredTypePC() && !fmds[i].getElement().isEmbedded()) {
                           this.addPCDatas(broker, gen, ((Map)val).values(), fetch);
                        }
                     }
                  }
               }
            }
         }

      }
   }

   private void addPCDatas(Broker broker, RemotePCDataGenerator gen, Collection oids, FetchConfiguration fetch) {
      if (oids != null) {
         Iterator itr = oids.iterator();

         while(itr.hasNext()) {
            this.addPCData(broker, gen, (Object)null, itr.next(), fetch);
         }
      }

   }

   protected abstract Result initialize(Broker var1);

   protected abstract Iterator iterator(Result var1, int var2);

   protected abstract FetchConfiguration getFetchConfiguration(Result var1);

   protected void read(ObjectInput in) throws Exception {
      this._initialize = in.readBoolean();
      this._initOnly = in.readBoolean();
      this._index = in.readInt();
      this._num = in.readInt();
      this._bid = in.readLong();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeBoolean(this._initialize);
      out.writeBoolean(this._initOnly);
      out.writeInt(this._index);
      out.writeInt(this._num);
      out.writeLong(this._bid);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      if (this._initialize) {
         this.setClientId(in.readLong());
      }

      this._index = in.readInt();
      this._size = in.readInt();
      this._results = (Object[])((Object[])in.readObject());
      this._oids = (BitSet)in.readObject();
      this._pcdatas = (LinkedMap)in.readObject();
      this._buf = (byte[])((byte[])in.readObject());
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      if (this._initialize) {
         out.writeLong(this.getClientId());
      }

      out.writeInt(this._index);
      out.writeInt(this._size);
      out.writeObject(this._results);
      out.writeObject(this._oids);
      out.writeObject(this._pcdatas);
      out.writeObject(this._buf);
   }

   public static class ContextualResult {
      public Result result;
      public long contextId;
   }
}
