package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.ValidationContext;
import org.apache.xmlbeans.impl.util.XsTypeConverter;

public abstract class JavaLongHolderEx extends JavaLongHolder {
   private SchemaType _schemaType;

   public JavaLongHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }

   protected void set_text(String s) {
      long v;
      try {
         v = XsTypeConverter.lexLong(s);
      } catch (Exception var5) {
         throw new XmlValueOutOfRangeException();
      }

      if (this._validateOnSet()) {
         validateValue(v, this._schemaType, _voorVc);
         validateLexical(s, this._schemaType, _voorVc);
      }

      super.set_long(v);
   }

   protected void set_long(long v) {
      if (this._validateOnSet()) {
         validateValue(v, this._schemaType, _voorVc);
      }

      super.set_long(v);
   }

   public static void validateLexical(String v, SchemaType sType, ValidationContext context) {
      JavaDecimalHolder.validateLexical(v, context);
      if (sType.hasPatternFacet() && !sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"long", v, QNameHelper.readable(sType)});
      }

   }

   private static void validateValue(long v, SchemaType sType, ValidationContext context) {
      XmlObject td = sType.getFacet(7);
      if (td != null) {
         long m = getLongValue(td);
         String temp = Long.toString(v);
         int len = temp.length();
         if (len > 0 && temp.charAt(0) == '-') {
            --len;
         }

         if ((long)len > m) {
            context.invalid("cvc-totalDigits-valid", new Object[]{new Integer(len), temp, new Long(m), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject mine = sType.getFacet(3);
      if (mine != null) {
         long m = getLongValue(mine);
         if (v <= m) {
            context.invalid("cvc-minExclusive-valid", new Object[]{"long", new Long(v), new Long(m), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject mini = sType.getFacet(4);
      if (mini != null) {
         long m = getLongValue(mini);
         if (v < m) {
            context.invalid("cvc-minInclusive-valid", new Object[]{"long", new Long(v), new Long(m), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject maxi = sType.getFacet(5);
      if (maxi != null) {
         long m = getLongValue(maxi);
         if (v > m) {
            context.invalid("cvc-maxInclusive-valid", new Object[]{"long", new Long(v), new Long(m), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject maxe = sType.getFacet(6);
      if (maxe != null) {
         long m = getLongValue(maxe);
         if (v >= m) {
            context.invalid("cvc-maxExclusive-valid", new Object[]{"long", new Long(v), new Long(m), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         for(int i = 0; i < vals.length; ++i) {
            if (v == getLongValue(vals[i])) {
               return;
            }
         }

         context.invalid("cvc-enumeration-valid", new Object[]{"long", new Long(v), QNameHelper.readable(sType)});
      }

   }

   private static long getLongValue(XmlObject o) {
      SchemaType s = o.schemaType();
      switch (s.getDecimalSize()) {
         case 64:
            return ((XmlObjectBase)o).getLongValue();
         case 1000000:
            return ((XmlObjectBase)o).getBigIntegerValue().longValue();
         case 1000001:
            return ((XmlObjectBase)o).getBigDecimalValue().longValue();
         default:
            throw new IllegalStateException("Bad facet type: " + s);
      }
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(lexical, this.schemaType(), ctx);
      validateValue(this.getLongValue(), this.schemaType(), ctx);
   }
}
