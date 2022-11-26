package kodo.jdbc.meta;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.meta.FieldMetaData;

public class KodoFieldMapping extends FieldMapping {
   private LockGroup _lg = null;

   public KodoFieldMapping(String name, Class type, ClassMapping owner) {
      super(name, type, owner);
      this._lg = ((KodoMappingRepository)owner.getMappingRepository()).getLockGroup("default");
   }

   public LockGroup getLockGroup() {
      return this.getManagement() != 3 ? null : this._lg;
   }

   public void setLockGroup(LockGroup lg) {
      this._lg = lg;
   }

   public void copy(FieldMetaData fmd) {
      super.copy(fmd);
      if (this._lg != null && this._lg.isDefault()) {
         this._lg = ((KodoFieldMapping)fmd).getLockGroup();
      }

   }
}
