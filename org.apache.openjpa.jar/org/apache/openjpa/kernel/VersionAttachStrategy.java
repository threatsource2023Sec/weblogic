package org.apache.openjpa.kernel;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.ObjectNotFoundException;
import org.apache.openjpa.util.OptimisticException;

class VersionAttachStrategy extends AttachStrategy implements DetachState {
   private static final Localizer _loc = Localizer.forPackage(VersionAttachStrategy.class);

   protected Object getDetachedObjectId(AttachManager manager, Object toAttach) {
      Broker broker = manager.getBroker();
      ClassMetaData meta = broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData(ImplHelper.getManagedInstance(toAttach).getClass(), broker.getClassLoader(), true);
      return ApplicationIds.create(ImplHelper.toPersistenceCapable(toAttach, broker.getConfiguration()), meta);
   }

   protected void provideField(Object toAttach, StateManagerImpl sm, int field) {
      sm.provideField(ImplHelper.toPersistenceCapable(toAttach, sm.getContext().getConfiguration()), this, field);
   }

   public Object attach(AttachManager manager, Object toAttach, ClassMetaData meta, PersistenceCapable into, OpenJPAStateManager owner, ValueMetaData ownerMeta, boolean explicit) {
      BrokerImpl broker = manager.getBroker();
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(toAttach, meta.getRepository().getConfiguration());
      boolean embedded = ownerMeta != null && ownerMeta.isEmbeddedPC();
      boolean isNew = !broker.isDetached(pc);
      Object version = null;
      StateManagerImpl sm;
      if (!embedded || !isNew && into != null && broker.getStateManager(into) != null) {
         Object id;
         if (isNew) {
            id = null;
            if (!this.isPrimaryKeysGenerated(meta)) {
               id = ApplicationIds.create(pc, meta);
            }

            sm = this.persist(manager, pc, meta, id, explicit);
            into = sm.getPersistenceCapable();
         } else if (!embedded && into == null) {
            id = this.getDetachedObjectId(manager, toAttach);
            if (id != null) {
               into = ImplHelper.toPersistenceCapable(broker.find(id, true, (FindCallbacks)null), broker.getConfiguration());
            }

            if (into == null) {
               throw (new OptimisticException(_loc.get("attach-version-del", ImplHelper.getManagedInstance(pc).getClass(), id, version))).setFailedObject(toAttach);
            }

            sm = manager.assertManaged(into);
            if (meta.getDescribedType() != sm.getMetaData().getDescribedType()) {
               throw (new ObjectNotFoundException(_loc.get("attach-wrongclass", id, toAttach.getClass(), sm.getMetaData().getDescribedType()))).setFailedObject(toAttach);
            }
         } else {
            sm = manager.assertManaged(into);
         }
      } else {
         if (into == null) {
            into = pc.pcNewInstance((StateManager)null, false);
         }

         sm = (StateManagerImpl)broker.embed(into, (Object)null, owner, ownerMeta);
         into = sm.getPersistenceCapable();
      }

      manager.setAttachedCopy(toAttach, into);
      if (pc == into) {
         this.attachFieldsInPlace(manager, sm);
         return into;
      } else {
         if (isNew) {
            broker.fireLifecycleEvent(toAttach, (Object)null, meta, 0);
         } else {
            manager.fireBeforeAttach(toAttach, meta);
         }

         StateManager smBefore = pc.pcGetStateManager();
         pc.pcReplaceStateManager(sm);
         int detach = isNew ? 2 : broker.getDetachState();
         FetchConfiguration fetch = broker.getFetchConfiguration();

         try {
            FieldMetaData[] fmds = meta.getFields();

            for(int i = 0; i < fmds.length; ++i) {
               switch (detach) {
                  case 0:
                     if (fetch.requiresFetch(fmds[i]) != 0) {
                        this.attachField(manager, toAttach, sm, fmds[i], true);
                     }
                     break;
                  case 1:
                     this.attachField(manager, toAttach, sm, fmds[i], false);
                     break;
                  case 2:
                     this.attachField(manager, toAttach, sm, fmds[i], true);
               }
            }
         } finally {
            pc.pcReplaceStateManager(smBefore);
         }

         if (!embedded && !isNew) {
            this.compareVersion(sm, pc);
         }

         return ImplHelper.getManagedInstance(into);
      }
   }

   private void compareVersion(StateManagerImpl sm, PersistenceCapable pc) {
      Object version = pc.pcGetVersion();
      if (version != null) {
         StoreManager store = sm.getBroker().getStoreManager();
         switch (store.compareVersion(sm, version, sm.getVersion())) {
            case 1:
               sm.setVersion(version);
            case 3:
            default:
               return;
            case 2:
            case 4:
               sm.setVersion(version);
               throw new OptimisticException(sm.getManagedInstance());
         }
      }
   }

   private void attachFieldsInPlace(AttachManager manager, StateManagerImpl sm) {
      FieldMetaData[] fmds = sm.getMetaData().getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].getManagement() == 3) {
            Object cur;
            Object attached;
            switch (fmds[i].getDeclaredTypeCode()) {
               case 11:
                  if (!fmds[i].getElement().isDeclaredTypePC()) {
                     continue;
                  }

                  cur = sm.fetchObjectField(i);
                  attached = this.attachInPlace(manager, sm, fmds[i], (Object[])((Object[])cur));
                  break;
               case 12:
                  if (!fmds[i].getElement().isDeclaredTypePC()) {
                     continue;
                  }

                  cur = sm.fetchObjectField(i);
                  attached = this.attachInPlace(manager, sm, fmds[i], (Collection)cur);
                  break;
               case 13:
                  if (!fmds[i].getElement().isDeclaredTypePC() && !fmds[i].getKey().isDeclaredTypePC()) {
                     continue;
                  }

                  cur = sm.fetchObjectField(i);
                  attached = this.attachInPlace(manager, sm, fmds[i], (Map)cur);
                  break;
               case 15:
               case 27:
                  cur = sm.fetchObjectField(i);
                  attached = this.attachInPlace(manager, sm, (ValueMetaData)fmds[i], (Object)cur);
                  break;
               default:
                  continue;
            }

            if (cur != attached) {
               sm.settingObjectField(sm.getPersistenceCapable(), i, cur, attached, 1);
            }
         }
      }

   }

   private Object attachInPlace(AttachManager manager, StateManagerImpl sm, ValueMetaData vmd, Object pc) {
      if (pc == null) {
         return null;
      } else {
         Object attached = manager.getAttachedCopy(pc);
         if (attached != null) {
            return attached;
         } else {
            OpenJPAStateManager into = manager.getBroker().getStateManager(pc);
            PersistenceCapable intoPC = into == null ? null : into.getPersistenceCapable();
            return vmd.isEmbedded() ? manager.attach(pc, intoPC, sm, vmd, false) : manager.attach(pc, intoPC, (OpenJPAStateManager)null, (ValueMetaData)null, false);
         }
      }
   }

   private Object[] attachInPlace(AttachManager manager, StateManagerImpl sm, FieldMetaData fmd, Object[] arr) {
      if (arr == null) {
         return null;
      } else {
         for(int i = 0; i < arr.length; ++i) {
            arr[i] = this.attachInPlace(manager, sm, fmd.getElement(), arr[i]);
         }

         return arr;
      }
   }

   private Collection attachInPlace(AttachManager manager, StateManagerImpl sm, FieldMetaData fmd, Collection coll) {
      if (coll != null && !coll.isEmpty()) {
         Collection copy = null;
         if (fmd.getElement().isEmbedded()) {
            copy = (Collection)sm.newFieldProxy(fmd.getIndex());
         } else {
            Iterator itr = coll.iterator();

            while(itr.hasNext()) {
               if (manager.getBroker().isDetached(itr.next())) {
                  copy = (Collection)sm.newFieldProxy(fmd.getIndex());
                  break;
               }
            }
         }

         Iterator itr = coll.iterator();

         while(itr.hasNext()) {
            Object attached = this.attachInPlace(manager, sm, fmd.getElement(), itr.next());
            if (copy != null) {
               copy.add(attached);
            }
         }

         return copy == null ? coll : copy;
      } else {
         return coll;
      }
   }

   private Map attachInPlace(AttachManager manager, StateManagerImpl sm, FieldMetaData fmd, Map map) {
      if (map != null && !map.isEmpty()) {
         Map copy = null;
         boolean keyPC = fmd.getKey().isDeclaredTypePC();
         boolean valPC = fmd.getElement().isDeclaredTypePC();
         Map.Entry entry;
         if (!fmd.getKey().isEmbeddedPC() && !fmd.getElement().isEmbeddedPC()) {
            label62: {
               Iterator itr = map.entrySet().iterator();

               do {
                  if (!itr.hasNext()) {
                     break label62;
                  }

                  entry = (Map.Entry)itr.next();
               } while((!keyPC || !manager.getBroker().isDetached(entry.getKey())) && (!valPC || !manager.getBroker().isDetached(entry.getValue())));

               copy = (Map)sm.newFieldProxy(fmd.getIndex());
            }
         } else {
            copy = (Map)sm.newFieldProxy(fmd.getIndex());
         }

         Iterator itr = map.entrySet().iterator();

         while(itr.hasNext()) {
            entry = (Map.Entry)itr.next();
            Object key = entry.getKey();
            if (keyPC) {
               key = this.attachInPlace(manager, sm, fmd.getKey(), key);
            }

            Object val = entry.getValue();
            if (valPC) {
               val = this.attachInPlace(manager, sm, fmd.getElement(), val);
            }

            if (copy != null) {
               copy.put(key, val);
            }
         }

         return copy == null ? map : copy;
      } else {
         return map;
      }
   }

   protected PersistenceCapable findFromDatabase(AttachManager manager, Object pc) {
      Object oid = manager.getBroker().newObjectId(pc.getClass(), manager.getDetachedObjectId(pc));
      return oid != null ? ImplHelper.toPersistenceCapable(manager.getBroker().find(oid, true, (FindCallbacks)null), manager.getBroker().getConfiguration()) : null;
   }

   private boolean isPrimaryKeysGenerated(ClassMetaData meta) {
      FieldMetaData[] pks = meta.getPrimaryKeyFields();

      for(int i = 0; i < pks.length; ++i) {
         if (pks[i].getValueStrategy() != 0) {
            return true;
         }
      }

      return false;
   }
}
