package org.apache.openjpa.kernel;

import java.io.IOException;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.IdentityMap;
import org.apache.openjpa.conf.DetachOptions;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.CallbackException;
import org.apache.openjpa.util.ObjectNotFoundException;
import org.apache.openjpa.util.Proxy;
import org.apache.openjpa.util.ProxyManager;
import org.apache.openjpa.util.UserException;

public class DetachManager implements DetachState {
   private static Localizer _loc = Localizer.forPackage(DetachManager.class);
   private final BrokerImpl _broker;
   private final boolean _copy;
   private final boolean _full;
   private final ProxyManager _proxy;
   private final DetachOptions _opts;
   private final OpCallbacks _call;
   private final boolean _failFast;
   private boolean _flushed = false;
   private boolean _flushBeforeDetach;
   private final IdentityMap _detached;
   private final DetachFieldManager _fullFM;

   static boolean preSerialize(StateManagerImpl sm) {
      if (!sm.isPersistent()) {
         return false;
      } else {
         if (sm.getBroker().getConfiguration().getCompatibilityInstance().getFlushBeforeDetach()) {
            flushDirty(sm);
         }

         ClassMetaData meta = sm.getMetaData();
         boolean setState = meta.getDetachedState() != null && !"`syn".equals(meta.getDetachedState());
         BitSet idxs = setState ? new BitSet(meta.getFields().length) : null;
         preDetach(sm.getBroker(), sm, idxs);
         if (setState) {
            sm.getPersistenceCapable().pcSetDetachedState(getDetachedState(sm, idxs));
            return false;
         } else {
            return true;
         }
      }
   }

   static boolean writeDetachedState(StateManagerImpl sm, ObjectOutput out, BitSet idxs) throws IOException {
      if (!sm.isPersistent()) {
         out.writeObject((Object)null);
         out.writeObject((Object)null);
         return false;
      } else {
         flushDirty(sm);
         Broker broker = sm.getBroker();
         preDetach(broker, sm, idxs);
         DetachOptions opts = broker.getConfiguration().getDetachStateInstance();
         if (opts.getDetachedStateManager() && useDetachedStateManager(sm, opts)) {
            out.writeObject((Object)null);
            out.writeObject(new DetachedStateManager(sm.getPersistenceCapable(), sm, idxs, opts.getAccessUnloaded(), broker.getMultithreaded()));
            return true;
         } else {
            out.writeObject(getDetachedState(sm, idxs));
            out.writeObject((Object)null);
            return false;
         }
      }
   }

   private static void preDetach(Broker broker, StateManagerImpl sm, BitSet idxs) {
      int detachMode = broker.getDetachState();
      int loadMode = 0;
      BitSet exclude = null;
      if (detachMode == 1) {
         exclude = StoreContext.EXCLUDE_ALL;
      } else if (detachMode == 2) {
         loadMode = 1;
      }

      try {
         sm.load(broker.getFetchConfiguration(), loadMode, exclude, (Object)null, false);
      } catch (ObjectNotFoundException var8) {
      }

      if (idxs != null) {
         if (detachMode == 0) {
            setFetchGroupFields(broker, sm, idxs);
         } else {
            idxs.or(sm.getLoaded());
         }

         FieldMetaData[] fmds = sm.getMetaData().getFields();

         for(int i = 0; i < fmds.length; ++i) {
            if (fmds[i].isLRS()) {
               idxs.clear(i);
            }
         }
      }

   }

   private static Object getDetachedState(StateManagerImpl sm, BitSet fields) {
      int offset = sm.getMetaData().getIdentityType() == 1 ? 1 : 0;
      Object[] state;
      if (sm.isNew()) {
         state = new Object[3 + offset];
      } else {
         state = new Object[2 + offset];
      }

      if (offset > 0) {
         Object id;
         if (!sm.isEmbedded() && sm.getObjectId() != null) {
            id = sm.getObjectId();
         } else {
            id = sm.getId();
         }

         state[0] = id.toString();
      }

      state[offset] = sm.getVersion();
      state[offset + 1] = fields;
      return state;
   }

   private static boolean flushDirty(StateManagerImpl sm) {
      if (sm.isDirty() && sm.getBroker().isActive()) {
         BitSet dirtyFields = sm.getDirty();
         BitSet flushedFields = sm.getFlushed();

         for(int i = 0; i < dirtyFields.size(); ++i) {
            if (dirtyFields.get(i) && !flushedFields.get(i)) {
               if (sm.getBroker().getRollbackOnly()) {
                  sm.getBroker().preFlush();
               } else {
                  sm.getBroker().flush();
               }

               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static void setFetchGroupFields(Broker broker, StateManagerImpl sm, BitSet idxs) {
      FetchConfiguration fetch = broker.getFetchConfiguration();
      FieldMetaData[] fmds = sm.getMetaData().getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].isPrimaryKey() || fetch.requiresFetch(fmds[i]) != 0) {
            idxs.set(i);
         }
      }

   }

   public DetachManager(BrokerImpl broker, boolean full, OpCallbacks call) {
      this._broker = broker;
      this._proxy = broker.getConfiguration().getProxyManagerInstance();
      this._opts = broker.getConfiguration().getDetachStateInstance();
      this._copy = !full;
      this._flushed = full;
      this._call = call;
      this._failFast = (broker.getConfiguration().getMetaDataRepositoryInstance().getMetaDataFactory().getDefaults().getCallbackMode() & 2) != 0;
      this._full = full && broker.getDetachState() == 1;
      if (this._full) {
         this._detached = null;
         this._fullFM = new DetachFieldManager();
      } else {
         this._detached = new IdentityMap();
         this._fullFM = null;
      }

      this._flushBeforeDetach = broker.getConfiguration().getCompatibilityInstance().getFlushBeforeDetach();
   }

   public Object detach(Object toDetach) {
      List exceps = null;

      Object var4;
      try {
         Object var3 = this.detachInternal(toDetach);
         return var3;
      } catch (CallbackException var9) {
         exceps = new ArrayList(1);
         ((List)exceps).add(var9);
         var4 = null;
      } finally {
         if (exceps == null || !this._failFast) {
            exceps = this.invokeAfterDetach(Collections.singleton(toDetach), (List)exceps);
         }

         if (this._detached != null) {
            this._detached.clear();
         }

         this.throwExceptions((List)exceps);
      }

      return var4;
   }

   public Object[] detachAll(Collection instances) {
      List exceps = null;
      List detached = null;
      if (this._copy) {
         detached = new ArrayList(instances.size());
      }

      boolean failFast = false;

      try {
         Iterator itr = instances.iterator();

         while(itr.hasNext()) {
            Object detach = this.detachInternal(itr.next());
            if (this._copy) {
               detached.add(detach);
            }
         }
      } catch (RuntimeException var11) {
         if (var11 instanceof CallbackException && this._failFast) {
            failFast = true;
         }

         exceps = this.add(exceps, var11);
      } finally {
         if (!failFast) {
            exceps = this.invokeAfterDetach(instances, exceps);
         }

         if (this._detached != null) {
            this._detached.clear();
         }

      }

      this.throwExceptions(exceps);
      return this._copy ? detached.toArray() : null;
   }

   private List invokeAfterDetach(Collection objs, List exceps) {
      Iterator itr = this._full ? objs.iterator() : this._detached.entrySet().iterator();

      while(itr.hasNext()) {
         Object orig;
         Object detached;
         if (this._full) {
            orig = itr.next();
            detached = orig;
         } else {
            Map.Entry entry = (Map.Entry)itr.next();
            orig = entry.getKey();
            detached = entry.getValue();
         }

         StateManagerImpl sm = this._broker.getStateManagerImpl(orig, true);

         try {
            if (sm != null) {
               this._broker.fireLifecycleEvent(detached, orig, sm.getMetaData(), 14);
            }
         } catch (CallbackException var9) {
            exceps = this.add(exceps, var9);
            if (this._failFast) {
               break;
            }
         }
      }

      return exceps;
   }

   private List add(List exceps, RuntimeException re) {
      if (exceps == null) {
         exceps = new LinkedList();
      }

      ((List)exceps).add(re);
      return (List)exceps;
   }

   private void throwExceptions(List exceps) {
      if (exceps != null) {
         if (exceps.size() == 1) {
            throw (RuntimeException)exceps.get(0);
         } else {
            throw (new UserException(_loc.get("nested-exceps"))).setNestedThrowables((Throwable[])((Throwable[])exceps.toArray(new Throwable[exceps.size()])));
         }
      }
   }

   private Object detachInternal(Object toDetach) {
      if (toDetach == null) {
         return null;
      } else {
         if (this._detached != null) {
            Object detached = this._detached.get(toDetach);
            if (detached != null) {
               return detached;
            }
         }

         StateManagerImpl sm = this._broker.getStateManagerImpl(toDetach, true);
         if (this._call != null && (this._call.processArgument(7, toDetach, sm) & 4) == 0) {
            return toDetach;
         } else if (sm == null) {
            return toDetach;
         } else {
            this._broker.fireLifecycleEvent(toDetach, (Object)null, sm.getMetaData(), 13);
            if (!this._flushed) {
               if (this._flushBeforeDetach) {
                  flushDirty(sm);
               }

               this._flushed = true;
            }

            BitSet fields = new BitSet();
            preDetach(this._broker, sm, fields);
            PersistenceCapable pc = sm.getPersistenceCapable();
            PersistenceCapable detachedPC;
            if (this._copy) {
               detachedPC = pc.pcNewInstance((StateManager)null, true);
            } else {
               detachedPC = pc;
            }

            if (this._detached != null) {
               this._detached.put(toDetach, detachedPC);
            }

            DetachedStateManager detSM = null;
            if (this._opts.getDetachedStateManager() && useDetachedStateManager(sm, this._opts) && (!sm.isNew() || sm.isDeleted() || sm.isFlushed())) {
               detSM = new DetachedStateManager(detachedPC, sm, fields, this._opts.getAccessUnloaded(), this._broker.getMultithreaded());
            }

            if (this._full) {
               this._fullFM.setStateManager(sm);
               this._fullFM.detachVersion();
               this._fullFM.reproxy(detSM);
               this._fullFM.setStateManager((StateManagerImpl)null);
            } else {
               InstanceDetachFieldManager fm = new InstanceDetachFieldManager(detachedPC, detSM);
               fm.setStateManager(sm);
               fm.detachFields(fields);
            }

            if (!Boolean.FALSE.equals(sm.getMetaData().usesDetachedState())) {
               detachedPC.pcSetDetachedState(getDetachedState(sm, fields));
            }

            if (!this._copy) {
               sm.release(false, !this._copy);
            }

            if (detSM != null) {
               detachedPC.pcReplaceStateManager(detSM);
            }

            return detachedPC;
         }
      }
   }

   private static boolean useDetachedStateManager(StateManagerImpl sm, DetachOptions opts) {
      ClassMetaData meta = sm.getMetaData();
      return !Boolean.FALSE.equals(meta.usesDetachedState()) && "`syn".equals(meta.getDetachedState()) && opts.getDetachedStateManager();
   }

   private class InstanceDetachFieldManager extends DetachFieldManager {
      private final PersistenceCapable _to;
      private final DetachedStateManager _detSM;

      public InstanceDetachFieldManager(PersistenceCapable to, DetachedStateManager detSM) {
         super(null);
         this._to = to;
         this._detSM = detSM;
      }

      protected PersistenceCapable getDetachedPersistenceCapable() {
         return this._to;
      }

      public void detachFields(BitSet fgfields) {
         PersistenceCapable from = this.sm.getPersistenceCapable();
         FieldMetaData[] pks = this.sm.getMetaData().getPrimaryKeyFields();
         FieldMetaData[] fmds = this.sm.getMetaData().getFields();
         if (DetachManager.this._copy) {
            this._to.pcReplaceStateManager(this.sm);
         }

         try {
            int i;
            for(i = 0; i < pks.length; ++i) {
               this.detachField(from, pks[i].getIndex(), true);
            }

            this.detachVersion();

            for(i = 0; i < fmds.length; ++i) {
               if (!fmds[i].isPrimaryKey() && !fmds[i].isVersion()) {
                  this.detachField(from, i, fgfields.get(i));
               }
            }
         } finally {
            if (DetachManager.this._copy) {
               this._to.pcReplaceStateManager((StateManager)null);
            }

         }

      }

      private void detachField(PersistenceCapable from, int i, boolean fg) {
         if (fg) {
            this.sm.provideField(from, this, i);
         } else if (!DetachManager.this._copy) {
            this.clear();
            this.sm.replaceField(this._to, this, i);
         }

      }

      public void storeBooleanField(int field, boolean curVal) {
         super.storeBooleanField(field, curVal);
         this.sm.replaceField(this._to, this, field);
      }

      public void storeByteField(int field, byte curVal) {
         super.storeByteField(field, curVal);
         this.sm.replaceField(this._to, this, field);
      }

      public void storeCharField(int field, char curVal) {
         super.storeCharField(field, curVal);
         this.sm.replaceField(this._to, this, field);
      }

      public void storeDoubleField(int field, double curVal) {
         super.storeDoubleField(field, curVal);
         this.sm.replaceField(this._to, this, field);
      }

      public void storeFloatField(int field, float curVal) {
         super.storeFloatField(field, curVal);
         this.sm.replaceField(this._to, this, field);
      }

      public void storeIntField(int field, int curVal) {
         super.storeIntField(field, curVal);
         this.sm.replaceField(this._to, this, field);
      }

      public void storeLongField(int field, long curVal) {
         super.storeLongField(field, curVal);
         this.sm.replaceField(this._to, this, field);
      }

      public void storeShortField(int field, short curVal) {
         super.storeShortField(field, curVal);
         this.sm.replaceField(this._to, this, field);
      }

      public void storeStringField(int field, String curVal) {
         super.storeStringField(field, curVal);
         this.sm.replaceField(this._to, this, field);
      }

      public void storeObjectField(int field, Object curVal) {
         super.storeObjectField(field, this.detachField(curVal, field));
         this.sm.replaceField(this._to, this, field);
      }

      private Object reproxy(Object obj, int field) {
         if (obj != null && this._detSM != null && obj instanceof Proxy) {
            ((Proxy)obj).setOwner(this._detSM, field);
         }

         return obj;
      }

      private Object detachField(Object curVal, int field) {
         if (curVal == null) {
            return null;
         } else {
            FieldMetaData fmd = this.sm.getMetaData().getField(field);
            Object newVal = null;
            switch (fmd.getDeclaredTypeCode()) {
               case 8:
                  if (DetachManager.this._copy) {
                     newVal = DetachManager.this._proxy.copyCustom(curVal);
                  }

                  return this.reproxy(newVal == null ? curVal : newVal, field);
               case 9:
               case 10:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               case 26:
               default:
                  return curVal;
               case 11:
                  if (DetachManager.this._copy) {
                     newVal = DetachManager.this._proxy.copyArray(curVal);
                  } else {
                     newVal = curVal;
                  }

                  this.detachArray(newVal, fmd);
                  return newVal;
               case 12:
                  if (DetachManager.this._copy) {
                     if (this._detSM != null) {
                        newVal = DetachManager.this._proxy.newCollectionProxy(fmd.getProxyType(), fmd.getElement().getDeclaredType(), fmd.getInitializer() instanceof Comparator ? (Comparator)fmd.getInitializer() : null, this.sm.getBroker().getConfiguration().getCompatibilityInstance().getAutoOff());
                        ((Collection)newVal).addAll((Collection)curVal);
                     } else {
                        newVal = DetachManager.this._proxy.copyCollection((Collection)curVal);
                     }
                  } else {
                     newVal = curVal;
                  }

                  this.detachCollection((Collection)newVal, (Collection)curVal, fmd);
                  return this.reproxy(newVal, field);
               case 13:
                  if (DetachManager.this._copy) {
                     if (this._detSM != null) {
                        newVal = DetachManager.this._proxy.newMapProxy(fmd.getProxyType(), fmd.getKey().getDeclaredType(), fmd.getElement().getDeclaredType(), fmd.getInitializer() instanceof Comparator ? (Comparator)fmd.getInitializer() : null, this.sm.getBroker().getConfiguration().getCompatibilityInstance().getAutoOff());
                        ((Map)newVal).putAll((Map)curVal);
                     } else {
                        newVal = DetachManager.this._proxy.copyMap((Map)curVal);
                     }
                  } else {
                     newVal = curVal;
                  }

                  this.detachMap((Map)newVal, (Map)curVal, fmd);
                  return this.reproxy(newVal, field);
               case 14:
                  newVal = DetachManager.this._copy ? DetachManager.this._proxy.copyDate((Date)curVal) : curVal;
                  return this.reproxy(newVal, field);
               case 15:
               case 27:
                  return DetachManager.this.detachInternal(curVal);
               case 28:
                  newVal = DetachManager.this._copy ? DetachManager.this._proxy.copyCalendar((Calendar)curVal) : curVal;
                  return this.reproxy(newVal, field);
            }
         }
      }

      private void detachArray(Object array, FieldMetaData fmd) {
         if (fmd.getElement().isDeclaredTypePC()) {
            int len = Array.getLength(array);

            for(int i = 0; i < len; ++i) {
               Array.set(array, i, DetachManager.this.detachInternal(Array.get(array, i)));
            }

         }
      }

      private void detachCollection(Collection coll, Collection orig, FieldMetaData fmd) {
         if (DetachManager.this._copy && coll == null) {
            throw new UserException(DetachManager._loc.get("not-copyable", (Object)fmd));
         } else if (fmd.getElement().isDeclaredTypePC()) {
            if (DetachManager.this._copy) {
               coll.clear();
            }

            Iterator itr = orig.iterator();

            while(itr.hasNext()) {
               Object detached = DetachManager.this.detachInternal(itr.next());
               if (DetachManager.this._copy) {
                  coll.add(detached);
               }
            }

         }
      }

      private void detachMap(Map map, Map orig, FieldMetaData fmd) {
         if (DetachManager.this._copy && map == null) {
            throw new UserException(DetachManager._loc.get("not-copyable", (Object)fmd));
         } else {
            boolean keyPC = fmd.getKey().isDeclaredTypePC();
            boolean valPC = fmd.getElement().isDeclaredTypePC();
            if (keyPC || valPC) {
               Map.Entry entry;
               if (DetachManager.this._copy && !keyPC) {
                  Iterator itrx = map.entrySet().iterator();

                  while(itrx.hasNext()) {
                     entry = (Map.Entry)itrx.next();
                     entry.setValue(DetachManager.this.detachInternal(entry.getValue()));
                  }
               } else {
                  if (DetachManager.this._copy) {
                     map.clear();
                  }

                  Iterator itr = orig.entrySet().iterator();

                  while(itr.hasNext()) {
                     entry = (Map.Entry)itr.next();
                     Object key = entry.getKey();
                     if (keyPC) {
                        key = DetachManager.this.detachInternal(key);
                     }

                     Object val = entry.getValue();
                     if (valPC) {
                        val = DetachManager.this.detachInternal(val);
                     }

                     if (DetachManager.this._copy) {
                        map.put(key, val);
                     }
                  }
               }

            }
         }
      }
   }

   private static class DetachFieldManager extends TransferFieldManager {
      protected StateManagerImpl sm;

      private DetachFieldManager() {
      }

      public void setStateManager(StateManagerImpl sm) {
         this.sm = sm;
      }

      public void detachVersion() {
         FieldMetaData fmd = this.sm.getMetaData().getVersionField();
         if (fmd != null) {
            Object val = JavaTypes.convert(this.sm.getVersion(), fmd.getTypeCode());
            val = fmd.getFieldValue(val, this.sm.getBroker());
            switch (fmd.getDeclaredTypeCode()) {
               case 1:
               case 5:
               case 6:
               case 7:
                  this.longval = val == null ? 0L : ((Number)val).longValue();
                  break;
               case 2:
               default:
                  this.objval = val;
                  break;
               case 3:
               case 4:
                  this.dblval = val == null ? 0.0 : ((Number)val).doubleValue();
            }

            this.sm.replaceField(this.getDetachedPersistenceCapable(), this, fmd.getIndex());
         }
      }

      public void reproxy(DetachedStateManager dsm) {
         FieldMetaData[] fmds = this.sm.getMetaData().getFields();

         for(int i = 0; i < fmds.length; ++i) {
            switch (fmds[i].getDeclaredTypeCode()) {
               case 12:
               case 13:
                  if (fmds[i].isLRS()) {
                     this.objval = null;
                     this.sm.replaceField(this.getDetachedPersistenceCapable(), this, i);
                     break;
                  }
               case 8:
               case 14:
               case 28:
                  this.sm.provideField(this.getDetachedPersistenceCapable(), this, i);
                  if (this.objval instanceof Proxy) {
                     Proxy proxy = (Proxy)this.objval;
                     if (proxy.getChangeTracker() != null) {
                        proxy.getChangeTracker().stopTracking();
                     }

                     proxy.setOwner(dsm, dsm == null ? -1 : i);
                  }
            }
         }

         this.clear();
      }

      protected PersistenceCapable getDetachedPersistenceCapable() {
         return this.sm.getPersistenceCapable();
      }

      // $FF: synthetic method
      DetachFieldManager(Object x0) {
         this();
      }
   }
}
