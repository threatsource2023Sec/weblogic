package kodo.jdbc.meta.strats;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import kodo.jdbc.meta.KodoClassMapping;
import kodo.jdbc.meta.LockGroup;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.VersionMappingInfo;
import org.apache.openjpa.jdbc.meta.strats.AbstractVersionStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Row;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;

public abstract class ColumnPerLockGroupVersionStrategy extends AbstractVersionStrategy {
   private static final Localizer _loc = Localizer.forPackage(ColumnPerLockGroupVersionStrategy.class);

   protected abstract int getJavaType();

   protected abstract Object nextVersion(Object var1);

   protected int compare(Object v1, Object v2) {
      if (v1 == v2) {
         return 0;
      } else if (v1 == null) {
         return -1;
      } else if (v2 == null) {
         return 1;
      } else {
         if (v1.getClass() != v2.getClass()) {
            if (v1 instanceof Number && !(v1 instanceof BigDecimal)) {
               v1 = new BigDecimal(((Number)v1).doubleValue());
            }

            if (v2 instanceof Number && !(v2 instanceof BigDecimal)) {
               v2 = new BigDecimal(((Number)v2).doubleValue());
            }
         }

         return ((Comparable)v1).compareTo(v2);
      }
   }

   public void map(boolean adapt) {
      KodoClassMapping cls = (KodoClassMapping)this.vers.getClassMapping();
      if (cls.getJoinablePCSuperclassMapping() == null && cls.getEmbeddingMetaData() == null) {
         LockGroup[] lgs = cls.getLockGroups();
         if (lgs.length == 0) {
            throw new MetaDataException(_loc.get("no-lock-groups", cls));
         } else {
            VersionMappingInfo info = this.vers.getMappingInfo();
            info.assertNoJoin(this.vers, true);
            info.assertNoForeignKey(this.vers, !adapt);
            info.assertNoUnique(this.vers, false);
            Column[] tmplates = new Column[lgs.length];

            for(int i = 0; i < lgs.length; ++i) {
               tmplates[i] = new Column();
               tmplates[i].setJavaType(this.getJavaType());
               if (lgs[i].isDefault()) {
                  tmplates[i].setName("versn");
               } else {
                  tmplates[i].setName(lgs[i].getName().replace('-', '_') + "_versn");
               }
            }

            Column[] cols = info.getColumns(this.vers, tmplates, adapt);
            this.vers.setColumns(cols);
            this.vers.setColumnIO(info.getColumnIO());
            Index idx = info.getIndex(this.vers, cols, adapt);
            this.vers.setIndex(idx);
         }
      } else {
         throw new MetaDataException(_loc.get("not-base-vers", cls));
      }
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Column[] cols = this.vers.getColumns();
      ColumnIO io = this.vers.getColumnIO();
      Object initial = this.nextVersion((Object)null);
      Row row = rm.getRow(this.vers.getClassMapping().getTable(), 1, sm, true);

      for(int i = 0; i < cols.length; ++i) {
         if (io.isInsertable(i, initial == null)) {
            row.setObject(cols[i], initial);
         }
      }

      Object nextVersion;
      if (cols.length == 1) {
         nextVersion = initial;
      } else {
         nextVersion = new Object[cols.length];
         Arrays.fill((Object[])((Object[])nextVersion), initial);
      }

      sm.setNextVersion(nextVersion);
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      LockGroup[] lgs = ((KodoClassMapping)this.vers.getClassMapping()).getLockGroups();
      Column[] cols = this.vers.getColumns();
      ColumnIO io = this.vers.getColumnIO();
      Row row = null;
      Object curVersion = sm.getVersion();
      Object nextVersion = null;

      for(int i = 0; i < lgs.length; ++i) {
         if (lgs[i].isDirty(sm) || sm.isVersionUpdateRequired()) {
            if (row == null) {
               row = rm.getRow(this.vers.getClassMapping().getTable(), 0, sm, true);
               row.setFailedObject(sm.getManagedInstance());
            }

            Object cur;
            if (lgs.length != 1 && curVersion != null) {
               cur = ((Object[])((Object[])curVersion))[i];
            } else {
               cur = curVersion;
            }

            Object next = this.nextVersion(cur);
            if (nextVersion == null && lgs.length > 1) {
               nextVersion = new Object[lgs.length];
               if (curVersion != null) {
                  System.arraycopy(curVersion, 0, nextVersion, 0, lgs.length);
               }
            }

            if (lgs.length == 1) {
               nextVersion = next;
            } else {
               ((Object[])((Object[])nextVersion))[i] = next;
            }

            if (cur != null && sm.isVersionCheckRequired()) {
               row.whereObject(cols[i], cur);
            }

            if (io.isUpdatable(i, next == null)) {
               row.setObject(cols[i], next);
            }
         }
      }

      if (nextVersion != null) {
         sm.setNextVersion(nextVersion);
      }

   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
      Row row = rm.getRow(this.vers.getClassMapping().getTable(), 2, sm, true);
      row.setFailedObject(sm.getManagedInstance());
      Column[] cols = this.vers.getColumns();
      Object curVersion = sm.getVersion();

      for(int i = 0; i < cols.length; ++i) {
         Object cur;
         if (cols.length != 1 && curVersion != null) {
            cur = ((Object[])((Object[])curVersion))[i];
         } else {
            cur = curVersion;
         }

         if (cur != null) {
            row.whereObject(cols[i], cur);
         }
      }

   }

   public boolean select(Select sel, ClassMapping mapping) {
      sel.select(this.vers.getColumns());
      return true;
   }

   public void load(OpenJPAStateManager sm, JDBCStore store, Result res) throws SQLException {
      Column[] cols = this.vers.getColumns();
      if (res.contains(cols[0])) {
         Object version = null;
         if (cols.length > 0) {
            version = new Object[cols.length];
         }

         for(int i = 0; i < cols.length; ++i) {
            if (i > 0 && !res.contains(cols[i])) {
               return;
            }

            Object cur = res.getObject(cols[i], -1, (Object)null);
            if (cols.length == 1) {
               version = cur;
            } else {
               ((Object[])((Object[])version))[i] = cur;
            }
         }

         sm.setVersion(version);
      }
   }

   public boolean checkVersion(OpenJPAStateManager sm, JDBCStore store, boolean updateVersion) throws SQLException {
      Column[] cols = this.vers.getColumns();
      Select sel = store.getSQLFactory().newSelect();
      sel.select(cols);
      sel.wherePrimaryKey(sm.getObjectId(), this.vers.getClassMapping(), store);
      Result res = sel.execute(store, (JDBCFetchConfiguration)null);

      boolean var17;
      try {
         if (!res.next()) {
            boolean var16 = false;
            return var16;
         }

         Object memVersion = sm.getVersion();
         Object dbVersion = null;
         if (cols.length > 1) {
            dbVersion = new Object[cols.length];
         }

         boolean refresh = false;

         for(int i = 0; i < cols.length; ++i) {
            Object db = res.getObject(cols[i], -1, (Object)null);
            if (cols.length == 1) {
               dbVersion = db;
            } else {
               ((Object[])((Object[])dbVersion))[i] = db;
            }

            if (!refresh) {
               Object mem;
               if (cols.length != 1 && memVersion != null) {
                  mem = ((Object[])((Object[])memVersion))[i];
               } else {
                  mem = memVersion;
               }

               if (mem == null || db != null && this.compare(mem, db) < 0) {
                  refresh = true;
               }
            }
         }

         if (updateVersion) {
            sm.setVersion(dbVersion);
         }

         var17 = !refresh;
      } finally {
         res.close();
      }

      return var17;
   }

   public int compareVersion(Object v1, Object v2) {
      if (v1 == v2) {
         return 3;
      } else if (v1 != null && v2 != null) {
         LockGroup[] lgs = ((KodoClassMapping)this.vers.getClassMapping()).getLockGroups();
         int cmp = 0;
         if (lgs.length == 1) {
            cmp = this.compare(v1, v2);
         } else {
            Object[] vs1 = (Object[])((Object[])v1);
            Object[] vs2 = (Object[])((Object[])v2);

            for(int i = 0; i < lgs.length; ++i) {
               int cur = this.compare(vs1[i], vs2[i]);
               if (cur != 0) {
                  cmp = cur;
               }

               if (cmp < 0) {
                  break;
               }
            }
         }

         if (cmp < 0) {
            return 2;
         } else {
            return cmp > 0 ? 1 : 3;
         }
      } else {
         return 4;
      }
   }
}
