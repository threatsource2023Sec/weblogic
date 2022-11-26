package org.apache.openjpa.jdbc.meta;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.xml.Commentable;
import org.apache.openjpa.util.UserException;

public class ClassMappingInfo extends MappingInfo implements SourceTracker, Commentable {
   private static final Localizer _loc = Localizer.forPackage(ClassMappingInfo.class);
   private String _className = Object.class.getName();
   private String _tableName = null;
   private String _schemaName = null;
   private boolean _joined = false;
   private Map _seconds = null;
   private String _subStrat = null;
   private File _file = null;
   private int _srcType = 0;
   private String[] _comments = null;
   private Collection _uniques = null;

   public String getClassName() {
      return this._className;
   }

   public void setClassName(String name) {
      this._className = name;
   }

   public String getHierarchyStrategy() {
      return this._subStrat;
   }

   public void setHierarchyStrategy(String strategy) {
      this._subStrat = strategy;
   }

   public String getTableName() {
      return this._tableName;
   }

   public void setTableName(String table) {
      this._tableName = table;
   }

   public String getSchemaName() {
      return this._schemaName;
   }

   public void setSchemaName(String schema) {
      this._schemaName = schema;
   }

   public boolean isJoinedSubclass() {
      return this._joined;
   }

   public void setJoinedSubclass(boolean joined) {
      this._joined = joined;
   }

   public String[] getSecondaryTableNames() {
      return this._seconds == null ? new String[0] : (String[])((String[])this._seconds.keySet().toArray(new String[0]));
   }

   public String getSecondaryTableName(String tableName) {
      if (this._seconds != null && tableName != null && !this._seconds.containsKey(tableName) && tableName.indexOf(46) == -1) {
         String best = tableName;
         int pts = 0;
         Iterator itr = this._seconds.keySet().iterator();

         while(true) {
            String fullJoin;
            int idx;
            do {
               if (!itr.hasNext()) {
                  return best;
               }

               fullJoin = (String)itr.next();
               idx = fullJoin.lastIndexOf(46);
               if (idx == -1 && pts < 2 && fullJoin.equalsIgnoreCase(tableName)) {
                  best = fullJoin;
                  pts = 2;
                  break;
               }
            } while(idx == -1);

            String join = fullJoin.substring(idx + 1);
            if (join.equals(tableName)) {
               return fullJoin;
            }

            if (pts < 1 && join.equalsIgnoreCase(tableName)) {
               best = fullJoin;
               pts = 1;
            }
         }
      } else {
         return tableName;
      }
   }

   public List getSecondaryTableJoinColumns(String tableName) {
      if (this._seconds != null && tableName != null) {
         List cols = (List)this._seconds.get(this.getSecondaryTableName(tableName));
         if (cols == null) {
            int idx = tableName.lastIndexOf(46);
            if (idx != -1) {
               tableName = tableName.substring(idx + 1);
               cols = (List)this._seconds.get(this.getSecondaryTableName(tableName));
            }
         }

         return cols == null ? Collections.EMPTY_LIST : cols;
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   public void setSecondaryTableJoinColumns(String tableName, List cols) {
      if (cols == null) {
         cols = Collections.EMPTY_LIST;
      }

      if (this._seconds == null) {
         this._seconds = new HashMap();
      }

      this._seconds.put(tableName, cols);
   }

   public Table getTable(final ClassMapping cls, boolean adapt) {
      Table t = this.createTable(cls, new MappingInfo.TableDefaults() {
         public String get(Schema schema) {
            return cls.getMappingRepository().getMappingDefaults().getTableName(cls, schema);
         }
      }, this._schemaName, this._tableName, adapt);
      t.setComment(cls.getTypeAlias() == null ? cls.getDescribedType().getName() : cls.getTypeAlias());
      return t;
   }

   public Column[] getDataStoreIdColumns(ClassMapping cls, Column[] tmplates, Table table, boolean adapt) {
      cls.getMappingRepository().getMappingDefaults().populateDataStoreIdColumns(cls, table, tmplates);
      return this.createColumns(cls, "datastoreid", tmplates, table, adapt);
   }

   public ForeignKey getSuperclassJoin(final ClassMapping cls, Table table, boolean adapt) {
      ClassMapping sup = cls.getJoinablePCSuperclassMapping();
      if (sup == null) {
         return null;
      } else {
         MappingInfo.ForeignKeyDefaults def = new MappingInfo.ForeignKeyDefaults() {
            public ForeignKey get(Table local, Table foreign, boolean inverse) {
               return cls.getMappingRepository().getMappingDefaults().getJoinForeignKey(cls, local, foreign);
            }

            public void populate(Table local, Table foreign, Column col, Object target, boolean inverse, int pos, int cols) {
               cls.getMappingRepository().getMappingDefaults().populateJoinColumn(cls, local, foreign, col, target, pos, cols);
            }
         };
         return this.createForeignKey(cls, "superclass", this.getColumns(), def, table, cls, sup, false, adapt);
      }
   }

   public void syncWith(ClassMapping cls) {
      this.clear(false);
      ClassMapping sup = cls.getMappedPCSuperclassMapping();
      if (cls.getTable() != null && (sup == null || sup.getTable() != cls.getTable())) {
         this._tableName = cls.getMappingRepository().getDBDictionary().getFullName(cls.getTable(), true);
      }

      this.setColumnIO(cls.getColumnIO());
      if (cls.getJoinForeignKey() != null && sup != null && sup.getTable() != null) {
         this.syncForeignKey(cls, cls.getJoinForeignKey(), cls.getTable(), sup.getTable());
      } else if (cls.getIdentityType() == 1) {
         this.syncColumns(cls, cls.getPrimaryKeyColumns(), false);
      }

      String strat = cls.getStrategy() == null ? null : cls.getStrategy().getAlias();
      if (strat != null && (cls.getPCSuperclass() != null || !"full".equals(strat))) {
         this.setStrategy(strat);
      }

   }

   public boolean hasSchemaComponents() {
      return super.hasSchemaComponents() || this._tableName != null;
   }

   protected void clear(boolean canFlags) {
      super.clear(canFlags);
      this._tableName = null;
   }

   public void copy(MappingInfo info) {
      super.copy(info);
      if (info instanceof ClassMappingInfo) {
         ClassMappingInfo cinfo = (ClassMappingInfo)info;
         if (this._tableName == null) {
            this._tableName = cinfo.getTableName();
         }

         if (this._subStrat == null) {
            this._subStrat = cinfo.getHierarchyStrategy();
         }

         if (cinfo._seconds != null) {
            if (this._seconds == null) {
               this._seconds = new HashMap();
            }

            Iterator itr = cinfo._seconds.keySet().iterator();

            while(itr.hasNext()) {
               Object key = itr.next();
               if (!this._seconds.containsKey(key)) {
                  this._seconds.put(key, cinfo._seconds.get(key));
               }
            }
         }

         if (cinfo._uniques != null) {
            this._uniques = new ArrayList(cinfo._uniques);
         }

      }
   }

   public void addUnique(Unique unique) {
      if (unique != null) {
         if (this._uniques == null) {
            this._uniques = new ArrayList();
         }

         this._uniques.add(unique);
      }
   }

   public Unique[] getUniques() {
      return this._uniques == null ? new Unique[0] : (Unique[])((Unique[])this._uniques.toArray(new Unique[this._uniques.size()]));
   }

   public Unique[] getUniques(ClassMapping cm, boolean adapt) {
      if (this._uniques != null && !this._uniques.isEmpty()) {
         Iterator uniqueConstraints = this._uniques.iterator();
         Table table = cm.getTable();
         Collection result = new ArrayList();

         while(uniqueConstraints.hasNext()) {
            Unique template = (Unique)uniqueConstraints.next();
            Column[] templateColumns = template.getColumns();
            Column[] uniqueColumns = new Column[templateColumns.length];
            boolean missingColumn = true;

            for(int i = 0; i < uniqueColumns.length; ++i) {
               String columnName = templateColumns[i].getName();
               Column uniqueColumn = table.getColumn(columnName);
               missingColumn = uniqueColumn == null;
               if (missingColumn) {
                  throw new UserException(_loc.get("missing-unique-column", cm, table, columnName));
               }

               uniqueColumns[i] = uniqueColumn;
            }

            Unique unique = super.createUnique(cm, "unique", template, uniqueColumns, adapt);
            if (unique != null) {
               result.add(unique);
            }
         }

         return (Unique[])((Unique[])result.toArray(new Unique[result.size()]));
      } else {
         return new Unique[0];
      }
   }

   public File getSourceFile() {
      return this._file;
   }

   public Object getSourceScope() {
      return null;
   }

   public int getSourceType() {
      return this._srcType;
   }

   public void setSource(File file, int srcType) {
      this._file = file;
      this._srcType = srcType;
   }

   public String getResourceName() {
      return this._className;
   }

   public String[] getComments() {
      return this._comments == null ? EMPTY_COMMENTS : this._comments;
   }

   public void setComments(String[] comments) {
      this._comments = comments;
   }
}
