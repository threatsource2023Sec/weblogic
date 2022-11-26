package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;

class LoadCommand extends KodoCommand {
   private Object[] _ids;
   private BitSet _fields;
   private int _load;
   private FetchConfiguration _fetch;
   private Map _pcdatas;
   private byte[] _buf;

   LoadCommand() {
      super((byte)9);
      this._ids = null;
      this._fields = null;
      this._load = 0;
      this._fetch = null;
      this._pcdatas = null;
      this._buf = null;
   }

   public LoadCommand(Object[] ids, int load, FetchConfiguration fetch) {
      this();
      this._ids = ids;
      this._load = load;
      this._fetch = fetch;
   }

   public LoadCommand(Object id, BitSet fields, FetchConfiguration fetch) {
      this();
      this._ids = new Object[]{id};
      this._fields = fields;
      this._fetch = fetch;
   }

   public Object[] getIds() {
      return this._ids;
   }

   public BitSet getFields() {
      return this._fields;
   }

   public int getForceLoad() {
      return this._load;
   }

   public FetchConfiguration getFetchConfiguration() {
      return this._fetch;
   }

   public Map getPCDatas() {
      return this._pcdatas;
   }

   public void execute(KodoContextFactory context) {
      Broker broker = (Broker)context.getContext(this.getClientId());
      RemotePCDataGenerator gen = context.getPCDataGenerator();
      List sms = null;

      for(int i = 0; i < this._ids.length; ++i) {
         this.load(broker, gen, this._ids[i], this._fields, this._load);
         OpenJPAStateManager sm = broker.getStateManager(broker.findCached(this._ids[i], (FindCallbacks)null));
         if (sm != null) {
            if (sms == null) {
               sms = new ArrayList(this._ids.length - i);
            }

            sms.add(sm);
         }
      }

      this._buf = RemoteTransfers.writeExternalBuffer(sms, context.getTransferListeners(this.getClientId()), context.getLog(), true);
   }

   private void load(Broker broker, RemotePCDataGenerator gen, Object id, BitSet set, int load) {
      if (id != null) {
         OpenJPAStateManager sm = null;
         Object pc = null;
         if (load == 3) {
            pc = broker.findCached(id, (FindCallbacks)null);
            if (pc != null) {
               sm = broker.getStateManager(pc);
               sm.beforeRefresh(true);
            }
         } else if (this._pcdatas != null && this._pcdatas.containsKey(id)) {
            return;
         }

         pc = broker.find(id, this._fetch, (BitSet)null, (Object)null, 0);
         if (pc != null) {
            if (sm == null) {
               sm = broker.getStateManager(pc);
               if (sm == null) {
                  return;
               }
            }

            ClassMetaData meta = sm.getMetaData();
            RemotePCData pcdata = null;
            if (gen == null) {
               pcdata = new RemotePCDataImpl(id, meta);
            } else {
               pcdata = (RemotePCData)gen.generatePCData(id, meta);
            }

            if (this._pcdatas == null) {
               this._pcdatas = new HashMap();
            }

            this._pcdatas.put(id, pcdata);
            BitSet fields = null;
            FieldMetaData[] fmds = meta.getFields();

            for(int i = 0; i < fmds.length; ++i) {
               if (load == 2 || set != null && set.get(i) || set == null && this._fetch.requiresFetch(fmds[i]) != 0) {
                  if (fields == null) {
                     fields = new BitSet();
                  }

                  fields.set(i);
               }
            }

            ((RemotePCData)pcdata).store(sm, fields);
            if (fields != null) {
               int i = 0;

               for(int len = fields.length(); i < len; ++i) {
                  if (fields.get(i)) {
                     Object val = ((RemotePCData)pcdata).getData(i);
                     if (val != null) {
                        FieldMetaData fmd = meta.getField(i);
                        if (fmd.isDeclaredTypePC() && !fmd.isEmbedded()) {
                           this.load(broker, gen, val, (BitSet)null, 0);
                        } else if ((fmd.getDeclaredTypeCode() == 11 || fmd.getDeclaredTypeCode() == 12) && fmd.getElement().isDeclaredTypePC() && !fmd.getElement().isEmbedded()) {
                           this.load(broker, gen, (Collection)val);
                        } else if (fmd.getDeclaredTypeCode() == 13) {
                           if (fmd.getKey().isDeclaredTypePC() && !fmd.getKey().isEmbedded()) {
                              this.load(broker, gen, ((Map)val).keySet());
                           }

                           if (fmd.getElement().isDeclaredTypePC() && !fmd.getElement().isEmbedded()) {
                              this.load(broker, gen, ((Map)val).values());
                           }
                        }
                     }
                  }
               }
            }

         }
      }
   }

   private void load(Broker broker, RemotePCDataGenerator gen, Collection oids) {
      if (oids != null) {
         Iterator itr = oids.iterator();

         while(itr.hasNext()) {
            this.load(broker, gen, itr.next(), (BitSet)null, 0);
         }
      }

   }

   public void readExternalBufferForDownload(ClientStoreManager store, OpenJPAStateManager sm) throws Exception {
      this.readExternalBufferForDownload(store, (Collection)Collections.singleton(sm));
   }

   public void readExternalBufferForDownload(ClientStoreManager store, Collection sms) throws Exception {
      RemoteTransfers.readExternalBuffer(sms, store.getTransferListeners(), this._buf, store.getLog(), true);
      this._buf = null;
   }

   protected void read(ObjectInput in) throws Exception {
      this._ids = (Object[])((Object[])in.readObject());
      this._fields = (BitSet)in.readObject();
      this._load = in.readInt();
      this._fetch = (FetchConfiguration)in.readObject();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._ids);
      out.writeObject(this._fields);
      out.writeInt(this._load);
      out.writeObject(this._fetch);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._pcdatas = (Map)in.readObject();
      this._buf = (byte[])((byte[])in.readObject());
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeObject(this._pcdatas);
      out.writeObject(this._buf);
   }
}
