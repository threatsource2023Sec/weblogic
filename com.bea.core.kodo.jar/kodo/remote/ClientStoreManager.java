package kodo.remote;

import com.solarmetric.remote.Command;
import com.solarmetric.remote.Transport;
import com.solarmetric.remote.TransportException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.map.IdentityMap;
import org.apache.commons.collections.set.MapBackedSet;
import org.apache.openjpa.event.FlushTransactionListener;
import org.apache.openjpa.event.TransactionEvent;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.ArrayStateImage;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.Id;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.OpenJPAId;
import org.apache.openjpa.util.OptimisticException;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UnsupportedException;

class ClientStoreManager implements StoreManager, FlushTransactionListener {
   private static final Localizer _loc = Localizer.forPackage(ClientStoreManager.class);
   private Broker _broker = null;
   private Log _log = null;
   private long _id = -1L;
   private Transport _transport = null;
   private KodoCommandIO _io = null;
   private Collection _listeners;
   private FetchConfiguration _fetch;
   private Class _oidClass;
   private BrokerFlags _brokerFlags;
   private Transport.Channel _channel;
   private int _openCount;
   private Set _persisted;
   private Set _uploaded;

   ClientStoreManager() {
      this._listeners = Collections.EMPTY_LIST;
      this._fetch = null;
      this._oidClass = null;
      this._brokerFlags = null;
      this._channel = null;
      this._openCount = 0;
      this._persisted = null;
      this._uploaded = null;
   }

   public Broker getBroker() {
      return this._broker;
   }

   public long getBrokerId() {
      return this._id;
   }

   public KodoCommandIO getCommandIO() {
      return this._io;
   }

   public Log getLog() {
      return this._log;
   }

   public Collection getTransferListeners() {
      return this._listeners;
   }

   public void setContext(StoreContext ctx) {
      ClientConfiguration conf = (ClientConfiguration)ctx.getConfiguration();
      this._broker = ctx.getBroker();
      this._log = conf.getLog("kodo.Remote");
      this._io = conf.getCommandIO();
      this._transport = (Transport)conf.getConnectionFactory();
      this._brokerFlags = new BrokerFlags();
      this._brokerFlags.latest(this._broker);
      this._broker.addTransactionListener(this);
      ClientBrokerFactory factory = (ClientBrokerFactory)this._broker.getBrokerFactory();
      this._listeners = new ArrayList(factory.getTransferListeners());
      NewBrokerCommand cmd = new NewBrokerCommand(this._broker.getConnectionUserName(), this._broker.getConnectionPassword(), this._brokerFlags, this._listeners);

      try {
         this.send(cmd);
      } catch (RuntimeException var6) {
         this.closeChannelInternal();
         throw var6;
      }

      this._id = cmd.getClientId();
      this._oidClass = cmd.getDataStoreIdType();
      this._fetch = cmd.getFetchConfiguration();
   }

   public void beginOptimistic() {
      this.send(new BeginCommand());
   }

   public void rollbackOptimistic() {
      this.rollback();
   }

   public void begin() {
      if (!this._broker.isActive()) {
         this.send(new BeginCommand());
      }

   }

   public void commit() {
      this._uploaded = null;
      this.send(new CommitCommand());
   }

   public void rollback() {
      this._uploaded = null;

      try {
         this.send(new RollbackCommand());
      } catch (TransportException var2) {
         if (this._log.isWarnEnabled()) {
            this._log.warn(_loc.get("cant-rollback"));
         }

         throw var2;
      }
   }

   public boolean exists(OpenJPAStateManager sm, Object edata) {
      ExistsCommand cmd = new ExistsCommand(sm.getObjectId());
      this.send(cmd);
      return cmd.exists();
   }

   public boolean syncVersion(OpenJPAStateManager sm, Object edata) {
      return false;
   }

   public boolean initialize(OpenJPAStateManager sm, PCState state, FetchConfiguration fetch, Object edata) {
      Object oid = sm.getObjectId();
      Map pcdatas = (Map)edata;
      RemotePCData pcdata;
      if (pcdatas != null) {
         pcdata = (RemotePCData)pcdatas.get(oid);
         if (pcdata != null) {
            this.initialize(sm, state, fetch, pcdata, pcdatas);
            return true;
         }
      }

      LoadCommand cmd = new LoadCommand(oid, (BitSet)null, fetch);
      this.openChannel();

      boolean var9;
      try {
         this.send(cmd);
         pcdatas = cmd.getPCDatas();
         if (pcdatas != null) {
            pcdata = (RemotePCData)pcdatas.get(oid);
            if (pcdata == null) {
               var9 = false;
               return var9;
            }

            this.initialize(sm, state, fetch, pcdata, pcdatas);
            cmd.readExternalBufferForDownload(this, sm);
            var9 = true;
            return var9;
         }

         var9 = false;
      } catch (OpenJPAException var14) {
         throw var14;
      } catch (Exception var15) {
         throw new StoreException(var15);
      } finally {
         this.closeChannel();
      }

      return var9;
   }

   private void initialize(OpenJPAStateManager sm, PCState state, FetchConfiguration fetch, RemotePCData pcdata, Map pcdatas) {
      sm.initialize(pcdata.getType(), state);
      pcdata.load(sm, fetch, pcdatas);
   }

   public boolean load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch, int lockLevel, Object edata) {
      Object id = sm.getId();
      Map pcdatas = (Map)edata;
      RemotePCData pcdata;
      if (pcdatas != null) {
         pcdata = (RemotePCData)pcdatas.get(id);
         if (pcdata != null) {
            pcdata.load(sm, fields, fetch, pcdatas);
            return true;
         }
      }

      LoadCommand cmd = new LoadCommand(id, fields, fetch);
      this.openChannel();

      boolean var10;
      try {
         this.send(cmd);
         pcdatas = cmd.getPCDatas();
         if (pcdatas == null) {
            var10 = false;
            return var10;
         }

         pcdata = (RemotePCData)pcdatas.get(id);
         if (pcdata != null) {
            pcdata.load(sm, fields, fetch, pcdatas);
            cmd.readExternalBufferForDownload(this, sm);
            var10 = true;
            return var10;
         }

         var10 = false;
      } catch (OpenJPAException var15) {
         throw var15;
      } catch (Exception var16) {
         throw new StoreException(var16);
      } finally {
         this.closeChannel();
      }

      return var10;
   }

   public Collection loadAll(Collection sms, PCState state, int load, FetchConfiguration fetch, Object edata) {
      Object[] ids = new Object[sms.size()];
      int idx = 0;

      for(Iterator itr = sms.iterator(); itr.hasNext(); ++idx) {
         ids[idx] = ((OpenJPAStateManager)itr.next()).getId();
      }

      LoadCommand cmd = new LoadCommand(ids, load, fetch);
      this.openChannel();

      Object var21;
      try {
         this.send(cmd);
         Map pcdatas = cmd.getPCDatas();
         Collection failed = null;
         Iterator itr = sms.iterator();

         while(itr.hasNext()) {
            OpenJPAStateManager sm = (OpenJPAStateManager)itr.next();
            if (sm != null) {
               if (pcdatas == null) {
                  failed = this.addFailedId(sm, failed);
               } else {
                  RemotePCData pcdata = (RemotePCData)pcdatas.get(sm.getId());
                  if (pcdata == null) {
                     failed = this.addFailedId(sm, failed);
                  } else {
                     if (sm.getManagedInstance() == null) {
                        sm.initialize(pcdata.getType(), state);
                     }

                     pcdata.load(sm, fetch, pcdatas);
                  }
               }
            }
         }

         cmd.readExternalBufferForDownload(this, sms);
         var21 = failed == null ? Collections.EMPTY_LIST : failed;
      } catch (OpenJPAException var18) {
         throw var18;
      } catch (Exception var19) {
         throw new StoreException(var19);
      } finally {
         this.closeChannel();
      }

      return (Collection)var21;
   }

   private Collection addFailedId(OpenJPAStateManager sm, Collection failed) {
      if (failed == null) {
         failed = new ArrayList();
      }

      ((Collection)failed).add(sm.getId());
      return (Collection)failed;
   }

   public void beforeStateChange(OpenJPAStateManager sm, PCState fromState, PCState toState) {
   }

   public Collection flush(Collection sms) {
      Collection inserts = null;
      Collection updates = null;
      Collection deletes = null;
      List insSMs = null;
      List upSMs = null;
      int uploads = 0;
      Iterator itr = sms.iterator();

      while(true) {
         OpenJPAStateManager sm;
         while(itr.hasNext()) {
            sm = (OpenJPAStateManager)itr.next();
            RemotePCData pcdata = null;
            if (sm.getPCState() == PCState.PNEW && !sm.isFlushed()) {
               pcdata = this.newPCData(sm);
               pcdata.setRemoteFlush(true);
               pcdata.store(sm);
               if (inserts == null) {
                  inserts = new ArrayList();
                  insSMs = new ArrayList();
               }

               inserts.add(pcdata);
               insSMs.add(sm);
               ++uploads;
            } else if (sm.getPCState() != PCState.PNEWFLUSHEDDELETED && sm.getPCState() != PCState.PDELETED && sm.getPCState() != PCState.PNEWDELETED) {
               if (sm.getPCState() == PCState.PDIRTY && (!sm.isFlushed() || sm.isFlushedDirty()) || sm.getPCState() == PCState.PNEW && sm.isFlushedDirty()) {
                  BitSet dirty = sm.getDirty();
                  if (sm.isFlushed()) {
                     dirty = (BitSet)dirty.clone();
                     dirty.andNot(sm.getFlushed());
                  }

                  if (dirty.length() > 0) {
                     pcdata = this.newPCData(sm);
                     pcdata.setRemoteFlush(true);
                     pcdata.store(sm, dirty);
                  }

                  if (updates == null) {
                     updates = new ArrayList();
                     upSMs = new ArrayList();
                  }

                  updates.add(pcdata);
                  upSMs.add(sm);
                  ++uploads;
               }
            } else {
               if (deletes == null) {
                  deletes = new ArrayList();
               }

               deletes.add(sm.getObjectId());
            }
         }

         if (this._persisted != null) {
            this._persisted.clear();
         }

         if (inserts == null && updates == null && deletes == null) {
            return Collections.EMPTY_LIST;
         }

         RemotePCData[] insertArr = inserts == null ? null : (RemotePCData[])((RemotePCData[])inserts.toArray(new RemotePCData[inserts.size()]));
         RemotePCData[] updateArr = updates == null ? null : (RemotePCData[])((RemotePCData[])updates.toArray(new RemotePCData[updates.size()]));
         Object[] deleteArr = deletes == null ? null : deletes.toArray();
         FlushCommand cmd = new FlushCommand(insertArr, updateArr, deleteArr);
         this.send(cmd);
         Object[] failed = cmd.getFailedIds();
         int i;
         if (failed != null && failed.length > 0) {
            Collection errs = new ArrayList(failed.length);

            for(i = 0; i < failed.length; ++i) {
               errs.add(new OptimisticException(this.findManagedInstance(failed[i], sms)));
            }

            return errs;
         }

         RemotePCData[] inserted = cmd.getInsertPCDatas();
         if (inserted != null) {
            for(i = 0; i < inserted.length; ++i) {
               sm = (OpenJPAStateManager)insSMs.get(i);
               if (inserted[i] != null) {
                  if (sm.getObjectId() == null) {
                     sm.setObjectId(inserted[i].getId());
                  }

                  inserted[i].load(sm, this._broker.getFetchConfiguration(), (Object)null);
               } else if (sm.getObjectId() == null) {
                  sm.setObjectId(sm.getId());
               }
            }
         }

         Object[] versions = cmd.getUpdateVersions();
         if (versions != null) {
            for(int i = 0; i < versions.length; ++i) {
               if (versions[i] != null) {
                  ((OpenJPAStateManager)upSMs.get(i)).setNextVersion(versions[i]);
               }
            }
         }

         if (!this._listeners.isEmpty() && uploads > 0) {
            this._uploaded = new HashSet(uploads);
            if (insSMs != null) {
               this._uploaded.addAll(insSMs);
            }

            if (upSMs != null) {
               this._uploaded.addAll(upSMs);
            }
         }

         return Collections.EMPTY_LIST;
      }
   }

   private Object findManagedInstance(Object id, Collection sms) {
      Iterator itr = sms.iterator();

      OpenJPAStateManager sm;
      do {
         if (!itr.hasNext()) {
            return id;
         }

         sm = (OpenJPAStateManager)itr.next();
      } while(!sm.getId().equals(id));

      return sm.getManagedInstance();
   }

   public boolean assignObjectId(OpenJPAStateManager sm, boolean preFlush) {
      if (preFlush) {
         return false;
      } else {
         ClassMetaData meta = sm.getMetaData();
         Object oid = null;
         if (meta.getIdentityType() == 2) {
            oid = ApplicationIds.create(sm.getPersistenceCapable(), meta);
         }

         PersistCommand cmd = new PersistCommand(sm.getId(), oid, meta.getDescribedType());
         this.send(cmd);
         oid = cmd.getObjectId();
         if (oid == null) {
            if (this._persisted == null) {
               this._persisted = MapBackedSet.decorate(new IdentityMap(5));
            }

            this._persisted.add(sm);
            return false;
         } else {
            if (meta.getIdentityType() == 2) {
               Object[] vals = ApplicationIds.toPKValues(oid, meta);
               FieldMetaData[] pks = meta.getPrimaryKeyFields();

               for(int i = 0; i < pks.length; ++i) {
                  sm.storeField(pks[i].getIndex(), vals[i]);
               }
            }

            sm.setObjectId(oid);
            return true;
         }
      }
   }

   public boolean assignField(OpenJPAStateManager sm, int field, boolean preFlush) {
      if (preFlush) {
         return false;
      } else {
         FieldMetaData fmd = sm.getMetaData().getField(field);
         Object val = ImplHelper.generateFieldValue(this._broker, fmd);
         if (val == null) {
            return false;
         } else {
            sm.store(field, val);
            return true;
         }
      }
   }

   public Class getManagedType(Object oid) {
      if (oid instanceof OpenJPAId) {
         return ((OpenJPAId)oid).getType();
      } else {
         PCTypeCommand cmd = new PCTypeCommand(oid);
         this.send(cmd);
         return cmd.getPCType();
      }
   }

   public Class getDataStoreIdType(ClassMetaData meta) {
      if (this._oidClass != null) {
         return this._oidClass;
      } else {
         DataStoreIdTypeCommand cmd = new DataStoreIdTypeCommand(meta.getDescribedType());
         this.send(cmd);
         return cmd.getDataStoreIdType();
      }
   }

   public Object copyDataStoreId(Object oid, ClassMetaData meta) {
      if (oid.getClass() == Id.class) {
         Id id = (Id)oid;
         return new Id(meta.getDescribedType(), id.getId(), id.hasSubclasses());
      } else {
         CopyDataStoreIdCommand cmd = new CopyDataStoreIdCommand(oid);
         this.send(cmd);
         return cmd.getCopy();
      }
   }

   public Object newDataStoreId(Object val, ClassMetaData meta) {
      if (!(val instanceof Id) && val.getClass() != this._oidClass) {
         if (val instanceof String && this._oidClass == Id.class) {
            return new Id((String)val, this._broker.getConfiguration(), this._broker.getClassLoader());
         } else {
            NewDataStoreIdCommand cmd = new NewDataStoreIdCommand(val, meta.getDescribedType());
            this.send(cmd);
            return cmd.getObjectId();
         }
      } else {
         return val;
      }
   }

   public Object getClientConnection() {
      throw new UnsupportedException(_loc.get("get-conn"));
   }

   public void retainConnection() {
      this.openChannel();
   }

   public void releaseConnection() {
      this.closeChannel();
   }

   public ResultObjectProvider executeExtent(ClassMetaData meta, boolean subs, FetchConfiguration fetch) {
      return new ExtentResultObjectProvider(this, meta, subs, fetch);
   }

   public StoreQuery newQuery(String language) {
      return new ClientStoreQuery(this, language);
   }

   public FetchConfiguration newFetchConfiguration() {
      return this._fetch;
   }

   public Seq getDataStoreIdSequence(ClassMetaData meta) {
      return new ClientSeq(meta);
   }

   public Seq getValueSequence(FieldMetaData fmd) {
      return new ClientSeq(fmd);
   }

   public int compareVersion(OpenJPAStateManager sm, Object v1, Object v2) {
      if (v1 == v2) {
         return 3;
      } else if (v1 != null && v2 != null) {
         if (v1 instanceof Comparable) {
            int cmp = ((Comparable)v1).compareTo(v2);
            if (cmp < 0) {
               return 2;
            } else {
               return cmp == 0 ? 3 : 1;
            }
         } else if (ArrayStateImage.isImage(v1)) {
            return ArrayStateImage.sameVersion((Object[])((Object[])v1), (Object[])((Object[])v2)) ? 3 : 4;
         } else {
            CompareVersionCommand cmd = new CompareVersionCommand(sm.getObjectId(), v1, v2);
            this.send(cmd);
            return cmd.compareVersion();
         }
      } else {
         return 3;
      }
   }

   private RemotePCData newPCData(OpenJPAStateManager sm) {
      Object oid = sm.getObjectId();
      boolean newInst = false;
      if (oid == null) {
         oid = sm.getId();
         newInst = this._persisted == null || !this._persisted.contains(sm);
      }

      RemotePCDataGenerator gen = this._io.getPCDataGenerator();
      Object pc;
      if (gen == null) {
         pc = new RemotePCDataImpl(oid, sm.getMetaData());
      } else {
         pc = (RemotePCData)gen.generatePCData(oid, sm.getMetaData());
      }

      ((RemotePCData)pc).setNewInstance(newInst);
      return (RemotePCData)pc;
   }

   public boolean cancelAll() {
      CancelAllCommand cmd = new CancelAllCommand();
      this.send(cmd);
      return cmd.canceled();
   }

   public void close() {
      try {
         this.send(new CloseCommand());
      } finally {
         this.closeChannelInternal();
      }

   }

   void openChannel() {
      this._broker.lock();

      try {
         ++this._openCount;
         if (this._openCount == 1) {
            try {
               if (this._log.isTraceEnabled()) {
                  this._log.trace(_loc.get("open-channel"));
               }

               this._channel = this._transport.getClientChannel();
            } catch (OpenJPAException var6) {
               this._openCount = 0;
               throw var6;
            } catch (Exception var7) {
               this._openCount = 0;
               throw new GeneralException(_loc.get("cant-open"), var7);
            }
         }
      } finally {
         this._broker.unlock();
      }

   }

   void closeChannel() {
      this._broker.lock();

      try {
         --this._openCount;
         if (this._openCount == 0 && this._channel != null) {
            this.closeChannelInternal();
         }
      } finally {
         this._broker.unlock();
      }

   }

   private void closeChannelInternal() {
      try {
         if (this._channel != null) {
            if (this._log.isTraceEnabled()) {
               this._log.trace(_loc.get("close-channel"));
            }

            this._channel.close();
         }
      } catch (Exception var5) {
         if (this._log.isWarnEnabled()) {
            this._log.warn(_loc.get("bad-close"), var5);
         }
      } finally {
         this._channel = null;
      }

   }

   void send(Command cmd) {
      this._broker.lock();

      try {
         this.openChannel();
         Command sending = null;

         try {
            if (this._brokerFlags.latest(this._broker)) {
               sending = new ChangeSettingsCommand(this._brokerFlags);
               sending.setClientId(this._id);
               this._io.send(sending, this._channel);
            }

            if (cmd.getClientId() == -1L) {
               cmd.setClientId(this._id);
            }

            this._io.send(cmd, this._channel);
         } catch (RuntimeException var14) {
            throw var14;
         } catch (Exception var15) {
            throw (new GeneralException(_loc.get("cant-send", sending), var15)).setFatal(true);
         } finally {
            --this._openCount;
            if (this._openCount == 0) {
               this.closeChannelInternal();
            }

         }
      } finally {
         this._broker.unlock();
      }

   }

   public void beforeFlush(TransactionEvent event) {
   }

   public void afterFlush(TransactionEvent event) {
      if (!this._listeners.isEmpty() && this._uploaded != null) {
         try {
            this.send(new AfterFlushCommand(this._uploaded, this._listeners, this._log));
         } catch (Exception var3) {
            if (this._log.isWarnEnabled()) {
               this._log.warn(_loc.get("bad-after-flush"), var3);
            }
         }

         this._uploaded = null;
      }
   }
}
