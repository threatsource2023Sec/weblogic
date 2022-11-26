package org.apache.openjpa.jdbc.schema;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;

public class Schema implements Comparable, Serializable {
   private String _name = null;
   private SchemaGroup _group = null;
   private Map _tableMap = null;
   private Map _seqMap = null;
   private Table[] _tables = null;
   private Sequence[] _seqs = null;

   public Schema() {
   }

   public Schema(String name, SchemaGroup group) {
      this.setName(name);
      this._group = group;
   }

   void remove() {
      Table[] tabs = this.getTables();

      for(int i = 0; i < tabs.length; ++i) {
         this.removeTable(tabs[i]);
      }

      Sequence[] seqs = this.getSequences();

      for(int i = 0; i < seqs.length; ++i) {
         this.removeSequence(seqs[i]);
      }

      this._group = null;
   }

   public SchemaGroup getSchemaGroup() {
      return this._group;
   }

   public String getName() {
      return this._name;
   }

   public void setName(String name) {
      if (this.getSchemaGroup() != null) {
         throw new IllegalStateException();
      } else {
         this._name = StringUtils.trimToNull(name);
      }
   }

   public Table[] getTables() {
      if (this._tables == null) {
         this._tables = this._tableMap == null ? new Table[0] : (Table[])((Table[])this._tableMap.values().toArray(new Table[this._tableMap.size()]));
      }

      return this._tables;
   }

   public Table getTable(String name) {
      return name != null && this._tableMap != null ? (Table)this._tableMap.get(name.toUpperCase()) : null;
   }

   public Table addTable(String name) {
      SchemaGroup group = this.getSchemaGroup();
      Table tab;
      if (group != null) {
         group.addName(name, true);
         tab = group.newTable(name, this);
      } else {
         tab = new Table(name, this);
      }

      if (this._tableMap == null) {
         this._tableMap = Collections.synchronizedMap(new TreeMap());
      }

      this._tableMap.put(name.toUpperCase(), tab);
      this._tables = null;
      return tab;
   }

   public boolean removeTable(Table tab) {
      if (tab != null && this._tableMap != null) {
         Table cur = (Table)this._tableMap.get(tab.getName().toUpperCase());
         if (!cur.equals(tab)) {
            return false;
         } else {
            this._tableMap.remove(tab.getName().toUpperCase());
            this._tables = null;
            SchemaGroup group = this.getSchemaGroup();
            if (group != null) {
               group.removeName(tab.getName());
            }

            tab.remove();
            return true;
         }
      } else {
         return false;
      }
   }

   public Table importTable(Table table) {
      if (table == null) {
         return null;
      } else {
         Table copy = this.addTable(table.getName());
         Column[] cols = table.getColumns();

         for(int i = 0; i < cols.length; ++i) {
            copy.importColumn(cols[i]);
         }

         copy.importPrimaryKey(table.getPrimaryKey());
         return copy;
      }
   }

   public Sequence[] getSequences() {
      if (this._seqs == null) {
         this._seqs = this._seqMap == null ? new Sequence[0] : (Sequence[])((Sequence[])this._seqMap.values().toArray(new Sequence[this._seqMap.size()]));
      }

      return this._seqs;
   }

   public Sequence getSequence(String name) {
      return name != null && this._seqMap != null ? (Sequence)this._seqMap.get(name.toUpperCase()) : null;
   }

   public Sequence addSequence(String name) {
      SchemaGroup group = this.getSchemaGroup();
      Sequence seq;
      if (group != null) {
         group.addName(name, true);
         seq = group.newSequence(name, this);
      } else {
         seq = new Sequence(name, this);
      }

      if (this._seqMap == null) {
         this._seqMap = Collections.synchronizedMap(new TreeMap());
      }

      this._seqMap.put(name.toUpperCase(), seq);
      this._seqs = null;
      return seq;
   }

   public boolean removeSequence(Sequence seq) {
      if (seq != null && this._seqMap != null) {
         Sequence cur = (Sequence)this._seqMap.get(seq.getName().toUpperCase());
         if (!cur.equals(seq)) {
            return false;
         } else {
            this._seqMap.remove(seq.getName().toUpperCase());
            this._seqs = null;
            SchemaGroup group = this.getSchemaGroup();
            if (group != null) {
               group.removeName(seq.getName());
            }

            seq.remove();
            return true;
         }
      } else {
         return false;
      }
   }

   public Sequence importSequence(Sequence seq) {
      if (seq == null) {
         return null;
      } else {
         Sequence copy = this.addSequence(seq.getName());
         copy.setInitialValue(seq.getInitialValue());
         copy.setIncrement(seq.getIncrement());
         copy.setAllocate(seq.getAllocate());
         return copy;
      }
   }

   public int compareTo(Object other) {
      String name = this.getName();
      String otherName = ((Schema)other).getName();
      if (name == null && otherName == null) {
         return 0;
      } else if (name == null) {
         return 1;
      } else {
         return otherName == null ? -1 : name.compareTo(otherName);
      }
   }

   public String toString() {
      return this.getName();
   }
}
