package org.apache.xmlbeans.impl.values;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.PrefixResolver;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.ValidationContext;

public abstract class JavaQNameHolderEx extends JavaQNameHolder {
   private SchemaType _schemaType;

   public SchemaType schemaType() {
      return this._schemaType;
   }

   public JavaQNameHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   protected int get_wscanon_rule() {
      return this.schemaType().getWhiteSpaceRule();
   }

   protected void set_text(String s) {
      PrefixResolver resolver = NamespaceContext.getCurrent();
      if (resolver == null && this.has_store()) {
         resolver = this.get_store();
      }

      QName v;
      if (this._validateOnSet()) {
         v = validateLexical(s, this._schemaType, _voorVc, (PrefixResolver)resolver);
         if (v != null) {
            validateValue(v, this._schemaType, _voorVc);
         }
      } else {
         v = JavaQNameHolder.validateLexical(s, _voorVc, (PrefixResolver)resolver);
      }

      super.set_QName(v);
   }

   protected void set_QName(QName name) {
      if (this._validateOnSet()) {
         validateValue(name, this._schemaType, _voorVc);
      }

      super.set_QName(name);
   }

   protected void set_xmlanysimple(XmlAnySimpleType value) {
      QName v;
      if (this._validateOnSet()) {
         v = validateLexical(value.getStringValue(), this._schemaType, _voorVc, NamespaceContext.getCurrent());
         if (v != null) {
            validateValue(v, this._schemaType, _voorVc);
         }
      } else {
         v = JavaQNameHolder.validateLexical(value.getStringValue(), _voorVc, NamespaceContext.getCurrent());
      }

      super.set_QName(v);
   }

   public static QName validateLexical(String v, SchemaType sType, ValidationContext context, PrefixResolver resolver) {
      QName name = JavaQNameHolder.validateLexical(v, context, resolver);
      if (sType.hasPatternFacet() && !sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"QName", v, QNameHelper.readable(sType)});
      }

      return name;
   }

   public static void validateValue(QName v, SchemaType sType, ValidationContext context) {
      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         for(int i = 0; i < vals.length; ++i) {
            if (v.equals(((XmlObjectBase)vals[i]).getQNameValue())) {
               return;
            }
         }

         context.invalid("cvc-enumeration-valid", new Object[]{"QName", v, QNameHelper.readable(sType)});
      }

   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateValue(this.getQNameValue(), this.schemaType(), ctx);
   }
}
