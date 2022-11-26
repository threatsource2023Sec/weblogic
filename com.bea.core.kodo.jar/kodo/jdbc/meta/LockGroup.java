package kodo.jdbc.meta;

import java.io.Serializable;
import java.util.BitSet;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class LockGroup implements Comparable, Serializable {
   public static final String NAME_DEFAULT = "default";
   private final String _name;

   public LockGroup(String name) {
      this._name = name;
   }

   public String getName() {
      return this._name;
   }

   public boolean isDirty(OpenJPAStateManager sm) {
      if (!sm.isDirty()) {
         return false;
      } else {
         FieldMapping[] fms = (FieldMapping[])((FieldMapping[])sm.getMetaData().getFields());
         BitSet dirty = sm.getDirty();
         BitSet flushed = sm.getFlushed();
         int i = 0;

         for(int len = dirty.length(); i < len; ++i) {
            if (dirty.get(i) && !flushed.get(i) && this == ((KodoFieldMapping)fms[i]).getLockGroup()) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isDefault() {
      return "default".equals(this._name);
   }

   public int hashCode() {
      return this._name.hashCode();
   }

   public boolean equals(Object other) {
      return this.compareTo(other) == 0;
   }

   public int compareTo(Object other) {
      if (other == this) {
         return 0;
      } else {
         return !(other instanceof LockGroup) ? 1 : this._name.compareTo(((LockGroup)other).getName());
      }
   }

   public String toString() {
      return "LockGroup@" + System.identityHashCode(this) + ": " + this._name;
   }
}
