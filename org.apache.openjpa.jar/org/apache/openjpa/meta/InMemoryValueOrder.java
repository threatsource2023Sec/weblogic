package org.apache.openjpa.meta;

import java.util.Comparator;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.ImplHelper;

class InMemoryValueOrder implements Order, Comparator {
   private final boolean _asc;
   private final OpenJPAConfiguration _conf;

   public InMemoryValueOrder(boolean asc, OpenJPAConfiguration conf) {
      this._asc = asc;
      this._conf = conf;
   }

   public String getName() {
      return "#element";
   }

   public boolean isAscending() {
      return this._asc;
   }

   public Comparator getComparator() {
      return this;
   }

   public int compare(Object o1, Object o2) {
      if (o1 == o2) {
         return 0;
      } else if (o1 == null) {
         return this._asc ? -1 : 1;
      } else if (o2 == null) {
         return this._asc ? 1 : -1;
      } else {
         int cmp;
         if (ImplHelper.isManageable(o1) && ImplHelper.isManageable(o2)) {
            PersistenceCapable pc1 = ImplHelper.toPersistenceCapable(o1, this._conf);
            PersistenceCapable pc2 = ImplHelper.toPersistenceCapable(o2, this._conf);
            OpenJPAStateManager sm1 = (OpenJPAStateManager)pc1.pcGetStateManager();
            OpenJPAStateManager sm2 = (OpenJPAStateManager)pc2.pcGetStateManager();
            if (sm1 != null && sm2 != null) {
               Object[] pk1 = toPKValues(sm1);
               Object[] pk2 = toPKValues(sm2);
               int len = Math.min(pk1.length, pk2.length);

               for(int i = 0; i < len; ++i) {
                  if (pk1[i] == pk2[i]) {
                     return 0;
                  }

                  if (pk1[i] == null) {
                     return this._asc ? -1 : 1;
                  }

                  if (pk2[i] == null) {
                     return this._asc ? 1 : -1;
                  }

                  cmp = ((Comparable)pk1[i]).compareTo(pk2[i]);
                  if (cmp != 0) {
                     return this._asc ? cmp : -cmp;
                  }
               }

               cmp = pk1.length - pk2.length;
               return this._asc ? cmp : -cmp;
            } else {
               return 0;
            }
         } else {
            cmp = ((Comparable)o1).compareTo(o2);
            return this._asc ? cmp : -cmp;
         }
      }
   }

   private static Object[] toPKValues(OpenJPAStateManager sm) {
      if (sm.getMetaData().getIdentityType() != 2) {
         return new Object[]{sm.getObjectId()};
      } else {
         Object[] pks = ApplicationIds.toPKValues(sm.getObjectId(), sm.getMetaData());
         if (pks == null) {
            pks = new Object[]{null};
         }

         return pks;
      }
   }
}
