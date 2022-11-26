package org.apache.openjpa.kernel;

import java.io.ObjectStreamException;
import java.io.Serializable;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.InvalidStateException;

public class PCState implements Serializable {
   public static final PCState PNEW = new PNewState();
   public static final PCState PCLEAN = new PCleanState();
   public static final PCState PDIRTY = new PDirtyState();
   public static final PCState PDELETED = new PDeletedState();
   public static final PCState PNEWDELETED = new PNewDeletedState();
   public static final PCState PNEWPROVISIONAL = new PNewProvisionalState();
   public static final PCState PNONTRANS = new PNonTransState();
   public static final PCState PNONTRANSDIRTY = new PNonTransDirtyState();
   public static final PCState PNONTRANSNEW = new PNonTransNewState();
   public static final PCState PNONTRANSDELETED = new PNonTransDeletedState();
   public static final PCState HOLLOW = new HollowState();
   public static final PCState TRANSIENT = new TransientState();
   public static final PCState TCLEAN = new TCleanState();
   public static final PCState TDIRTY = new TDirtyState();
   public static final PCState TLOADED = new TLoadedState();
   public static final PCState ECOPY = new ECopyState();
   public static final PCState ECLEAN = new ECleanState();
   public static final PCState EDIRTY = new EDirtyState();
   public static final PCState EDELETED = new EDeletedState();
   public static final PCState ENONTRANS = new ENonTransState();
   public static final PCState PNEWFLUSHEDDELETED = new PNewFlushedDeletedState();
   public static final PCState PNEWFLUSHEDDELETEDFLUSHED = new PNewFlushedDeletedFlushedState();
   public static final PCState PDELETEDFLUSHED = new PDeletedFlushedState();
   private static Localizer _loc = Localizer.forPackage(PCState.class);

   void initialize(StateManagerImpl context) {
   }

   void beforeFlush(StateManagerImpl context, boolean logical, OpCallbacks call) {
   }

   PCState flush(StateManagerImpl context) {
      return this;
   }

   PCState commit(StateManagerImpl context) {
      return this;
   }

   PCState commitRetain(StateManagerImpl context) {
      return this;
   }

   PCState rollback(StateManagerImpl context) {
      return this;
   }

   PCState rollbackRestore(StateManagerImpl context) {
      return this;
   }

   PCState persist(StateManagerImpl context) {
      return this;
   }

   PCState delete(StateManagerImpl context) {
      return this;
   }

   PCState nonprovisional(StateManagerImpl context, boolean logical, OpCallbacks call) {
      return this;
   }

   PCState nontransactional(StateManagerImpl context) {
      return this;
   }

   PCState transactional(StateManagerImpl context) {
      return this;
   }

   PCState release(StateManagerImpl context) {
      return this;
   }

   PCState evict(StateManagerImpl context) {
      return this;
   }

   PCState afterRefresh() {
      return this;
   }

   PCState afterOptimisticRefresh() {
      return this;
   }

   PCState afterNontransactionalRefresh() {
      return this;
   }

   PCState beforeRead(StateManagerImpl context, int field) {
      return this;
   }

   PCState beforeNontransactionalRead(StateManagerImpl context, int field) {
      return this;
   }

   PCState beforeOptimisticRead(StateManagerImpl context, int field) {
      return this;
   }

   PCState beforeWrite(StateManagerImpl context, int field, boolean mutate) {
      return this;
   }

   PCState beforeOptimisticWrite(StateManagerImpl context, int field, boolean mutate) {
      return this;
   }

   PCState beforeNontransactionalWrite(StateManagerImpl context, int field, boolean mutate) {
      return this;
   }

   boolean isTransactional() {
      return false;
   }

   boolean isPersistent() {
      return false;
   }

   boolean isNew() {
      return false;
   }

   boolean isDeleted() {
      return false;
   }

   boolean isDirty() {
      return false;
   }

   boolean isPendingTransactional() {
      return false;
   }

   boolean isProvisional() {
      return false;
   }

   boolean isVersionCheckRequired(StateManagerImpl context) {
      return false;
   }

   PCState error(String key, StateManagerImpl context) {
      throw (new InvalidStateException(_loc.get(key))).setFailedObject(context.getManagedInstance());
   }

   protected Object readResolve() throws ObjectStreamException {
      if (this instanceof PNewState) {
         return PNEW;
      } else if (this instanceof PCleanState) {
         return PCLEAN;
      } else if (this instanceof PDirtyState) {
         return PDIRTY;
      } else if (this instanceof PDeletedState) {
         return PDELETED;
      } else if (this instanceof PNewDeletedState) {
         return PNEWDELETED;
      } else if (this instanceof PNewProvisionalState) {
         return PNEWPROVISIONAL;
      } else if (this instanceof PNonTransState) {
         return PNONTRANS;
      } else if (this instanceof PNonTransDirtyState) {
         return PNONTRANSDIRTY;
      } else if (this instanceof PNonTransNewState) {
         return PNONTRANSNEW;
      } else if (this instanceof PNonTransDeletedState) {
         return PNONTRANSDELETED;
      } else if (this instanceof HollowState) {
         return HOLLOW;
      } else if (this instanceof TransientState) {
         return TRANSIENT;
      } else if (this instanceof TCleanState) {
         return TCLEAN;
      } else if (this instanceof TDirtyState) {
         return TDIRTY;
      } else if (this instanceof ECopyState) {
         return ECOPY;
      } else if (this instanceof ECleanState) {
         return ECLEAN;
      } else if (this instanceof EDirtyState) {
         return EDIRTY;
      } else if (this instanceof EDeletedState) {
         return EDELETED;
      } else if (this instanceof ENonTransState) {
         return ENONTRANS;
      } else if (this instanceof PNewFlushedDeletedState) {
         return PNEWFLUSHEDDELETED;
      } else if (this instanceof PNewFlushedDeletedFlushedState) {
         return PNEWFLUSHEDDELETEDFLUSHED;
      } else if (this instanceof PDeletedFlushedState) {
         return PDELETEDFLUSHED;
      } else {
         throw new InternalException();
      }
   }
}
