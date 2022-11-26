package org.apache.openjpa.jdbc.schema;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.lib.meta.XMLMetaDataParser;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class XMLSchemaParser extends XMLMetaDataParser implements SchemaParser {
   private static final Localizer _loc = Localizer.forPackage(XMLSchemaParser.class);
   private final DBDictionary _dict;
   private SchemaGroup _group = null;
   private Schema _schema = null;
   private Table _table = null;
   private PrimaryKeyInfo _pk = null;
   private IndexInfo _index = null;
   private UniqueInfo _unq = null;
   private ForeignKeyInfo _fk = null;
   private boolean _delay = false;
   private final Collection _pkInfos = new LinkedList();
   private final Collection _indexInfos = new LinkedList();
   private final Collection _unqInfos = new LinkedList();
   private final Collection _fkInfos = new LinkedList();

   public XMLSchemaParser(JDBCConfiguration conf) {
      this._dict = conf.getDBDictionaryInstance();
      this.setLog(conf.getLog("openjpa.jdbc.Schema"));
      this.setParseText(false);
      this.setSuffix(".schema");
   }

   public boolean getDelayConstraintResolve() {
      return this._delay;
   }

   public void setDelayConstraintResolve(boolean delay) {
      this._delay = delay;
   }

   public void resolveConstraints() {
      this.resolvePrimaryKeys();
      this.resolveIndexes();
      this.resolveForeignKeys();
      this.resolveUniques();
      this.clearConstraintInfo();
   }

   private void clearConstraintInfo() {
      this._pkInfos.clear();
      this._indexInfos.clear();
      this._fkInfos.clear();
      this._unqInfos.clear();
   }

   public SchemaGroup getSchemaGroup() {
      if (this._group == null) {
         this._group = new SchemaGroup();
      }

      return this._group;
   }

   public void setSchemaGroup(SchemaGroup group) {
      this._group = group;
   }

   protected void finish() {
      super.finish();
      if (!this._delay) {
         this.resolveConstraints();
      }

   }

   private void resolvePrimaryKeys() {
      Iterator itr = this._pkInfos.iterator();

      while(itr.hasNext()) {
         PrimaryKeyInfo pkInfo = (PrimaryKeyInfo)itr.next();

         Column col;
         for(Iterator cols = pkInfo.cols.iterator(); cols.hasNext(); pkInfo.pk.addColumn(col)) {
            String colName = (String)cols.next();
            col = pkInfo.pk.getTable().getColumn(colName);
            if (col == null) {
               this.throwUserException(_loc.get("pk-resolve", new Object[]{colName, pkInfo.pk.getTable()}));
            }
         }
      }

   }

   private void resolveIndexes() {
      Iterator itr = this._indexInfos.iterator();

      while(itr.hasNext()) {
         IndexInfo indexInfo = (IndexInfo)itr.next();

         Column col;
         for(Iterator cols = indexInfo.cols.iterator(); cols.hasNext(); indexInfo.index.addColumn(col)) {
            String colName = (String)cols.next();
            col = indexInfo.index.getTable().getColumn(colName);
            if (col == null) {
               this.throwUserException(_loc.get("index-resolve", new Object[]{indexInfo.index, colName, indexInfo.index.getTable()}));
            }
         }
      }

   }

   private void resolveForeignKeys() {
      Iterator itr = this._fkInfos.iterator();

      while(itr.hasNext()) {
         ForeignKeyInfo fkInfo = (ForeignKeyInfo)itr.next();
         Table toTable = this._group.findTable(fkInfo.toTable);
         if (toTable == null || toTable.getPrimaryKey() == null) {
            this.throwUserException(_loc.get("fk-totable", new Object[]{fkInfo.fk, fkInfo.toTable, fkInfo.fk.getTable()}));
         }

         PrimaryKey pk = toTable.getPrimaryKey();
         if (fkInfo.cols.size() == 1 && fkInfo.pks.size() == 0) {
            fkInfo.pks.add(pk.getColumns()[0].getName());
         }

         Iterator pks = fkInfo.pks.iterator();

         Column col;
         String colName;
         Column pkCol;
         String pkColName;
         Iterator cols;
         for(cols = fkInfo.cols.iterator(); cols.hasNext(); fkInfo.fk.join(col, pkCol)) {
            colName = (String)cols.next();
            col = fkInfo.fk.getTable().getColumn(colName);
            if (col == null) {
               this.throwUserException(_loc.get("fk-nocol", fkInfo.fk, colName, fkInfo.fk.getTable()));
            }

            pkColName = (String)pks.next();
            pkCol = toTable.getColumn(pkColName);
            if (pkCol == null) {
               this.throwUserException(_loc.get("fk-nopkcol", new Object[]{fkInfo.fk, pkColName, toTable, fkInfo.fk.getTable()}));
            }
         }

         cols = fkInfo.constCols.iterator();

         Iterator vals;
         for(vals = fkInfo.consts.iterator(); vals.hasNext(); fkInfo.fk.joinConstant(col, vals.next())) {
            colName = (String)cols.next();
            col = fkInfo.fk.getTable().getColumn(colName);
            if (col == null) {
               this.throwUserException(_loc.get("fk-nocol", fkInfo.fk, colName, fkInfo.fk.getTable()));
            }
         }

         pks = fkInfo.constColsPK.iterator();

         for(vals = fkInfo.constsPK.iterator(); vals.hasNext(); fkInfo.fk.joinConstant(vals.next(), pkCol)) {
            pkColName = (String)pks.next();
            pkCol = toTable.getColumn(pkColName);
            if (pkCol == null) {
               this.throwUserException(_loc.get("fk-nopkcol", new Object[]{fkInfo.fk, pkColName, toTable, fkInfo.fk.getTable()}));
            }
         }
      }

   }

   private void resolveUniques() {
      Iterator itr = this._unqInfos.iterator();

      while(itr.hasNext()) {
         UniqueInfo unqInfo = (UniqueInfo)itr.next();

         Column col;
         for(Iterator cols = unqInfo.cols.iterator(); cols.hasNext(); unqInfo.unq.addColumn(col)) {
            String colName = (String)cols.next();
            col = unqInfo.unq.getTable().getColumn(colName);
            if (col == null) {
               this.throwUserException(_loc.get("unq-resolve", new Object[]{unqInfo.unq, colName, unqInfo.unq.getTable()}));
            }
         }
      }

   }

   protected void reset() {
      this._schema = null;
      this._table = null;
      this._pk = null;
      this._index = null;
      this._fk = null;
      this._unq = null;
      if (!this._delay) {
         this.clearConstraintInfo();
      }

   }

   protected Reader getDocType() throws IOException {
      return new InputStreamReader(XMLSchemaParser.class.getResourceAsStream("schemas-doctype.rsrc"));
   }

   protected boolean startElement(String name, Attributes attrs) throws SAXException {
      switch (name.charAt(0)) {
         case 'c':
            this.startColumn(attrs);
            return true;
         case 'd':
         case 'e':
         case 'g':
         case 'h':
         case 'k':
         case 'l':
         case 'm':
         case 'n':
         case 'q':
         case 'r':
         default:
            return false;
         case 'f':
            this.startForeignKey(attrs);
            return true;
         case 'i':
            this.startIndex(attrs);
            return true;
         case 'j':
            this.startJoin(attrs);
            return true;
         case 'o':
            this.startOn(attrs);
            return true;
         case 'p':
            this.startPrimaryKey(attrs);
            return true;
         case 's':
            if ("schema".equals(name)) {
               this.startSchema(attrs);
            } else if ("sequence".equals(name)) {
               this.startSequence(attrs);
            }

            return true;
         case 't':
            this.startTable(attrs);
            return true;
         case 'u':
            this.startUnique(attrs);
            return true;
      }
   }

   protected void endElement(String name) {
      switch (name.charAt(0)) {
         case 'f':
            this.endForeignKey();
         case 'g':
         case 'h':
         case 'j':
         case 'k':
         case 'l':
         case 'm':
         case 'n':
         case 'o':
         case 'q':
         case 'r':
         default:
            break;
         case 'i':
            this.endIndex();
            break;
         case 'p':
            this.endPrimaryKey();
            break;
         case 's':
            if ("schema".equals(name)) {
               this.endSchema();
            }
            break;
         case 't':
            this.endTable();
            break;
         case 'u':
            this.endUnique();
      }

   }

   private void startSchema(Attributes attrs) {
      SchemaGroup group = this.getSchemaGroup();
      String name = attrs.getValue("name");
      this._schema = group.getSchema(name);
      if (this._schema == null) {
         this._schema = group.addSchema(name);
      }

   }

   private void endSchema() {
      this._schema = null;
   }

   private void startSequence(Attributes attrs) {
      Sequence seq = this._schema.addSequence(attrs.getValue("name"));
      seq.setSource(this.getSourceFile(), 2);

      try {
         String val = attrs.getValue("initial-value");
         if (val != null) {
            seq.setInitialValue(Integer.parseInt(val));
         }

         val = attrs.getValue("increment");
         if (val != null) {
            seq.setIncrement(Integer.parseInt(val));
         }

         val = attrs.getValue("allocate");
         if (val != null) {
            seq.setAllocate(Integer.parseInt(val));
         }
      } catch (NumberFormatException var4) {
         this.throwUserException(_loc.get("bad-seq-num", (Object)seq.getFullName()));
      }

   }

   private void startTable(Attributes attrs) {
      this._table = this._schema.addTable(attrs.getValue("name"));
      Table var10000 = this._table;
      File var10001 = this.getSourceFile();
      Table var10002 = this._table;
      var10000.setSource(var10001, 2);
   }

   private void endTable() {
      this._table = null;
   }

   private void startColumn(Attributes attrs) {
      Column col = this._table.addColumn(attrs.getValue("name"));
      col.setType(this._dict.getPreferredType(Schemas.getJDBCType(attrs.getValue("type"))));
      col.setTypeName(attrs.getValue("type-name"));
      String val = attrs.getValue("size");
      if (val != null) {
         col.setSize(Integer.parseInt(val));
      }

      val = attrs.getValue("decimal-digits");
      if (val != null) {
         col.setDecimalDigits(Integer.parseInt(val));
      }

      col.setNotNull("true".equals(attrs.getValue("not-null")));
      col.setAutoAssigned("true".equals(attrs.getValue("auto-assign")) || "true".equals(attrs.getValue("auto-increment")));
      col.setDefaultString(attrs.getValue("default"));
   }

   private void startPrimaryKey(Attributes attrs) {
      this._pk = new PrimaryKeyInfo();
      this._pk.pk = this._table.addPrimaryKey(attrs.getValue("name"));
      this._pk.pk.setLogical("true".equals(attrs.getValue("logical")));
      String val = attrs.getValue("column");
      if (val != null) {
         this._pk.cols.add(val);
      }

   }

   private void endPrimaryKey() {
      this._pkInfos.add(this._pk);
      this._pk = null;
   }

   private void startIndex(Attributes attrs) {
      this._index = new IndexInfo();
      this._index.index = this._table.addIndex(attrs.getValue("name"));
      this._index.index.setUnique("true".equals(attrs.getValue("unique")));
      String val = attrs.getValue("column");
      if (val != null) {
         this._index.cols.add(val);
      }

   }

   private void endIndex() {
      this._indexInfos.add(this._index);
      this._index = null;
   }

   private void startUnique(Attributes attrs) {
      this._unq = new UniqueInfo();
      this._unq.unq = this._table.addUnique(attrs.getValue("name"));
      this._unq.unq.setDeferred("true".equals(attrs.getValue("deferred")));
      String val = attrs.getValue("column");
      if (val != null) {
         this._unq.cols.add(val);
      }

   }

   private void endUnique() {
      this._unqInfos.add(this._unq);
      this._unq = null;
   }

   private void startForeignKey(Attributes attrs) {
      this._fk = new ForeignKeyInfo();
      this._fk.fk = this._table.addForeignKey(attrs.getValue("name"));
      if ("true".equals(attrs.getValue("deferred"))) {
         this._fk.fk.setDeferred(true);
      }

      String action = attrs.getValue("update-action");
      if (action != null) {
         this._fk.fk.setUpdateAction(ForeignKey.getAction(action));
      }

      action = attrs.getValue("delete-action");
      if (action != null) {
         this._fk.fk.setDeleteAction(ForeignKey.getAction(action));
      }

      this._fk.toTable = attrs.getValue("to-table");
      String val = attrs.getValue("column");
      if (val != null) {
         this._fk.cols.add(val);
      }

   }

   private void endForeignKey() {
      this._fkInfos.add(this._fk);
      this._fk = null;
   }

   private void startOn(Attributes attrs) {
      String col = attrs.getValue("column");
      if (this._pk != null) {
         this._pk.cols.add(col);
      } else if (this._index != null) {
         this._index.cols.add(col);
      } else {
         this._unq.cols.add(col);
      }

   }

   private void startJoin(Attributes attrs) {
      String col = attrs.getValue("column");
      String toCol = attrs.getValue("to-column");
      String val = attrs.getValue("value");
      if (val == null) {
         this._fk.cols.add(col);
         this._fk.pks.add(toCol);
      } else if (col == null) {
         this._fk.constsPK.add(convertConstant(val));
         this._fk.constColsPK.add(toCol);
      } else {
         this._fk.consts.add(convertConstant(val));
         this._fk.constCols.add(col);
      }

   }

   private static Object convertConstant(String val) {
      if ("null".equals(val)) {
         return null;
      } else if (val.startsWith("'")) {
         return val.substring(1, val.length() - 1);
      } else {
         return val.indexOf(46) == -1 ? new Long(val) : new Double(val);
      }
   }

   private void throwUserException(Localizer.Message msg) {
      throw new UserException(this.getSourceName() + ": " + msg.getMessage());
   }

   private static class ForeignKeyInfo {
      public ForeignKey fk;
      public String toTable;
      public Collection cols;
      public Collection pks;
      public Collection consts;
      public Collection constCols;
      public Collection constsPK;
      public Collection constColsPK;

      private ForeignKeyInfo() {
         this.fk = null;
         this.toTable = null;
         this.cols = new LinkedList();
         this.pks = new LinkedList();
         this.consts = new LinkedList();
         this.constCols = new LinkedList();
         this.constsPK = new LinkedList();
         this.constColsPK = new LinkedList();
      }

      // $FF: synthetic method
      ForeignKeyInfo(Object x0) {
         this();
      }
   }

   public static class UniqueInfo {
      public Unique unq = null;
      public Collection cols = new LinkedList();
   }

   private static class IndexInfo {
      public Index index;
      public Collection cols;

      private IndexInfo() {
         this.index = null;
         this.cols = new LinkedList();
      }

      // $FF: synthetic method
      IndexInfo(Object x0) {
         this();
      }
   }

   private static class PrimaryKeyInfo {
      public PrimaryKey pk;
      public Collection cols;

      private PrimaryKeyInfo() {
         this.pk = null;
         this.cols = new LinkedList();
      }

      // $FF: synthetic method
      PrimaryKeyInfo(Object x0) {
         this();
      }
   }
}
