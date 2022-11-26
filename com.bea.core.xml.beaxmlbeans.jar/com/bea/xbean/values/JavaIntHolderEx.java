package com.bea.xbean.values;

import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.ValidationContext;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlObject;

public abstract class JavaIntHolderEx extends JavaIntHolder {
   private SchemaType _schemaType;

   public JavaIntHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }

   protected void set_text(String s) {
      int v;
      try {
         v = XsTypeConverter.lexInt(s);
      } catch (Exception var4) {
         throw new XmlValueOutOfRangeException();
      }

      if (this._validateOnSet()) {
         validateValue(v, this._schemaType, _voorVc);
         validateLexical(s, this._schemaType, _voorVc);
      }

      super.set_int(v);
   }

   protected void set_int(int v) {
      if (this._validateOnSet()) {
         validateValue(v, this._schemaType, _voorVc);
      }

      super.set_int(v);
   }

   public static void validateLexical(String v, SchemaType sType, ValidationContext context) {
      JavaDecimalHolder.validateLexical(v, context);
      if (sType.hasPatternFacet() && !sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"int", v, QNameHelper.readable(sType)});
      }

   }

   private static void validateValue(int v, SchemaType sType, ValidationContext context) {
      XmlObject td = sType.getFacet(7);
      int m;
      int m;
      if (td != null) {
         String temp = Integer.toString(v);
         m = temp.length();
         if (m > 0 && temp.charAt(0) == '-') {
            --m;
         }

         m = getIntValue(td);
         if (m > m) {
            context.invalid("cvc-totalDigits-valid", new Object[]{new Integer(m), temp, new Integer(getIntValue(td)), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject mine = sType.getFacet(3);
      if (mine != null) {
         m = getIntValue(mine);
         if (v <= m) {
            context.invalid("cvc-minExclusive-valid", new Object[]{"int", new Integer(v), new Integer(m), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject mini = sType.getFacet(4);
      if (mini != null) {
         m = getIntValue(mini);
         if (v < m) {
            context.invalid("cvc-minInclusive-valid", new Object[]{"int", new Integer(v), new Integer(m), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject maxi = sType.getFacet(5);
      if (maxi != null) {
         int m = getIntValue(maxi);
         if (v > m) {
            context.invalid("cvc-maxExclusive-valid", new Object[]{"int", new Integer(v), new Integer(m), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject maxe = sType.getFacet(6);
      if (maxe != null) {
         int m = getIntValue(maxe);
         if (v >= m) {
            context.invalid("cvc-maxExclusive-valid", new Object[]{"int", new Integer(v), new Integer(m), QNameHelper.readable(sType)});
            return;
         }
      }

      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         for(int i = 0; i < vals.length; ++i) {
            if (v == getIntValue(vals[i])) {
               return;
            }
         }

         context.invalid("cvc-enumeration-valid", new Object[]{"int", new Integer(v), QNameHelper.readable(sType)});
      }

   }

   private static int getIntValue(XmlObject o) {
      SchemaType s = o.schemaType();
      switch (s.getDecimalSize()) {
         case 64:
            return (int)((XmlObjectBase)o).getLongValue();
         case 1000000:
            return ((XmlObjectBase)o).getBigIntegerValue().intValue();
         case 1000001:
            return ((XmlObjectBase)o).getBigDecimalValue().intValue();
         default:
            return ((XmlObjectBase)o).getIntValue();
      }
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(lexical, this.schemaType(), ctx);
      validateValue(this.getIntValue(), this.schemaType(), ctx);
   }
}
