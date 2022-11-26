package com.bea.xbean.values;

import com.bea.xbean.common.ValidationContext;
import com.bea.xbean.schema.BuiltinSchemaTypeSystem;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlObject;

public abstract class JavaBooleanHolder extends XmlObjectBase {
   private boolean _value;

   public SchemaType schemaType() {
      return BuiltinSchemaTypeSystem.ST_BOOLEAN;
   }

   protected String compute_text(NamespaceManager nsm) {
      return this._value ? "true" : "false";
   }

   protected void set_text(String s) {
      this._value = validateLexical(s, _voorVc);
   }

   public static boolean validateLexical(String v, ValidationContext context) {
      if (!v.equals("true") && !v.equals("1")) {
         if (!v.equals("false") && !v.equals("0")) {
            context.invalid("boolean", new Object[]{v});
            return false;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   protected void set_nil() {
      this._value = false;
   }

   public boolean getBooleanValue() {
      this.check_dated();
      return this._value;
   }

   protected void set_boolean(boolean f) {
      this._value = f;
   }

   protected int compare_to(XmlObject i) {
      return this._value == ((XmlBoolean)i).getBooleanValue() ? 0 : 2;
   }

   protected boolean equal_to(XmlObject i) {
      return this._value == ((XmlBoolean)i).getBooleanValue();
   }

   protected int value_hash_code() {
      return this._value ? 957379554 : 676335975;
   }
}
