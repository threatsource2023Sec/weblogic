package org.apache.openjpa.jdbc.schema;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SchemaGroup extends NameSet implements Cloneable {
   private Map _schemaMap = null;
   private Schema[] _schemas = null;

   public Schema[] getSchemas() {
      if (this._schemas == null) {
         this._schemas = this._schemaMap == null ? new Schema[0] : (Schema[])((Schema[])this._schemaMap.values().toArray(new Schema[this._schemaMap.size()]));
      }

      return this._schemas;
   }

   public Schema getSchema(String name) {
      if (this._schemaMap == null) {
         return null;
      } else {
         if (name != null) {
            name = name.toUpperCase();
         }

         return (Schema)this._schemaMap.get(name);
      }
   }

   public Schema addSchema() {
      return this.addSchema((String)null);
   }

   public Schema addSchema(String name) {
      this.addName(name, false);
      Schema schema = this.newSchema(name);
      if (name != null) {
         name = name.toUpperCase();
      }

      if (this._schemaMap == null) {
         this._schemaMap = new HashMap();
      }

      this._schemaMap.put(name, schema);
      this._schemas = null;
      return schema;
   }

   public boolean removeSchema(Schema schema) {
      if (schema == null) {
         return false;
      } else {
         String name = schema.getName();
         if (name != null) {
            name = name.toUpperCase();
         }

         Schema rem = (Schema)this._schemaMap.get(name);
         if (schema.equals(rem)) {
            this._schemaMap.remove(name);
            this.removeName(schema.getName());
            this._schemas = null;
            schema.remove();
            return true;
         } else {
            return false;
         }
      }
   }

   public Schema importSchema(Schema schema) {
      if (schema == null) {
         return null;
      } else {
         Schema copy = this.addSchema(schema.getName());
         Sequence[] seqs = schema.getSequences();

         for(int i = 0; i < seqs.length; ++i) {
            copy.importSequence(seqs[i]);
         }

         Table[] tables = schema.getTables();

         for(int i = 0; i < tables.length; ++i) {
            Table tab = copy.importTable(tables[i]);
            Index[] idxs = tables[i].getIndexes();

            int j;
            for(j = 0; j < idxs.length; ++j) {
               tab.importIndex(idxs[j]);
            }

            Unique[] unqs = tables[i].getUniques();

            for(j = 0; j < unqs.length; ++j) {
               tab.importUnique(unqs[j]);
            }
         }

         return copy;
      }
   }

   public boolean isKnownTable(Table table) {
      return this.findTable(table) != null;
   }

   public boolean isKnownTable(String name) {
      return this.findTable(name) != null;
   }

   public Table findTable(Table table) {
      return this.findTable(table.getFullName());
   }

   public Table findTable(String name) {
      if (name == null) {
         return null;
      } else {
         int dotIdx = name.indexOf(46);
         if (dotIdx != -1) {
            String schemaName = name.substring(0, dotIdx);
            name = name.substring(dotIdx + 1);
            Schema schema = this.getSchema(schemaName);
            if (schema != null) {
               return schema.getTable(name);
            }
         } else {
            Schema[] schemas = this.getSchemas();

            for(int i = 0; i < schemas.length; ++i) {
               Table tab = schemas[i].getTable(name);
               if (tab != null) {
                  return tab;
               }
            }
         }

         return null;
      }
   }

   public Table findTable(Schema inSchema, String name) {
      if (name == null) {
         return null;
      } else {
         int dotIdx = name.indexOf(46);
         if (dotIdx != -1) {
            String schemaName = name.substring(0, dotIdx);
            name = name.substring(dotIdx + 1);
            Schema schema = this.getSchema(schemaName);
            if (schema != null) {
               return schema.getTable(name);
            }
         } else {
            Schema[] schemas = this.getSchemas();

            for(int i = 0; i < schemas.length; ++i) {
               Table tab = schemas[i].getTable(name);
               if (tab != null && (schemas[i] == inSchema || inSchema.getName() == null)) {
                  return tab;
               }
            }
         }

         return null;
      }
   }

   public boolean isKnownSequence(Sequence seq) {
      return this.findSequence(seq) != null;
   }

   public boolean isKnownSequence(String name) {
      return this.findSequence(name) != null;
   }

   public Sequence findSequence(Sequence seq) {
      return this.findSequence(seq.getFullName());
   }

   public Sequence findSequence(String name) {
      if (name == null) {
         return null;
      } else {
         int dotIdx = name.indexOf(46);
         if (dotIdx != -1) {
            String schemaName = name.substring(0, dotIdx);
            name = name.substring(dotIdx + 1);
            Schema schema = this.getSchema(schemaName);
            if (schema != null) {
               return schema.getSequence(name);
            }
         } else {
            Schema[] schemas = this.getSchemas();

            for(int i = 0; i < schemas.length; ++i) {
               Sequence seq = schemas[i].getSequence(name);
               if (seq != null) {
                  return seq;
               }
            }
         }

         return null;
      }
   }

   public Sequence findSequence(Schema inSchema, String name) {
      if (name == null) {
         return null;
      } else {
         int dotIdx = name.indexOf(46);
         if (dotIdx != -1) {
            String schemaName = name.substring(0, dotIdx);
            name = name.substring(dotIdx + 1);
            Schema schema = this.getSchema(schemaName);
            if (schema != null) {
               return schema.getSequence(name);
            }
         } else {
            Schema[] schemas = this.getSchemas();

            for(int i = 0; i < schemas.length; ++i) {
               Sequence seq = schemas[i].getSequence(name);
               if (seq != null && (schemas[i] == inSchema || inSchema.getName() == null)) {
                  return seq;
               }
            }
         }

         return null;
      }
   }

   public ForeignKey[] findExportedForeignKeys(PrimaryKey pk) {
      if (pk == null) {
         return new ForeignKey[0];
      } else {
         Schema[] schemas = this.getSchemas();
         Collection exports = new LinkedList();

         for(int i = 0; i < schemas.length; ++i) {
            Table[] tabs = schemas[i].getTables();

            for(int j = 0; j < tabs.length; ++j) {
               ForeignKey[] fks = tabs[j].getForeignKeys();

               for(int k = 0; k < fks.length; ++k) {
                  if (fks[k].getPrimaryKeyTable() != null && pk.equals(fks[k].getPrimaryKeyTable().getPrimaryKey())) {
                     exports.add(fks[k]);
                  }
               }
            }
         }

         return (ForeignKey[])((ForeignKey[])exports.toArray(new ForeignKey[exports.size()]));
      }
   }

   public void removeUnusedComponents() {
      Schema[] schemas = this.getSchemas();

      for(int i = 0; i < schemas.length; ++i) {
         Sequence[] seqs = schemas[i].getSequences();

         int j;
         for(j = 0; j < seqs.length; ++j) {
            if (seqs[j].getRefCount() == 0) {
               schemas[i].removeSequence(seqs[j]);
            }
         }

         Table[] tabs = schemas[i].getTables();

         for(j = 0; j < tabs.length; ++j) {
            PrimaryKey pk = tabs[j].getPrimaryKey();
            ForeignKey[] fks = tabs[j].getForeignKeys();
            Column[] cols = tabs[j].getColumns();
            if (pk != null && pk.getRefCount() == 0) {
               tabs[j].removePrimaryKey();
            }

            int k;
            for(k = 0; k < fks.length; ++k) {
               if (fks[k].getRefCount() == 0) {
                  tabs[j].removeForeignKey(fks[k]);
               }
            }

            for(k = 0; k < cols.length; ++k) {
               if (cols[k].getRefCount() == 0) {
                  tabs[j].removeColumn(cols[k]);
               }
            }

            if (tabs[j].getColumns().length == 0) {
               schemas[i].removeTable(tabs[j]);
            }
         }

         if (schemas[i].getTables().length == 0) {
            this.removeSchema(schemas[i]);
         }
      }

   }

   public Object clone() {
      SchemaGroup clone = this.newInstance();
      clone.copy(this);
      return clone;
   }

   protected SchemaGroup newInstance() {
      return new SchemaGroup();
   }

   protected void copy(SchemaGroup group) {
      Schema[] schemas = group.getSchemas();

      for(int i = 0; i < schemas.length; ++i) {
         this.importSchema(schemas[i]);
      }

      for(int i = 0; i < schemas.length; ++i) {
         Table[] tabs = schemas[i].getTables();

         for(int j = 0; j < tabs.length; ++j) {
            ForeignKey[] fks = tabs[j].getForeignKeys();

            for(int k = 0; k < fks.length; ++k) {
               this.getSchema(schemas[i].getName()).getTable(tabs[j].getName()).importForeignKey(fks[k]);
            }
         }
      }

   }

   protected Schema newSchema(String name) {
      return new Schema(name, this);
   }

   protected Sequence newSequence(String name, Schema schema) {
      return new Sequence(name, schema);
   }

   protected Table newTable(String name, Schema schema) {
      return new Table(name, schema);
   }

   protected Column newColumn(String name, Table table) {
      return new Column(name, table);
   }

   protected PrimaryKey newPrimaryKey(String name, Table table) {
      return new PrimaryKey(name, table);
   }

   protected Index newIndex(String name, Table table) {
      return new Index(name, table);
   }

   protected Unique newUnique(String name, Table table) {
      return new Unique(name, table);
   }

   protected ForeignKey newForeignKey(String name, Table table) {
      return new ForeignKey(name, table);
   }
}
