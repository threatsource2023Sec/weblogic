package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.ValidationContext;

public abstract class JavaFloatHolderEx extends JavaFloatHolder {
   private SchemaType _schemaType;

   public JavaFloatHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }

   protected void set_float(float v) {
      if (this._validateOnSet()) {
         validateValue(v, this._schemaType, _voorVc);
      }

      super.set_float(v);
   }

   public static float validateLexical(String v, SchemaType sType, ValidationContext context) {
      float f = JavaFloatHolder.validateLexical(v, context);
      if (!sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"float", v, QNameHelper.readable(sType)});
      }

      return f;
   }

   public static void validateValue(float v, SchemaType sType, ValidationContext context) {
      XmlAnySimpleType x;
      float f;
      if ((x = sType.getFacet(3)) != null && compare(v, f = ((XmlObjectBase)x).floatValue()) <= 0) {
         context.invalid("cvc-minExclusive-valid", new Object[]{"float", new Float(v), new Float(f), QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(4)) != null && compare(v, f = ((XmlObjectBase)x).floatValue()) < 0) {
         context.invalid("cvc-minInclusive-valid", new Object[]{"float", new Float(v), new Float(f), QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(5)) != null && compare(v, f = ((XmlObjectBase)x).floatValue()) > 0) {
         context.invalid("cvc-maxInclusive-valid", new Object[]{"float", new Float(v), new Float(f), QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(6)) != null && compare(v, f = ((XmlObjectBase)x).floatValue()) >= 0) {
         context.invalid("cvc-maxExclusive-valid", new Object[]{"float", new Float(v), new Float(f), QNameHelper.readable(sType)});
      }

      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         for(int i = 0; i < vals.length; ++i) {
            if (compare(v, ((XmlObjectBase)vals[i]).floatValue()) == 0) {
               return;
            }
         }

         context.invalid("cvc-enumeration-valid", new Object[]{"float", new Float(v), QNameHelper.readable(sType)});
      }

   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(lexical, this.schemaType(), ctx);
      validateValue(this.floatValue(), this.schemaType(), ctx);
   }
}
