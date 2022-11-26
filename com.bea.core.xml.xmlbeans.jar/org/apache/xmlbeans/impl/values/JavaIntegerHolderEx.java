package org.apache.xmlbeans.impl.values;

import java.math.BigInteger;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlPositiveInteger;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.ValidationContext;

public class JavaIntegerHolderEx extends JavaIntegerHolder {
   private SchemaType _schemaType;

   public JavaIntegerHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }

   protected void set_text(String s) {
      BigInteger v = lex(s, _voorVc);
      if (this._validateOnSet()) {
         validateValue(v, this._schemaType, _voorVc);
      }

      if (this._validateOnSet()) {
         validateLexical(s, this._schemaType, _voorVc);
      }

      super.set_BigInteger(v);
   }

   protected void set_BigInteger(BigInteger v) {
      if (this._validateOnSet()) {
         validateValue(v, this._schemaType, _voorVc);
      }

      super.set_BigInteger(v);
   }

   public static void validateLexical(String v, SchemaType sType, ValidationContext context) {
      JavaDecimalHolder.validateLexical(v, context);
      if (v.lastIndexOf(46) >= 0) {
         context.invalid("integer", new Object[]{v});
      }

      if (sType.hasPatternFacet() && !sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"integer", v, QNameHelper.readable(sType)});
      }

   }

   private static void validateValue(BigInteger v, SchemaType sType, ValidationContext context) {
      XmlPositiveInteger td = (XmlPositiveInteger)sType.getFacet(7);
      if (td != null) {
         String temp = v.toString();
         int len = temp.length();
         if (len > 0 && temp.charAt(0) == '-') {
            --len;
         }

         if (len > td.getBigIntegerValue().intValue()) {
            context.invalid("cvc-totalDigits-valid", new Object[]{new Integer(len), temp, new Integer(td.getBigIntegerValue().intValue()), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject mine = sType.getFacet(3);
      if (mine != null) {
         BigInteger m = getBigIntegerValue(mine);
         if (v.compareTo(m) <= 0) {
            context.invalid("cvc-minExclusive-valid", new Object[]{"integer", v, m, QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject mini = sType.getFacet(4);
      if (mini != null) {
         BigInteger m = getBigIntegerValue(mini);
         if (v.compareTo(m) < 0) {
            context.invalid("cvc-minInclusive-valid", new Object[]{"integer", v, m, QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject maxi = sType.getFacet(5);
      if (maxi != null) {
         BigInteger m = getBigIntegerValue(maxi);
         if (v.compareTo(m) > 0) {
            context.invalid("cvc-maxInclusive-valid", new Object[]{"integer", v, m, QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject maxe = sType.getFacet(6);
      if (maxe != null) {
         BigInteger m = getBigIntegerValue(maxe);
         if (v.compareTo(m) >= 0) {
            context.invalid("cvc-maxExclusive-valid", new Object[]{"integer", v, m, QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         for(int i = 0; i < vals.length; ++i) {
            if (v.equals(getBigIntegerValue(vals[i]))) {
               return;
            }
         }

         context.invalid("cvc-enumeration-valid", new Object[]{"integer", v, QNameHelper.readable(sType)});
      }

   }

   private static BigInteger getBigIntegerValue(XmlObject o) {
      SchemaType s = o.schemaType();
      switch (s.getDecimalSize()) {
         case 1000000:
            return ((XmlObjectBase)o).bigIntegerValue();
         case 1000001:
            return ((XmlObjectBase)o).bigDecimalValue().toBigInteger();
         default:
            throw new IllegalStateException("Bad facet type for Big Int: " + s);
      }
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(lexical, this.schemaType(), ctx);
      validateValue(this.getBigIntegerValue(), this.schemaType(), ctx);
   }
}
