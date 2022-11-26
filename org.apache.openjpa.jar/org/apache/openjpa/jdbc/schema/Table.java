package org.apache.openjpa.jdbc.schema;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.meta.SourceTracker;

public class Table extends NameSet implements Comparable, SourceTracker {
   private String _name = null;
   private String _schemaName = null;
   private Map _colMap = null;
   private Map _idxMap = null;
   private Collection _fkList = null;
   private Collection _unqList = null;
   private Schema _schema = null;
   private PrimaryKey _pk = null;
   private File _source = null;
   private int _srcType = 0;
   private String _fullName = null;
   private Column[] _cols = null;
   private Column[] _autoAssign = null;
   private Column[] _rels = null;
   private ForeignKey[] _fks = null;
   private Index[] _idxs = null;
   private Unique[] _unqs = null;
   private String _comment = null;

   public Table() {
   }

   public Table(String name, Schema schema) {
      this.setName(name);
      this.addName(name, true);
      if (schema != null) {
         this.setSchemaName(schema.getName());
      }

      this._schema = schema;
   }

   void remove() {
      ForeignKey[] fks = this.getForeignKeys();

      for(int i = 0; i < fks.length; ++i) {
         this.removeForeignKey(fks[i]);
      }

      Index[] idxs = this.getIndexes();

      for(int i = 0; i < idxs.length; ++i) {
         this.removeIndex(idxs[i]);
      }

      Unique[] unqs = this.getUniques();

      for(int i = 0; i < unqs.length; ++i) {
         this.removeUnique(unqs[i]);
      }

      this.removePrimaryKey();
      Column[] cols = this.getColumns();

      for(int i = 0; i < cols.length; ++i) {
         this.removeColumn(cols[i]);
      }

      this._schema = null;
      this._schemaName = null;
      this._fullName = null;
   }

   public Schema getSchema() {
      return this._schema;
   }

   public String getSchemaName() {
      return this._schemaName;
   }

   public void setSchemaName(String name) {
      if (this.getSchema() != null) {
         throw new IllegalStateException();
      } else {
         this._schemaName = name;
         this._fullName = null;
      }
   }

   public String getName() {
      return this._name;
   }

   public void setName(String name) {
      if (this.getSchema() != null) {
         throw new IllegalStateException();
      } else {
         this._name = name;
         this._fullName = null;
      }
   }

   public String getFullName() {
      if (this._fullName == null) {
         Schema schema = this.getSchema();
         if (schema != null && schema.getName() != null) {
            this._fullName = schema.getName() + "." + this.getName();
         } else {
            this._fullName = this.getName();
         }
      }

      return this._fullName;
   }

   public File getSourceFile() {
      return this._source;
   }

   public Object getSourceScope() {
      return null;
   }

   public int getSourceType() {
      return this._srcType;
   }

   public void setSource(File source, int srcType) {
      this._source = source;
      this._srcType = srcType;
   }

   public String getResourceName() {
      return this.getFullName();
   }

   public Column[] getColumns() {
      if (this._cols == null) {
         if (this._colMap == null) {
            this._cols = Schemas.EMPTY_COLUMNS;
         } else {
            Column[] cols = new Column[this._colMap.size()];
            Iterator itr = this._colMap.values().iterator();

            for(int i = 0; itr.hasNext(); ++i) {
               cols[i] = (Column)itr.next();
               cols[i].setIndex(i);
            }

            this._cols = cols;
         }
      }

      return this._cols;
   }

   public Column[] getAutoAssignedColumns() {
      if (this._autoAssign == null) {
         if (this._colMap == null) {
            this._autoAssign = Schemas.EMPTY_COLUMNS;
         } else {
            Collection autos = null;
            Column[] cols = this.getColumns();

            for(int i = 0; i < cols.length; ++i) {
               if (cols[i].isAutoAssigned()) {
                  if (autos == null) {
                     autos = new ArrayList(3);
                  }

                  autos.add(cols[i]);
               }
            }

            this._autoAssign = autos == null ? Schemas.EMPTY_COLUMNS : (Column[])((Column[])autos.toArray(new Column[autos.size()]));
         }
      }

      return this._autoAssign;
   }

   public Column[] getRelationIdColumns() {
      if (this._rels == null) {
         if (this._colMap == null) {
            this._rels = Schemas.EMPTY_COLUMNS;
         } else {
            Collection rels = null;
            Column[] cols = this.getColumns();

            for(int i = 0; i < cols.length; ++i) {
               if (cols[i].isRelationId()) {
                  if (rels == null) {
                     rels = new ArrayList(3);
                  }

                  rels.add(cols[i]);
               }
            }

            this._rels = rels == null ? Schemas.EMPTY_COLUMNS : (Column[])((Column[])rels.toArray(new Column[rels.size()]));
         }
      }

      return this._rels;
   }

   public Column getColumn(String name) {
      return name != null && this._colMap != null ? (Column)this._colMap.get(name.toUpperCase()) : null;
   }

   public Column addColumn(String name) {
      this.addName(name, true);
      Schema schema = this.getSchema();
      Column col;
      if (schema != null && schema.getSchemaGroup() != null) {
         col = schema.getSchemaGroup().newColumn(name, this);
      } else {
         col = new Column(name, this);
      }

      if (this._colMap == null) {
         this._colMap = new LinkedHashMap();
      }

      this._colMap.put(name.toUpperCase(), col);
      this._cols = null;
      return col;
   }

   public boolean removeColumn(Column col) {
      if (col != null && this._colMap != null) {
         Column cur = (Column)this._colMap.get(col.getName().toUpperCase());
         if (!col.equals(cur)) {
            return false;
         } else {
            this.removeName(col.getName());
            this._colMap.remove(col.getName().toUpperCase());
            this._cols = null;
            if (col.isAutoAssigned()) {
               this._autoAssign = null;
            }

            if (col.isRelationId()) {
               this._rels = null;
            }

            col.remove();
            return true;
         }
      } else {
         return false;
      }
   }

   public Column importColumn(Column col) {
      if (col == null) {
         return null;
      } else {
         Column copy = this.addColumn(col.getName());
         copy.setType(col.getType());
         copy.setTypeName(col.getTypeName());
         copy.setJavaType(col.getJavaType());
         copy.setNotNull(col.isNotNull());
         copy.setDefaultString(col.getDefaultString());
         copy.setSize(col.getSize());
         copy.setDecimalDigits(col.getDecimalDigits());
         copy.setAutoAssigned(col.isAutoAssigned());
         return copy;
      }
   }

   public PrimaryKey getPrimaryKey() {
      return this._pk;
   }

   public PrimaryKey addPrimaryKey() {
      return this.addPrimaryKey((String)null);
   }

   public PrimaryKey addPrimaryKey(String name) {
      Schema schema = this.getSchema();
      if (schema != null && schema.getSchemaGroup() != null) {
         schema.getSchemaGroup().addName(name, false);
         this._pk = schema.getSchemaGroup().newPrimaryKey(name, this);
      } else {
         this._pk = new PrimaryKey(name, this);
      }

      return this._pk;
   }

   public boolean removePrimaryKey() {
      boolean rem = this._pk != null;
      if (rem) {
         Schema schema = this.getSchema();
         if (schema != null && schema.getSchemaGroup() != null) {
            schema.getSchemaGroup().removeName(this._pk.getName());
         }

         this._pk.remove();
      }

      this._pk = null;
      return rem;
   }

   public PrimaryKey importPrimaryKey(PrimaryKey pk) {
      if (pk == null) {
         return null;
      } else {
         PrimaryKey copy = this.addPrimaryKey(pk.getName());
         copy.setLogical(pk.isLogical());
         Column[] cols = pk.getColumns();

         for(int i = 0; i < cols.length; ++i) {
            copy.addColumn(this.getColumn(cols[i].getName()));
         }

         return copy;
      }
   }

   public ForeignKey getForeignKey(String name) {
      ForeignKey[] fks = this.getForeignKeys();

      for(int i = 0; i < fks.length; ++i) {
         if (StringUtils.equalsIgnoreCase(name, fks[i].getName())) {
            return fks[i];
         }
      }

      return null;
   }

   public ForeignKey[] getForeignKeys() {
      if (this._fks == null) {
         if (this._fkList == null) {
            this._fks = Schemas.EMPTY_FOREIGN_KEYS;
         } else {
            ForeignKey[] fks = new ForeignKey[this._fkList.size()];
            Iterator itr = this._fkList.iterator();

            for(int i = 0; itr.hasNext(); ++i) {
               fks[i] = (ForeignKey)itr.next();
               fks[i].setIndex(i);
            }

            this._fks = fks;
         }
      }

      return this._fks;
   }

   public ForeignKey addForeignKey() {
      return this.addForeignKey((String)null);
   }

   public ForeignKey addForeignKey(String name) {
      Schema schema = this.getSchema();
      ForeignKey fk;
      if (schema != null && schema.getSchemaGroup() != null) {
         schema.getSchemaGroup().addName(name, false);
         fk = schema.getSchemaGroup().newForeignKey(name, this);
      } else {
         fk = new ForeignKey(name, this);
      }

      if (this._fkList == null) {
         this._fkList = new ArrayList(3);
      }

      this._fkList.add(fk);
      this._fks = null;
      return fk;
   }

   public boolean removeForeignKey(ForeignKey fk) {
      if (fk != null && this._fkList != null) {
         if (!this._fkList.remove(fk)) {
            return false;
         } else {
            Schema schema = this.getSchema();
            if (schema != null && schema.getSchemaGroup() != null) {
               schema.getSchemaGroup().removeName(fk.getName());
            }

            this._fks = null;
            fk.remove();
            return true;
         }
      } else {
         return false;
      }
   }

   public ForeignKey importForeignKey(ForeignKey fk) {
      if (fk == null) {
         return null;
      } else {
         ForeignKey copy = this.addForeignKey(fk.getName());
         copy.setDeleteAction(fk.getDeleteAction());
         Schema schema = this.getSchema();
         if (schema != null && schema.getSchemaGroup() != null) {
            Column[] pks = fk.getPrimaryKeyColumns();
            Table joined = null;
            if (pks.length > 0) {
               joined = schema.getSchemaGroup().findTable(pks[0].getTable());
            }

            Column[] cols = fk.getColumns();

            int i;
            for(i = 0; i < cols.length; ++i) {
               copy.join(this.getColumn(cols[i].getName()), joined.getColumn(pks[i].getName()));
            }

            cols = fk.getConstantColumns();

            for(i = 0; i < cols.length; ++i) {
               copy.joinConstant(this.getColumn(cols[i].getName()), fk.getPrimaryKeyConstant(cols[i]));
            }

            pks = fk.getConstantPrimaryKeyColumns();
            if (joined == null && pks.length > 0) {
               joined = schema.getSchemaGroup().findTable(pks[0].getTable());
            }

            for(i = 0; i < pks.length; ++i) {
               copy.joinConstant(fk.getConstant(pks[i]), joined.getColumn(pks[i].getName()));
            }
         }

         return copy;
      }
   }

   public Index[] getIndexes() {
      if (this._idxs == null || this._idxs.length == 0) {
         this._idxs = this._idxMap == null ? Schemas.EMPTY_INDEXES : (Index[])((Index[])this._idxMap.values().toArray(new Index[this._idxMap.size()]));
      }

      return this._idxs;
   }

   public Index getIndex(String name) {
      return name != null && this._idxMap != null ? (Index)this._idxMap.get(name.toUpperCase()) : null;
   }

   public Index addIndex(String name) {
      Schema schema = this.getSchema();
      Index idx;
      if (schema != null && schema.getSchemaGroup() != null) {
         schema.getSchemaGroup().addName(name, true);
         idx = schema.getSchemaGroup().newIndex(name, this);
      } else {
         idx = new Index(name, this);
      }

      if (this._idxMap == null) {
         this._idxMap = new TreeMap();
      }

      this._idxMap.put(name.toUpperCase(), idx);
      this._idxs = null;
      return idx;
   }

   public boolean removeIndex(Index idx) {
      if (idx != null && this._idxMap != null) {
         Index cur = (Index)this._idxMap.get(idx.getName().toUpperCase());
         if (!idx.equals(cur)) {
            return false;
         } else {
            this._idxMap.remove(idx.getName().toUpperCase());
            Schema schema = this.getSchema();
            if (schema != null && schema.getSchemaGroup() != null) {
               schema.getSchemaGroup().removeName(idx.getName());
            }

            idx.remove();
            this._idxs = null;
            return true;
         }
      } else {
         return false;
      }
   }

   public Index importIndex(Index idx) {
      if (idx == null) {
         return null;
      } else {
         Index copy = this.addIndex(idx.getName());
         copy.setUnique(idx.isUnique());
         Column[] cols = idx.getColumns();

         for(int i = 0; i < cols.length; ++i) {
            copy.addColumn(this.getColumn(cols[i].getName()));
         }

         return copy;
      }
   }

   public Unique[] getUniques() {
      if (this._unqs == null) {
         this._unqs = this._unqList == null ? Schemas.EMPTY_UNIQUES : (Unique[])((Unique[])this._unqList.toArray(new Unique[this._unqList.size()]));
      }

      return this._unqs;
   }

   public Unique getUnique(String name) {
      Unique[] unqs = this.getUniques();

      for(int i = 0; i < unqs.length; ++i) {
         if (StringUtils.equalsIgnoreCase(name, unqs[i].getName())) {
            return unqs[i];
         }
      }

      return null;
   }

   public Unique addUnique(String name) {
      Schema schema = this.getSchema();
      Unique unq;
      if (schema != null && schema.getSchemaGroup() != null) {
         schema.getSchemaGroup().addName(name, false);
         unq = schema.getSchemaGroup().newUnique(name, this);
      } else {
         unq = new Unique(name, this);
      }

      if (this._unqList == null) {
         this._unqList = new ArrayList(3);
      }

      this._unqList.add(unq);
      this._unqs = null;
      return unq;
   }

   public boolean removeUnique(Unique unq) {
      if (unq != null && this._unqList != null) {
         if (!this._unqList.remove(unq)) {
            return false;
         } else {
            Schema schema = this.getSchema();
            if (schema != null && schema.getSchemaGroup() != null) {
               schema.getSchemaGroup().removeName(unq.getName());
            }

            this._unqs = null;
            unq.remove();
            return true;
         }
      } else {
         return false;
      }
   }

   public Unique importUnique(Unique unq) {
      if (unq == null) {
         return null;
      } else {
         Unique copy = this.addUnique(unq.getName());
         copy.setDeferred(unq.isDeferred());
         Column[] cols = unq.getColumns();

         for(int i = 0; i < cols.length; ++i) {
            copy.addColumn(this.getColumn(cols[i].getName()));
         }

         return copy;
      }
   }

   void indexColumns() {
      this.getColumns();
   }

   void indexForeignKeys() {
      this.getForeignKeys();
   }

   void changeAutoAssigned(Column col) {
      this._autoAssign = null;
   }

   void changeRelationId(Column col) {
      this._rels = null;
   }

   public int compareTo(Object other) {
      String name = this.getFullName();
      String otherName = ((Table)other).getFullName();
      if (name == null && otherName == null) {
         return 0;
      } else if (name == null) {
         return 1;
      } else {
         return otherName == null ? -1 : name.compareTo(otherName);
      }
   }

   public String toString() {
      return this.getFullName();
   }

   public boolean hasComment() {
      return this._comment != null && !this._comment.equalsIgnoreCase(this._name);
   }

   public String getComment() {
      return this._comment;
   }

   public void setComment(String comment) {
      this._comment = comment;
   }

   public void addSubColumn(String name) {
      this.addSubName(name);
   }

   public void resetSubColumns() {
      this.resetSubNames();
   }
}
