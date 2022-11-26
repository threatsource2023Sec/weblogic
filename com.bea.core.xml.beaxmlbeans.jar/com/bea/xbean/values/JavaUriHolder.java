package com.bea.xbean.values;

import com.bea.xbean.common.ValidationContext;
import com.bea.xbean.schema.BuiltinSchemaTypeSystem;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnyURI;
import com.bea.xml.XmlObject;

public abstract class JavaUriHolder extends XmlObjectBase {
   private String _value;

   public SchemaType schemaType() {
      return BuiltinSchemaTypeSystem.ST_ANY_URI;
   }

   public String compute_text(NamespaceManager nsm) {
      return this._value == null ? "" : this._value;
   }

   protected void set_text(String s) {
      if (this._validateOnSet()) {
         validateLexical(s, _voorVc);
      }

      this._value = s;
   }

   public static void validateLexical(String v, ValidationContext context) {
      if (v.startsWith("##")) {
         context.invalid("anyURI", new Object[]{v});
      }

   }

   protected void set_nil() {
      this._value = null;
   }

   protected boolean equal_to(XmlObject obj) {
      return this._value.equals(((XmlAnyURI)obj).getStringValue());
   }

   protected int value_hash_code() {
      return this._value.hashCode();
   }
}
