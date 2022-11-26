package org.apache.openjpa.jdbc.schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.lib.meta.XMLMetaDataSerializer;
import org.apache.openjpa.lib.util.Localizer;
import org.xml.sax.SAXException;

public class XMLSchemaSerializer extends XMLMetaDataSerializer implements SchemaSerializer {
   private static final Localizer _loc = Localizer.forPackage(XMLSchemaSerializer.class);
   private final Collection _tables = new TreeSet();
   private final Collection _seqs = new TreeSet();

   public XMLSchemaSerializer(JDBCConfiguration conf) {
      this.setLog(conf.getLog("openjpa.jdbc.Schema"));
   }

   public Table[] getTables() {
      return (Table[])((Table[])this._tables.toArray(new Table[this._tables.size()]));
   }

   public void addTable(Table table) {
      if (table != null) {
         this._tables.add(table);
      }

   }

   public boolean removeTable(Table table) {
      return this._tables.remove(table);
   }

   public Sequence[] getSequences() {
      return (Sequence[])((Sequence[])this._seqs.toArray(new Sequence[this._seqs.size()]));
   }

   public void addSequence(Sequence seq) {
      if (seq != null) {
         this._seqs.add(seq);
      }

   }

   public boolean removeSequence(Sequence seq) {
      return this._seqs.remove(seq);
   }

   public void addAll(Schema schema) {
      if (schema != null) {
         Table[] tables = schema.getTables();

         for(int i = 0; i < tables.length; ++i) {
            this.addTable(tables[i]);
         }

         Sequence[] seqs = schema.getSequences();

         for(int i = 0; i < seqs.length; ++i) {
            this.addSequence(seqs[i]);
         }

      }
   }

   public void addAll(SchemaGroup group) {
      if (group != null) {
         Schema[] schemas = group.getSchemas();

         for(int i = 0; i < schemas.length; ++i) {
            this.addAll(schemas[i]);
         }

      }
   }

   public boolean removeAll(Schema schema) {
      if (schema == null) {
         return false;
      } else {
         boolean removed = false;
         Table[] tables = schema.getTables();

         for(int i = 0; i < tables.length; ++i) {
            removed |= this.removeTable(tables[i]);
         }

         Sequence[] seqs = schema.getSequences();

         for(int i = 0; i < seqs.length; ++i) {
            removed |= this.removeSequence(seqs[i]);
         }

         return removed;
      }
   }

   public boolean removeAll(SchemaGroup group) {
      if (group == null) {
         return false;
      } else {
         boolean removed = false;
         Schema[] schemas = group.getSchemas();

         for(int i = 0; i < schemas.length; ++i) {
            removed |= this.removeAll(schemas[i]);
         }

         return removed;
      }
   }

   public void clear() {
      this._tables.clear();
      this._seqs.clear();
   }

   protected Collection getObjects() {
      if (this._seqs.isEmpty()) {
         return this._tables;
      } else if (this._tables.isEmpty()) {
         return this._seqs;
      } else {
         List all = new ArrayList(this._seqs.size() + this._tables.size());
         all.addAll(this._seqs);
         all.addAll(this._tables);
         return all;
      }
   }

   protected void serialize(Collection objs) throws SAXException {
      Map schemas = new HashMap();

      Object schemaObjs;
      Object obj;
      for(Iterator itr = objs.iterator(); itr.hasNext(); ((Collection)schemaObjs).add(obj)) {
         obj = itr.next();
         String schemaName;
         if (obj instanceof Table) {
            schemaName = ((Table)obj).getSchemaName();
         } else {
            schemaName = ((Sequence)obj).getSchemaName();
         }

         schemaObjs = (Collection)schemas.get(schemaName);
         if (schemaObjs == null) {
            schemaObjs = new LinkedList();
            schemas.put(schemaName, schemaObjs);
         }
      }

      this.startElement("schemas");
      Iterator itr = schemas.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         this.serializeSchema((String)entry.getKey(), (Collection)entry.getValue());
      }

      this.endElement("schemas");
   }

   private void serializeSchema(String name, Collection objs) throws SAXException {
      if (!objs.isEmpty()) {
         if (this.getLog().isTraceEnabled()) {
            this.getLog().trace(_loc.get("ser-schema", (Object)name));
         }

         if (name != null) {
            this.addAttribute("name", name);
         }

         this.startElement("schema");
         Iterator itr = objs.iterator();

         while(itr.hasNext()) {
            Object obj = itr.next();
            if (obj instanceof Table) {
               this.serializeTable((Table)obj);
            } else {
               this.serializeSequence((Sequence)obj);
            }
         }

         this.endElement("schema");
      }
   }

   private void serializeSequence(Sequence seq) throws SAXException {
      this.addAttribute("name", seq.getName());
      if (seq.getInitialValue() != 1) {
         this.addAttribute("initial-value", String.valueOf(seq.getInitialValue()));
      }

      if (seq.getIncrement() > 1) {
         this.addAttribute("increment", String.valueOf(seq.getIncrement()));
      }

      if (seq.getAllocate() > 1) {
         this.addAttribute("allocate", String.valueOf(seq.getAllocate()));
      }

      this.startElement("sequence");
      this.endElement("sequence");
   }

   private void serializeTable(Table table) throws SAXException {
      this.addAttribute("name", table.getName());
      this.startElement("table");
      PrimaryKey pk = table.getPrimaryKey();
      if (pk != null) {
         this.serializePrimaryKey(pk);
      }

      Column[] cols = table.getColumns();

      for(int i = 0; i < cols.length; ++i) {
         this.serializeColumn(cols[i]);
      }

      ForeignKey[] fks = table.getForeignKeys();

      for(int i = 0; i < fks.length; ++i) {
         this.serializeForeignKey(fks[i]);
      }

      Index[] idxs = table.getIndexes();

      for(int i = 0; i < idxs.length; ++i) {
         this.serializeIndex(idxs[i]);
      }

      Unique[] unqs = table.getUniques();

      for(int i = 0; i < unqs.length; ++i) {
         this.serializeUnique(unqs[i]);
      }

      this.endElement("table");
   }

   private void serializeColumn(Column col) throws SAXException {
      this.addAttribute("name", col.getName());
      this.addAttribute("type", Schemas.getJDBCName(col.getType()));
      if (!StringUtils.isEmpty(col.getTypeName()) && !col.getTypeName().equalsIgnoreCase(Schemas.getJDBCName(col.getType()))) {
         this.addAttribute("type-name", col.getTypeName());
      }

      if (col.isNotNull()) {
         this.addAttribute("not-null", "true");
      }

      if (col.isAutoAssigned()) {
         this.addAttribute("auto-assign", "true");
      }

      if (col.getDefaultString() != null) {
         this.addAttribute("default", col.getDefaultString());
      }

      if (col.getSize() != 0) {
         this.addAttribute("size", String.valueOf(col.getSize()));
      }

      if (col.getDecimalDigits() != 0) {
         this.addAttribute("decimal-digits", String.valueOf(col.getDecimalDigits()));
      }

      this.startElement("column");
      this.endElement("column");
   }

   private void serializePrimaryKey(PrimaryKey pk) throws SAXException {
      if (pk.getName() != null) {
         this.addAttribute("name", pk.getName());
      }

      if (pk.isLogical()) {
         this.addAttribute("logical", "true");
      }

      Column[] cols = pk.getColumns();
      if (cols.length == 1) {
         this.addAttribute("column", cols[0].getName());
      }

      this.startElement("pk");
      if (cols.length > 1) {
         for(int i = 0; i < cols.length; ++i) {
            this.serializeOn(cols[i]);
         }
      }

      this.endElement("pk");
   }

   private void serializeIndex(Index idx) throws SAXException {
      this.addAttribute("name", idx.getName());
      if (idx.isUnique()) {
         this.addAttribute("unique", "true");
      }

      Column[] cols = idx.getColumns();
      if (cols.length == 1) {
         this.addAttribute("column", cols[0].getName());
      }

      this.startElement("index");
      if (cols.length > 1) {
         for(int i = 0; i < cols.length; ++i) {
            this.serializeOn(cols[i]);
         }
      }

      this.endElement("index");
   }

   private void serializeUnique(Unique unq) throws SAXException {
      if (unq.getName() != null) {
         this.addAttribute("name", unq.getName());
      }

      if (unq.isDeferred()) {
         this.addAttribute("deferred", "true");
      }

      Column[] cols = unq.getColumns();
      if (cols.length == 1) {
         this.addAttribute("column", cols[0].getName());
      }

      this.startElement("unique");
      if (cols.length > 1) {
         for(int i = 0; i < cols.length; ++i) {
            this.serializeOn(cols[i]);
         }
      }

      this.endElement("unique");
   }

   private void serializeForeignKey(ForeignKey fk) throws SAXException {
      if (fk.getName() != null) {
         this.addAttribute("name", fk.getName());
      }

      if (fk.isDeferred()) {
         this.addAttribute("deferred", "true");
      }

      if (fk.getDeleteAction() != 1) {
         this.addAttribute("delete-action", ForeignKey.getActionName(fk.getDeleteAction()));
      }

      if (fk.getUpdateAction() != 1 && fk.getUpdateAction() != 2) {
         this.addAttribute("update-action", ForeignKey.getActionName(fk.getUpdateAction()));
      }

      Column[] cols = fk.getColumns();
      Column[] pks = fk.getPrimaryKeyColumns();
      Column[] consts = fk.getConstantColumns();
      Column[] constsPK = fk.getConstantPrimaryKeyColumns();
      this.addAttribute("to-table", fk.getPrimaryKeyTable().getFullName());
      if (cols.length == 1 && consts.length == 0 && constsPK.length == 0) {
         this.addAttribute("column", cols[0].getName());
      }

      this.startElement("fk");
      int i;
      if (cols.length > 1 || consts.length > 0 || constsPK.length > 0) {
         for(i = 0; i < cols.length; ++i) {
            this.serializeJoin(cols[i], pks[i]);
         }
      }

      for(i = 0; i < consts.length; ++i) {
         this.serializeJoin(consts[i], fk.getConstant(consts[i]));
      }

      for(i = 0; i < constsPK.length; ++i) {
         this.serializeJoin(fk.getPrimaryKeyConstant(constsPK[i]), constsPK[i]);
      }

      this.endElement("fk");
   }

   private void serializeOn(Column col) throws SAXException {
      this.addAttribute("column", col.getName());
      this.startElement("on");
      this.endElement("on");
   }

   private void serializeJoin(Column col, Column pk) throws SAXException {
      this.addAttribute("column", col.getName());
      this.addAttribute("to-column", pk.getName());
      this.startElement("join");
      this.endElement("join");
   }

   private void serializeJoin(Object val, Column pk) throws SAXException {
      this.addAttribute("value", stringifyConstant(val));
      this.addAttribute("to-column", pk.getName());
      this.startElement("join");
      this.endElement("join");
   }

   private void serializeJoin(Column col, Object val) throws SAXException {
      this.addAttribute("column", col.getName());
      this.addAttribute("value", stringifyConstant(val));
      this.startElement("join");
      this.endElement("join");
   }

   private static String stringifyConstant(Object val) {
      if (val == null) {
         return "null";
      } else {
         return val instanceof String ? "'" + val + "'" : val.toString();
      }
   }
}
