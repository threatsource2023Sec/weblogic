package org.apache.openjpa.slice.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.ConnectionInfo;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.kernel.JDBCStoreManager;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.ResultSetResult;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.kernel.QueryLanguages;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.kernel.exps.ExpressionParser;
import org.apache.openjpa.lib.rop.MergedResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.slice.DistributionPolicy;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UserException;

class DistributedStoreManager extends JDBCStoreManager {
   private final List _slices;
   private JDBCStoreManager _master;
   private final DistributedJDBCConfiguration _conf;
   private static final Localizer _loc = Localizer.forPackage(DistributedStoreManager.class);
   private static ExecutorService threadPool = Executors.newCachedThreadPool();

   public DistributedStoreManager(DistributedJDBCConfiguration conf) {
      this._conf = conf;
      this._slices = new ArrayList();
      List sliceNames = conf.getActiveSliceNames();
      Iterator i$ = sliceNames.iterator();

      while(i$.hasNext()) {
         String name = (String)i$.next();
         SliceStoreManager slice = new SliceStoreManager(conf.getSlice(name));
         this._slices.add(slice);
         if (name.equals(conf.getMaster().getName())) {
            this._master = slice;
         }
      }

   }

   public DistributedJDBCConfiguration getConfiguration() {
      return this._conf;
   }

   public SliceStoreManager getSlice(int i) {
      return (SliceStoreManager)this._slices.get(i);
   }

   protected String findSliceName(OpenJPAStateManager sm, Object info) {
      boolean hasIndex = this.hasSlice(sm);
      if (hasIndex) {
         return sm.getImplData().toString();
      } else {
         String slice = this.estimateSlice(sm, info);
         return slice == null ? this.assignSlice(sm) : slice;
      }
   }

   private boolean hasSlice(OpenJPAStateManager sm) {
      return sm.getImplData() != null;
   }

   private String assignSlice(OpenJPAStateManager sm) {
      Object pc = sm.getPersistenceCapable();
      DistributionPolicy policy = this._conf.getDistributionPolicyInstance();
      List sliceNames = this._conf.getActiveSliceNames();
      String slice = policy.distribute(pc, sliceNames, this.getContext());
      if (!sliceNames.contains(slice)) {
         throw new UserException(_loc.get("bad-policy-slice", new Object[]{policy.getClass().getName(), slice, pc, sliceNames}));
      } else {
         sm.setImplData(slice, true);
         return slice;
      }
   }

   private String estimateSlice(OpenJPAStateManager sm, Object edata) {
      if (edata != null && edata instanceof ConnectionInfo) {
         Result result = ((ConnectionInfo)edata).result;
         if (result instanceof ResultSetResult) {
            JDBCStore store = ((ResultSetResult)result).getStore();
            Iterator i$ = this._slices.iterator();

            while(i$.hasNext()) {
               SliceStoreManager slice = (SliceStoreManager)i$.next();
               if (slice == store) {
                  String sliceId = slice.getName();
                  sm.setImplData(sliceId, true);
                  return sliceId;
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private StoreManager selectStore(OpenJPAStateManager sm, Object edata) {
      String name = this.findSliceName(sm, edata);
      SliceStoreManager slice = this.lookup(name);
      if (slice == null) {
         throw new InternalException(_loc.get("wrong-slice", name, sm));
      } else {
         return slice;
      }
   }

   public boolean assignField(OpenJPAStateManager sm, int field, boolean preFlush) {
      return this.selectStore(sm, (Object)null).assignField(sm, field, preFlush);
   }

   public boolean assignObjectId(OpenJPAStateManager sm, boolean preFlush) {
      return this._master.assignObjectId(sm, preFlush);
   }

   public void beforeStateChange(OpenJPAStateManager sm, PCState fromState, PCState toState) {
      this._master.beforeStateChange(sm, fromState, toState);
   }

   public void beginOptimistic() {
      Iterator i$ = this._slices.iterator();

      while(i$.hasNext()) {
         SliceStoreManager slice = (SliceStoreManager)i$.next();
         slice.beginOptimistic();
      }

   }

   public boolean cancelAll() {
      boolean ret = true;

      SliceStoreManager slice;
      for(Iterator i$ = this._slices.iterator(); i$.hasNext(); ret &= slice.cancelAll()) {
         slice = (SliceStoreManager)i$.next();
      }

      return ret;
   }

   public int compareVersion(OpenJPAStateManager sm, Object v1, Object v2) {
      return this.selectStore(sm, (Object)null).compareVersion(sm, v1, v2);
   }

   public Object copyDataStoreId(Object oid, ClassMetaData meta) {
      return this._master.copyDataStoreId(oid, meta);
   }

   public ResultObjectProvider executeExtent(ClassMetaData meta, boolean subclasses, FetchConfiguration fetch) {
      int i = 0;
      List targets = this.getTargets(fetch);
      ResultObjectProvider[] tmp = new ResultObjectProvider[targets.size()];

      SliceStoreManager slice;
      for(Iterator i$ = targets.iterator(); i$.hasNext(); tmp[i++] = slice.executeExtent(meta, subclasses, fetch)) {
         slice = (SliceStoreManager)i$.next();
      }

      return new MergedResultObjectProvider(tmp);
   }

   public boolean exists(OpenJPAStateManager sm, Object edata) {
      Iterator i$ = this._slices.iterator();

      SliceStoreManager slice;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         slice = (SliceStoreManager)i$.next();
      } while(!slice.exists(sm, edata));

      sm.setImplData(slice.getName(), true);
      return true;
   }

   public Collection flush(Collection sms) {
      Collection exceptions = new ArrayList();
      List futures = new ArrayList();
      Map subsets = this.bin(sms, (Object)null);
      Iterator i$ = this._slices.iterator();

      while(i$.hasNext()) {
         SliceStoreManager slice = (SliceStoreManager)i$.next();
         List subset = (List)subsets.get(slice.getName());
         if (!subset.isEmpty()) {
            futures.add(threadPool.submit(new Flusher(slice, subset)));
         }
      }

      i$ = futures.iterator();

      while(i$.hasNext()) {
         Future future = (Future)i$.next();

         try {
            Collection error = (Collection)future.get();
            if (error != null && !error.isEmpty()) {
               exceptions.addAll(error);
            }
         } catch (InterruptedException var9) {
            throw new StoreException(var9);
         } catch (ExecutionException var10) {
            throw new StoreException(var10.getCause());
         }
      }

      return exceptions;
   }

   private Map bin(Collection sms, Object edata) {
      Map subsets = new HashMap();
      Iterator i$ = this._slices.iterator();

      while(i$.hasNext()) {
         SliceStoreManager slice = (SliceStoreManager)i$.next();
         subsets.put(slice.getName(), new ArrayList());
      }

      i$ = sms.iterator();

      while(i$.hasNext()) {
         Object x = i$.next();
         OpenJPAStateManager sm = (OpenJPAStateManager)x;
         String slice = this.findSliceName(sm, edata);
         ((List)subsets.get(slice)).add(sm);
      }

      return subsets;
   }

   public Object getClientConnection() {
      throw new UnsupportedOperationException();
   }

   public Seq getDataStoreIdSequence(ClassMetaData forClass) {
      return this._master.getDataStoreIdSequence(forClass);
   }

   public Class getDataStoreIdType(ClassMetaData meta) {
      return this._master.getDataStoreIdType(meta);
   }

   public Class getManagedType(Object oid) {
      return this._master.getManagedType(oid);
   }

   public Seq getValueSequence(FieldMetaData forField) {
      return this._master.getValueSequence(forField);
   }

   public boolean initialize(OpenJPAStateManager sm, PCState state, FetchConfiguration fetch, Object edata) {
      if (edata instanceof ConnectionInfo) {
         String slice = this.findSliceName(sm, (ConnectionInfo)edata);
         if (slice != null) {
            return this.lookup(slice).initialize(sm, state, fetch, edata);
         }
      }

      List targets = this.getTargets(fetch);
      Iterator i$ = targets.iterator();

      SliceStoreManager slice;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         slice = (SliceStoreManager)i$.next();
      } while(!slice.initialize(sm, state, fetch, edata));

      sm.setImplData(slice.getName(), true);
      return true;
   }

   public boolean load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch, int lockLevel, Object edata) {
      return this.selectStore(sm, edata).load(sm, fields, fetch, lockLevel, edata);
   }

   public Collection loadAll(Collection sms, PCState state, int load, FetchConfiguration fetch, Object edata) {
      Map subsets = this.bin(sms, edata);
      Collection result = new ArrayList();
      Iterator i$ = this._slices.iterator();

      while(i$.hasNext()) {
         SliceStoreManager slice = (SliceStoreManager)i$.next();
         List subset = (List)subsets.get(slice.getName());
         if (!subset.isEmpty()) {
            Collection tmp = slice.loadAll(subset, state, load, fetch, edata);
            if (tmp != null && !tmp.isEmpty()) {
               result.addAll(tmp);
            }
         }
      }

      return result;
   }

   public Object newDataStoreId(Object oidVal, ClassMetaData meta) {
      return this._master.newDataStoreId(oidVal, meta);
   }

   public FetchConfiguration newFetchConfiguration() {
      return this._master.newFetchConfiguration();
   }

   public StoreQuery newQuery(String language) {
      ExpressionParser parser = QueryLanguages.parserForLanguage(language);
      DistributedStoreQuery ret = new DistributedStoreQuery(this, parser);
      Iterator i$ = this._slices.iterator();

      while(i$.hasNext()) {
         SliceStoreManager slice = (SliceStoreManager)i$.next();
         ret.add(slice.newQuery(language));
      }

      return ret;
   }

   public void setContext(StoreContext ctx) {
      super.setContext(ctx);
      Iterator i$ = this._slices.iterator();

      while(i$.hasNext()) {
         SliceStoreManager store = (SliceStoreManager)i$.next();
         store.setContext(ctx, (JDBCConfiguration)store.getSlice().getConfiguration());
      }

   }

   private SliceStoreManager lookup(String name) {
      Iterator i$ = this._slices.iterator();

      SliceStoreManager slice;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         slice = (SliceStoreManager)i$.next();
      } while(!slice.getName().equals(name));

      return slice;
   }

   public boolean syncVersion(OpenJPAStateManager sm, Object edata) {
      return this.selectStore(sm, edata).syncVersion(sm, edata);
   }

   protected JDBCStoreManager.RefCountConnection connectInternal() throws SQLException {
      List list = new ArrayList();
      Iterator i$ = this._slices.iterator();

      while(i$.hasNext()) {
         SliceStoreManager slice = (SliceStoreManager)i$.next();
         list.add(slice.getConnection());
      }

      DistributedConnection con = new DistributedConnection(list);
      return new JDBCStoreManager.RefCountConnection(con);
   }

   List getTargets(FetchConfiguration fetch) {
      if (fetch == null) {
         return this._slices;
      } else {
         Object hint = fetch.getHint("openjpa.hint.slice.Target");
         if (hint != null && hint instanceof String) {
            List targetNames = Arrays.asList(hint.toString().split("\\,"));
            List targets = new ArrayList();
            Iterator i$ = this._slices.iterator();

            while(i$.hasNext()) {
               SliceStoreManager slice = (SliceStoreManager)i$.next();
               if (targetNames.contains(slice.getName())) {
                  targets.add(slice);
               }
            }

            if (targets.isEmpty()) {
               return this._slices;
            } else {
               return targets;
            }
         } else {
            return this._slices;
         }
      }
   }

   void log(String s) {
      System.out.println("[" + Thread.currentThread().getName() + "] " + this + s);
   }

   private static class Flusher implements Callable {
      final SliceStoreManager store;
      final Collection toFlush;

      Flusher(SliceStoreManager store, Collection toFlush) {
         this.store = store;
         this.toFlush = toFlush;
      }

      public Collection call() throws Exception {
         return this.store.flush(this.toFlush);
      }
   }
}
