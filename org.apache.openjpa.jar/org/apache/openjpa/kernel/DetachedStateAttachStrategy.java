package org.apache.openjpa.kernel;

import java.util.BitSet;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.OptimisticException;

class DetachedStateAttachStrategy extends AttachStrategy {
   private static final Localizer _loc = Localizer.forPackage(DetachedStateAttachStrategy.class);

   protected Object getDetachedObjectId(AttachManager manager, Object toAttach) {
      if (toAttach == null) {
         return null;
      } else {
         Broker broker = manager.getBroker();
         PersistenceCapable pc = ImplHelper.toPersistenceCapable(toAttach, broker.getConfiguration());
         ClassMetaData meta = broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData(ImplHelper.getManagedInstance(toAttach).getClass(), broker.getClassLoader(), true);
         switch (meta.getIdentityType()) {
            case 1:
               Object[] state = (Object[])((Object[])pc.pcGetDetachedState());
               if (state == null) {
                  return null;
               }

               return broker.newObjectId(toAttach.getClass(), state[0]);
            case 2:
               return ApplicationIds.create(pc, meta);
            default:
               throw new InternalException();
         }
      }
   }

   protected void provideField(Object toAttach, StateManagerImpl sm, int field) {
      sm.provideField(ImplHelper.toPersistenceCapable(toAttach, sm.getContext().getConfiguration()), this, field);
   }

   public Object attach(AttachManager manager, Object toAttach, ClassMetaData meta, PersistenceCapable into, OpenJPAStateManager owner, ValueMetaData ownerMeta, boolean explicit) {
      BrokerImpl broker = manager.getBroker();
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(toAttach, manager.getBroker().getConfiguration());
      Object[] state = (Object[])((Object[])pc.pcGetDetachedState());
      boolean embedded = ownerMeta != null && ownerMeta.isEmbeddedPC();
      int offset;
      StateManagerImpl sm;
      if (!embedded || state != null && into != null && broker.getStateManager(into) != null) {
         if (state == null) {
            sm = this.persist(manager, pc, meta, ApplicationIds.create(pc, meta), explicit);
            into = sm.getPersistenceCapable();
         } else if (!embedded && into == null) {
            Object id = this.getDetachedObjectId(manager, pc);
            if (id != null) {
               into = ImplHelper.toPersistenceCapable(broker.find(id, true, (FindCallbacks)null), manager.getBroker().getConfiguration());
            }

            if (into == null) {
               offset = meta.getIdentityType() == 1 ? 1 : 0;
               boolean isNew = state.length == 3 + offset;
               if (!isNew) {
                  throw (new OptimisticException(_loc.get("attach-deleted", ImplHelper.getManagedInstance(pc).getClass(), id))).setFailedObject(id);
               }

               sm = this.persist(manager, pc, meta, id, explicit);
               into = sm.getPersistenceCapable();
               state = null;
            } else {
               sm = manager.assertManaged(into);
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

      manager.setAttachedCopy(pc, into);
      meta = sm.getMetaData();
      manager.fireBeforeAttach(pc, meta);
      offset = meta.getIdentityType() == 1 ? 1 : 0;
      pc.pcReplaceStateManager(sm);
      BitSet fields = state == null ? null : (BitSet)state[1 + offset];

      try {
         FieldMetaData[] fmds = meta.getFields();

         for(int i = 0; i < fmds.length; ++i) {
            if (fields == null || fields.get(i)) {
               this.attachField(manager, pc, sm, fmds[i], true);
            }
         }
      } finally {
         pc.pcReplaceStateManager((StateManager)null);
      }

      if (state != null && !embedded) {
         if (fields != null && !fields.equals(sm.getLoaded())) {
            BitSet toLoad = (BitSet)fields.clone();
            toLoad.andNot(sm.getLoaded());
            if (toLoad.length() > 0) {
               sm.loadFields(toLoad, (FetchConfiguration)null, 0, (Object)null);
            }
         }

         Object version = state[offset];
         StoreManager store = broker.getStoreManager();
         switch (store.compareVersion(sm, version, sm.getVersion())) {
            case 1:
               sm.setVersion(version);
               break;
            case 2:
            case 4:
               sm.setVersion(version);
               throw new OptimisticException(into);
            case 3:
         }
      }

      return ImplHelper.getManagedInstance(into);
   }
}
