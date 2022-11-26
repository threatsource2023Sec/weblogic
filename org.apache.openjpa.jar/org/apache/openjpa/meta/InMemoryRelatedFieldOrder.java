package org.apache.openjpa.meta;

import java.util.Comparator;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.util.ImplHelper;

class InMemoryRelatedFieldOrder implements Order, Comparator {
   private final FieldMetaData _rel;
   private final boolean _asc;
   private final OpenJPAConfiguration _conf;

   public InMemoryRelatedFieldOrder(FieldMetaData rel, boolean asc, OpenJPAConfiguration conf) {
      this._rel = rel;
      this._asc = asc;
      this._conf = conf;
   }

   public String getName() {
      return this._rel.getName();
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
      } else if (ImplHelper.isManageable(o1) && ImplHelper.isManageable(o2)) {
         PersistenceCapable pc1 = ImplHelper.toPersistenceCapable(o1, this._conf);
         PersistenceCapable pc2 = ImplHelper.toPersistenceCapable(o2, this._conf);
         OpenJPAStateManager sm1 = (OpenJPAStateManager)pc1.pcGetStateManager();
         OpenJPAStateManager sm2 = (OpenJPAStateManager)pc2.pcGetStateManager();
         if (sm1 != null && sm2 != null) {
            Object v1 = sm1.fetchField(this._rel.getIndex(), false);
            Object v2 = sm2.fetchField(this._rel.getIndex(), false);
            if (v1 == v2) {
               return 0;
            } else if (v1 == null) {
               return this._asc ? -1 : 1;
            } else if (v2 == null) {
               return this._asc ? 1 : -1;
            } else {
               int cmp = ((Comparable)v1).compareTo(v2);
               return this._asc ? cmp : -cmp;
            }
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }
}
