package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.schema.BuiltinSchemaTypeSystem;

public class JavaStringHolder extends XmlObjectBase {
   private String _value;

   public SchemaType schemaType() {
      return BuiltinSchemaTypeSystem.ST_STRING;
   }

   protected int get_wscanon_rule() {
      return 1;
   }

   public String compute_text(NamespaceManager nsm) {
      return this._value;
   }

   protected void set_text(String s) {
      this._value = s;
   }

   protected void set_nil() {
      this._value = null;
   }

   protected boolean equal_to(XmlObject obj) {
      return this._value.equals(((XmlObjectBase)obj).stringValue());
   }

   protected int value_hash_code() {
      return this._value.hashCode();
   }

   protected boolean is_defaultable_ws(String v) {
      return false;
   }
}
