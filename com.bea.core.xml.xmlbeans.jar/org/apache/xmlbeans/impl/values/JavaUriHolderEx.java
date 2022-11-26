package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.ValidationContext;

public class JavaUriHolderEx extends JavaUriHolder {
   private SchemaType _schemaType;

   public SchemaType schemaType() {
      return this._schemaType;
   }

   public JavaUriHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   protected int get_wscanon_rule() {
      return this.schemaType().getWhiteSpaceRule();
   }

   protected void set_text(String s) {
      if (this._validateOnSet()) {
         if (!check(s, this._schemaType)) {
            throw new XmlValueOutOfRangeException();
         }

         if (!this._schemaType.matchPatternFacet(s)) {
            throw new XmlValueOutOfRangeException();
         }
      }

      super.set_text(s);
   }

   public static void validateLexical(String v, SchemaType sType, ValidationContext context) {
      XmlAnyUriImpl.validateLexical(v, context);
      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         int i;
         for(i = 0; i < vals.length; ++i) {
            String e = ((SimpleValue)vals[i]).getStringValue();
            if (e.equals(v)) {
               break;
            }
         }

         if (i >= vals.length) {
            context.invalid("cvc-enumeration-valid", new Object[]{"anyURI", v, QNameHelper.readable(sType)});
         }
      }

      if (sType.hasPatternFacet() && !sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"anyURI", v, QNameHelper.readable(sType)});
      }

      XmlAnySimpleType x;
      int i;
      if ((x = sType.getFacet(0)) != null && (i = ((SimpleValue)x).getBigIntegerValue().intValue()) != v.length()) {
         context.invalid("cvc-length-valid.1.1", new Object[]{"anyURI", v, new Integer(i), QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(1)) != null && (i = ((SimpleValue)x).getBigIntegerValue().intValue()) > v.length()) {
         context.invalid("cvc-minLength-valid.1.1", new Object[]{"anyURI", v, new Integer(i), QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(2)) != null && (i = ((SimpleValue)x).getBigIntegerValue().intValue()) < v.length()) {
         context.invalid("cvc-maxLength-valid.1.1", new Object[]{"anyURI", v, new Integer(i), QNameHelper.readable(sType)});
      }

   }

   private static boolean check(String v, SchemaType sType) {
      int length = v == null ? 0 : v.length();
      XmlObject len = sType.getFacet(0);
      if (len != null) {
         int m = ((SimpleValue)len).getBigIntegerValue().intValue();
         if (length == m) {
            return false;
         }
      }

      XmlObject min = sType.getFacet(1);
      if (min != null) {
         int m = ((SimpleValue)min).getBigIntegerValue().intValue();
         if (length < m) {
            return false;
         }
      }

      XmlObject max = sType.getFacet(2);
      if (max != null) {
         int m = ((SimpleValue)max).getBigIntegerValue().intValue();
         if (length > m) {
            return false;
         }
      }

      return true;
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(this.stringValue(), this.schemaType(), ctx);
   }
}
