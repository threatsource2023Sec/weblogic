package com.bea.xbean.values;

import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.ValidationContext;
import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;

public abstract class JavaStringEnumerationHolderEx extends JavaStringHolderEx {
   private StringEnumAbstractBase _val;

   public JavaStringEnumerationHolderEx(SchemaType type, boolean complex) {
      super(type, complex);
   }

   protected void set_text(String s) {
      StringEnumAbstractBase se = this.schemaType().enumForString(s);
      if (se == null) {
         throw new XmlValueOutOfRangeException("cvc-enumeration-valid", new Object[]{"string", s, QNameHelper.readable(this.schemaType())});
      } else {
         super.set_text(s);
         this._val = se;
      }
   }

   public static void validateLexical(String v, SchemaType sType, ValidationContext context) {
      JavaStringHolderEx.validateLexical(v, sType, context);
   }

   protected void set_nil() {
      this._val = null;
      super.set_nil();
   }

   public StringEnumAbstractBase getEnumValue() {
      this.check_dated();
      return this._val;
   }

   protected void set_enum(StringEnumAbstractBase se) {
      Class ejc = this.schemaType().getEnumJavaClass();
      if (ejc != null && !se.getClass().equals(ejc)) {
         throw new XmlValueOutOfRangeException();
      } else {
         super.set_text(se.toString());
         this._val = se;
      }
   }
}
