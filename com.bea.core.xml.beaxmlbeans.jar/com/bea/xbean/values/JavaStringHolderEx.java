package com.bea.xbean.values;

import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.ValidationContext;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlObject;

public abstract class JavaStringHolderEx extends JavaStringHolder {
   private SchemaType _schemaType;

   public SchemaType schemaType() {
      return this._schemaType;
   }

   public JavaStringHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   protected int get_wscanon_rule() {
      return this.schemaType().getWhiteSpaceRule();
   }

   protected void set_text(String s) {
      if (this._validateOnSet()) {
         validateLexical(s, this._schemaType, _voorVc);
      }

      super.set_text(s);
   }

   protected boolean is_defaultable_ws(String v) {
      try {
         validateLexical(v, this._schemaType, _voorVc);
         return false;
      } catch (XmlValueOutOfRangeException var3) {
         return true;
      }
   }

   public static void validateLexical(String v, SchemaType sType, ValidationContext context) {
      if (!sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"string", v, QNameHelper.readable(sType)});
      } else {
         XmlObject len = sType.getFacet(0);
         if (len != null) {
            int m = ((XmlObjectBase)len).bigIntegerValue().intValue();
            if (v.length() != m) {
               context.invalid("cvc-length-valid.1.1", new Object[]{"string", new Integer(v.length()), new Integer(m), QNameHelper.readable(sType)});
               return;
            }
         }

         XmlObject min = sType.getFacet(1);
         if (min != null) {
            int m = ((XmlObjectBase)min).bigIntegerValue().intValue();
            if (v.length() < m) {
               context.invalid("cvc-minLength-valid.1.1", new Object[]{"string", new Integer(v.length()), new Integer(m), QNameHelper.readable(sType)});
               return;
            }
         }

         XmlObject max = sType.getFacet(2);
         if (max != null) {
            int m = ((XmlObjectBase)max).bigIntegerValue().intValue();
            if (v.length() > m) {
               context.invalid("cvc-maxLength-valid.1.1", new Object[]{"string", new Integer(v.length()), new Integer(m), QNameHelper.readable(sType)});
               return;
            }
         }

         XmlAnySimpleType[] vals = sType.getEnumerationValues();
         if (vals != null) {
            for(int i = 0; i < vals.length; ++i) {
               if (v.equals(vals[i].getStringValue())) {
                  return;
               }
            }

            context.invalid("cvc-enumeration-valid", new Object[]{"string", v, QNameHelper.readable(sType)});
         }

      }
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(this.stringValue(), this.schemaType(), ctx);
   }
}
