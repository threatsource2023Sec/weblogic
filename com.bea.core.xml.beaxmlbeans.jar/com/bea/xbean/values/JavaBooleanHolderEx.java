package com.bea.xbean.values;

import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.ValidationContext;
import com.bea.xml.SchemaType;

public abstract class JavaBooleanHolderEx extends JavaBooleanHolder {
   private SchemaType _schemaType;

   public SchemaType schemaType() {
      return this._schemaType;
   }

   public static boolean validateLexical(String v, SchemaType sType, ValidationContext context) {
      boolean b = JavaBooleanHolder.validateLexical(v, context);
      validatePattern(v, sType, context);
      return b;
   }

   public static void validatePattern(String v, SchemaType sType, ValidationContext context) {
      if (!sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"boolean", v, QNameHelper.readable(sType)});
      }

   }

   public JavaBooleanHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   protected void set_text(String s) {
      if (this._validateOnSet()) {
         validatePattern(s, this._schemaType, _voorVc);
      }

      super.set_text(s);
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(lexical, this.schemaType(), ctx);
   }
}
