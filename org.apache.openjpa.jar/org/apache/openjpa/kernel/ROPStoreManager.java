package org.apache.openjpa.kernel;

import java.util.BitSet;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.StoreException;

class ROPStoreManager extends DelegatingStoreManager {
   public ROPStoreManager(StoreManager delegate) {
      super(delegate);
   }

   public boolean exists(OpenJPAStateManager sm, Object context) {
      if (context instanceof PCResultObjectProvider) {
         context = null;
      }

      return super.exists(sm, context);
   }

   public boolean initialize(OpenJPAStateManager sm, PCState state, FetchConfiguration fetch, Object context) {
      if (context instanceof PCResultObjectProvider) {
         try {
            ((PCResultObjectProvider)context).initialize(sm, state, fetch);
            return true;
         } catch (OpenJPAException var6) {
            throw var6;
         } catch (Exception var7) {
            throw new StoreException(var7);
         }
      } else {
         return super.initialize(sm, state, fetch, context);
      }
   }

   public boolean syncVersion(OpenJPAStateManager sm, Object context) {
      if (context instanceof PCResultObjectProvider) {
         context = null;
      }

      return super.syncVersion(sm, context);
   }

   public boolean load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch, int lockLevel, Object context) {
      if (context instanceof PCResultObjectProvider) {
         context = null;
      }

      return super.load(sm, fields, fetch, lockLevel, context);
   }
}
