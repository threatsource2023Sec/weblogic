package kodo.jdbc.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.openjpa.jdbc.meta.MappingInfo;
import org.apache.openjpa.jdbc.meta.Version;
import org.apache.openjpa.jdbc.meta.VersionMappingInfo;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;

public class LockGroupVersionMappingInfo extends VersionMappingInfo {
   private static final Localizer _loc = Localizer.forPackage(LockGroupVersionMappingInfo.class);
   private List _lgs = null;

   public List getColumnLockGroupNames() {
      return this._lgs == null ? Collections.EMPTY_LIST : this._lgs;
   }

   public void setColumnLockGroupNames(List lgs) {
      this._lgs = lgs;
   }

   public Column[] getColumns(Version version, Column[] tmplates, boolean adapt) {
      List cols = this.getColumns();
      List lgs = this.getColumnLockGroupNames();
      if (!lgs.isEmpty() && cols.size() != lgs.size()) {
         throw new InternalException();
      } else {
         if (!lgs.isEmpty() && cols.size() > 1) {
            List sortedLGs = new ArrayList(lgs);
            Collections.sort(sortedLGs);
            int pos = 0;
            Object cur = cols.get(0);

            for(int i = 0; i < cols.size(); ++i) {
               pos = sortedLGs.indexOf(lgs.get(pos));
               Object next = cols.get(pos);
               cols.set(pos, cur);
               cur = next;
            }
         }

         Table table = version.getClassMapping().getTable();
         version.getMappingRepository().getMappingDefaults().populateColumns(version, table, tmplates);
         return this.createColumns(version, (String)null, tmplates, table, adapt);
      }
   }

   public void syncWith(Version version) {
      super.syncWith(version);
      KodoClassMapping cls = (KodoClassMapping)version.getClassMapping();
      LockGroup[] lgs = cls.getLockGroups();
      Column[] cols = version.getColumns();
      if (cols.length != 0 && (lgs.length > 1 || !lgs[0].isDefault())) {
         if (cols.length != lgs.length) {
            throw new MetaDataException(_loc.get("bad-col-lg-counts", version, String.valueOf(cols.length), String.valueOf(lgs.length)));
         }

         this._lgs = new ArrayList(cols.length);

         for(int i = 0; i < lgs.length; ++i) {
            this._lgs.add(lgs[i].getName());
         }
      }

   }

   protected void clear(boolean canFlags) {
      super.clear(canFlags);
      this._lgs = null;
   }

   public void copy(MappingInfo info) {
      boolean hadColumns = !this.getColumns().isEmpty();
      super.copy(info);
      if (info instanceof LockGroupVersionMappingInfo) {
         LockGroupVersionMappingInfo vinfo = (LockGroupVersionMappingInfo)info;
         if (this.getColumnLockGroupNames().isEmpty() && !hadColumns && !this.getColumns().isEmpty() && !vinfo.getColumnLockGroupNames().isEmpty()) {
            this._lgs = new ArrayList(vinfo.getColumnLockGroupNames());
         }

      }
   }
}
