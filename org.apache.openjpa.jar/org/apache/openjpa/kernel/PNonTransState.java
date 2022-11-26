package org.apache.openjpa.kernel;

import java.util.BitSet;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

class PNonTransState extends PCState {
   private static final Localizer _loc = Localizer.forPackage(PNonTransState.class);

   void initialize(StateManagerImpl context) {
      context.setDirty(false);
      context.clearSavedFields();
      context.proxyFields(true, true);
   }

   PCState delete(StateManagerImpl context) {
      context.preDelete();
      return !context.getBroker().isActive() ? PNONTRANSDELETED : PDELETED;
   }

   PCState transactional(StateManagerImpl context) {
      if (!context.getBroker().getOptimistic() || context.getBroker().getAutoClear() == 1) {
         context.clearFields();
      }

      return PCLEAN;
   }

   PCState release(StateManagerImpl context) {
      return TRANSIENT;
   }

   PCState evict(StateManagerImpl context) {
      return HOLLOW;
   }

   PCState beforeRead(StateManagerImpl context, int field) {
      context.clearFields();
      return PCLEAN;
   }

   PCState beforeWrite(StateManagerImpl context, int field, boolean mutate) {
      return this.beforeWrite(context, field, mutate, false);
   }

   PCState beforeOptimisticWrite(StateManagerImpl context, int field, boolean mutate) {
      return context.getBroker().getAutoClear() == 1 ? this.beforeWrite(context, field, mutate, true) : PDIRTY;
   }

   private PCState beforeWrite(StateManagerImpl context, int field, boolean mutate, boolean optimistic) {
      if (mutate && !optimistic) {
         Log log = context.getBroker().getConfiguration().getLog("openjpa.Runtime");
         if (log.isWarnEnabled()) {
            log.warn(_loc.get("pessimistic-mutate", context.getMetaData().getField(field), context.getManagedInstance()));
         }
      } else if (!mutate) {
         if (context.getDirty().length() > 0) {
            context.saveFields(true);
         }

         context.clearFields();
         context.load((FetchConfiguration)null, 0, (BitSet)null, (Object)null, true);
      }

      return PDIRTY;
   }

   PCState beforeNontransactionalWrite(StateManagerImpl context, int field, boolean mutate) {
      return PNONTRANSDIRTY;
   }

   boolean isPersistent() {
      return true;
   }
}
