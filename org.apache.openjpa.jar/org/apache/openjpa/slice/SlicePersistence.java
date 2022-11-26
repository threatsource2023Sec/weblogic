package org.apache.openjpa.slice;

import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.kernel.StateManagerImpl;
import org.apache.openjpa.util.ImplHelper;

public class SlicePersistence {
   public static String getSlice(Object obj) {
      if (obj == null) {
         return null;
      } else {
         PersistenceCapable pc = ImplHelper.toPersistenceCapable(obj, (Object)null);
         if (pc == null) {
            return null;
         } else {
            StateManagerImpl sm = (StateManagerImpl)pc.pcGetStateManager();
            if (sm == null) {
               return null;
            } else {
               Object slice = sm.getImplData();
               return slice instanceof String ? (String)slice : null;
            }
         }
      }
   }
}
