package kodo.jdbc.meta;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.meta.SourceTrackers;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.MetaDataException;

public class KodoClassMapping extends ClassMapping {
   private static final Localizer _loc = Localizer.forPackage(KodoClassMapping.class);
   private Set _lgSet = null;
   private LockGroup[] _lgs = null;

   protected KodoClassMapping(Class type, MappingRepository repos) {
      super(type, repos);
   }

   protected KodoClassMapping(ValueMetaData vmd) {
      super(vmd);
   }

   public LockGroup[] getLockGroups() {
      if (this._lgs == null) {
         Set lgs = new TreeSet();
         ClassMapping sup = this.getPCSuperclassMapping();
         if (sup != null) {
            lgs.addAll(Arrays.asList(((KodoClassMapping)sup).getLockGroups()));
         }

         FieldMapping[] fms = this.getFieldMappings();

         for(int i = 0; i < fms.length; ++i) {
            if (fms[i].getDefiningMetaData() == this && ((KodoFieldMapping)fms[i]).getLockGroup() != null) {
               lgs.add(((KodoFieldMapping)fms[i]).getLockGroup());
            }
         }

         if (this._lgSet != null) {
            lgs.addAll(this._lgSet);
         }

         if (lgs.isEmpty()) {
            lgs.add(((KodoMappingRepository)this.getMappingRepository()).getLockGroup("default"));
         }

         this._lgs = (LockGroup[])((LockGroup[])lgs.toArray(new LockGroup[lgs.size()]));
      }

      return this._lgs;
   }

   public void addDeclaredLockGroup(LockGroup lg) {
      this._lgs = null;
      if (this._lgSet == null) {
         this._lgSet = new TreeSet();
      }

      this._lgSet.add(lg);
   }

   public boolean removeDeclaredLockGroup(LockGroup lg) {
      if (this._lgSet != null && this._lgSet.remove(lg)) {
         this._lgs = null;
         return true;
      } else {
         return false;
      }
   }

   protected void validateMapping(boolean runtime) {
      super.validateMapping(runtime);
      ClassMapping sup = this.getMappedPCSuperclassMapping();
      if (sup != null && this.getLockGroups().length > ((KodoClassMapping)sup).getLockGroups().length) {
         throw new MetaDataException(_loc.get("sub-lock-groups", this, SourceTrackers.getSourceLocationMessage(new SourceTracker[]{this, sup})));
      } else if (this.getVersionField() != null && this.getLockGroups().length > 1) {
         throw new MetaDataException(_loc.get("vers-mult-lock-groups", this));
      }
   }

   protected void clearAllFieldCache() {
      super.clearAllFieldCache();
      this._lgs = null;
   }
}
