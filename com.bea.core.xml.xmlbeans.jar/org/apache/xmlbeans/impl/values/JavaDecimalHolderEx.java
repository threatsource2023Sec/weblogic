package org.apache.xmlbeans.impl.values;

import java.math.BigDecimal;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.ValidationContext;

public abstract class JavaDecimalHolderEx extends JavaDecimalHolder {
   private SchemaType _schemaType;

   public SchemaType schemaType() {
      return this._schemaType;
   }

   public JavaDecimalHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   protected void set_text(String s) {
      if (this._validateOnSet()) {
         validateLexical(s, this._schemaType, _voorVc);
      }

      BigDecimal v = null;

      try {
         v = new BigDecimal(s);
      } catch (NumberFormatException var4) {
         _voorVc.invalid("decimal", new Object[]{s});
      }

      if (this._validateOnSet()) {
         validateValue(v, this._schemaType, _voorVc);
      }

      super.set_BigDecimal(v);
   }

   protected void set_BigDecimal(BigDecimal v) {
      if (this._validateOnSet()) {
         validateValue(v, this._schemaType, _voorVc);
      }

      super.set_BigDecimal(v);
   }

   public static void validateLexical(String v, SchemaType sType, ValidationContext context) {
      JavaDecimalHolder.validateLexical(v, context);
      if (sType.hasPatternFacet() && !sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"decimal", v, QNameHelper.readable(sType)});
      }

   }

   public static void validateValue(BigDecimal v, SchemaType sType, ValidationContext context) {
      XmlObject fd = sType.getFacet(8);
      if (fd != null) {
         int scale = ((XmlObjectBase)fd).getBigIntegerValue().intValue();

         try {
            v.setScale(scale);
         } catch (ArithmeticException var12) {
            context.invalid("cvc-fractionDigits-valid", new Object[]{new Integer(v.scale()), v.toString(), new Integer(scale), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject td = sType.getFacet(7);
      int i;
      if (td != null) {
         String temp = v.unscaledValue().toString();
         int tdf = ((XmlObjectBase)td).getBigIntegerValue().intValue();
         int origLen = temp.length();
         int len = origLen;
         if (origLen > 0) {
            if (temp.charAt(0) == '-') {
               len = origLen - 1;
            }

            int insignificantTrailingZeros = 0;
            i = v.scale();

            for(int j = origLen - 1; temp.charAt(j) == '0' && j > 0 && insignificantTrailingZeros < i; --j) {
               ++insignificantTrailingZeros;
            }

            len -= insignificantTrailingZeros;
         }

         if (len > tdf) {
            context.invalid("cvc-totalDigits-valid", new Object[]{new Integer(len), v.toString(), new Integer(tdf), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject mine = sType.getFacet(3);
      if (mine != null) {
         BigDecimal m = ((XmlObjectBase)mine).getBigDecimalValue();
         if (v.compareTo(m) <= 0) {
            context.invalid("cvc-minExclusive-valid", new Object[]{"decimal", v, m, QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject mini = sType.getFacet(4);
      if (mini != null) {
         BigDecimal m = ((XmlObjectBase)mini).getBigDecimalValue();
         if (v.compareTo(m) < 0) {
            context.invalid("cvc-minInclusive-valid", new Object[]{"decimal", v, m, QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject maxi = sType.getFacet(5);
      if (maxi != null) {
         BigDecimal m = ((XmlObjectBase)maxi).getBigDecimalValue();
         if (v.compareTo(m) > 0) {
            context.invalid("cvc-maxInclusive-valid", new Object[]{"decimal", v, m, QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject maxe = sType.getFacet(6);
      if (maxe != null) {
         BigDecimal m = ((XmlObjectBase)maxe).getBigDecimalValue();
         if (v.compareTo(m) >= 0) {
            context.invalid("cvc-maxExclusive-valid", new Object[]{"decimal", v, m, QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         for(i = 0; i < vals.length; ++i) {
            if (v.equals(((XmlObjectBase)vals[i]).getBigDecimalValue())) {
               return;
            }
         }

         context.invalid("cvc-enumeration-valid", new Object[]{"decimal", v, QNameHelper.readable(sType)});
      }

   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(lexical, this.schemaType(), ctx);
      validateValue(this.getBigDecimalValue(), this.schemaType(), ctx);
   }
}
