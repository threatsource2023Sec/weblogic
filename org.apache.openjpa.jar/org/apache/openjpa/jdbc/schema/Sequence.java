package org.apache.openjpa.jdbc.schema;

import java.io.File;
import org.apache.openjpa.lib.meta.SourceTracker;

public class Sequence extends ReferenceCounter implements Comparable, SourceTracker {
   private String _name = null;
   private String _fullName = null;
   private Schema _schema = null;
   private String _schemaName = null;
   private int _initial = 1;
   private int _increment = 1;
   private int _cache = 0;
   private File _source = null;
   private int _srcType = 0;

   public Sequence() {
   }

   public Sequence(String name, Schema schema) {
      this.setName(name);
      if (schema != null) {
         this.setSchemaName(schema.getName());
      }

      this._schema = schema;
   }

   void remove() {
      this._schema = null;
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

   public int getInitialValue() {
      return this._initial;
   }

   public void setInitialValue(int initial) {
      this._initial = initial;
   }

   public int getIncrement() {
      return this._increment;
   }

   public void setIncrement(int increment) {
      this._increment = increment;
   }

   public int getAllocate() {
      return this._cache;
   }

   public void setAllocate(int cache) {
      this._cache = cache;
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

   public int compareTo(Object other) {
      String name = this.getFullName();
      String otherName = ((Sequence)other).getFullName();
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
}
