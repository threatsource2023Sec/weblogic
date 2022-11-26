package com.bea.xbean.schema;

import com.bea.xml.SchemaStringEnumEntry;

public class SchemaStringEnumEntryImpl implements SchemaStringEnumEntry {
   private String _string;
   private int _int;
   private String _enumName;

   public SchemaStringEnumEntryImpl(String str, int i, String enumName) {
      this._string = str;
      this._int = i;
      this._enumName = enumName;
   }

   public String getString() {
      return this._string;
   }

   public int getIntValue() {
      return this._int;
   }

   public String getEnumName() {
      return this._enumName;
   }
}
