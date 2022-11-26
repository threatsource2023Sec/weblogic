package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnyURI;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.ValidationContext;
import org.apache.xmlbeans.impl.schema.BuiltinSchemaTypeSystem;

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
