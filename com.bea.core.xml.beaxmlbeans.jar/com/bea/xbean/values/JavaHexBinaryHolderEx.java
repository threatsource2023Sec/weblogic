package com.bea.xbean.values;

import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.ValidationContext;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlObject;

public abstract class JavaHexBinaryHolderEx extends JavaHexBinaryHolder {
   private SchemaType _schemaType;

   public SchemaType schemaType() {
      return this._schemaType;
   }

   public JavaHexBinaryHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   protected int get_wscanon_rule() {
      return this.schemaType().getWhiteSpaceRule();
   }

   protected void set_text(String s) {
      byte[] v;
      if (this._validateOnSet()) {
         v = validateLexical(s, this.schemaType(), _voorVc);
      } else {
         v = lex(s, _voorVc);
      }

      if (this._validateOnSet() && v != null) {
         validateValue(v, this.schemaType(), XmlObjectBase._voorVc);
      }

      super.set_ByteArray(v);
      this._value = v;
   }

   protected void set_ByteArray(byte[] v) {
      if (this._validateOnSet()) {
         validateValue(v, this.schemaType(), _voorVc);
      }

      super.set_ByteArray(v);
   }

   public static void validateValue(byte[] v, SchemaType sType, ValidationContext context) {
      int i;
      XmlAnySimpleType o;
      if ((o = sType.getFacet(0)) != null && (i = ((XmlObjectBase)o).bigIntegerValue().intValue()) != v.length) {
         context.invalid("cvc-length-valid.1.2", new Object[]{"hexBinary", new Integer(v.length), new Integer(i), QNameHelper.readable(sType)});
      }

      if ((o = sType.getFacet(1)) != null && (i = ((XmlObjectBase)o).bigIntegerValue().intValue()) > v.length) {
         context.invalid("cvc-minLength-valid.1.2", new Object[]{"hexBinary", new Integer(v.length), new Integer(i), QNameHelper.readable(sType)});
      }

      if ((o = sType.getFacet(2)) != null && (i = ((XmlObjectBase)o).bigIntegerValue().intValue()) < v.length) {
         context.invalid("cvc-maxLength-valid.1.2", new Object[]{"hexBinary", new Integer(v.length), new Integer(i), QNameHelper.readable(sType)});
      }

      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         label47:
         for(i = 0; i < vals.length; ++i) {
            byte[] enumBytes = ((XmlObjectBase)vals[i]).byteArrayValue();
            if (enumBytes.length == v.length) {
               int j = 0;

               while(true) {
                  if (j >= enumBytes.length) {
                     break label47;
                  }

                  if (enumBytes[j] != v[j]) {
                     break;
                  }

                  ++j;
               }
            }
         }

         if (i >= vals.length) {
            context.invalid("cvc-enumeration-valid.b", new Object[]{"hexBinary", QNameHelper.readable(sType)});
         }
      }

   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(lexical, this.schemaType(), ctx);
      validateValue(this.byteArrayValue(), this.schemaType(), ctx);
   }
}
