package org.apache.xmlbeans.impl.values;

import org.apache.xmlbeans.GDuration;
import org.apache.xmlbeans.GDurationSpecification;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.ValidationContext;

public abstract class JavaGDurationHolderEx extends XmlObjectBase {
   GDuration _value;
   private SchemaType _schemaType;

   public JavaGDurationHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }

   protected void set_text(String s) {
      GDuration newVal;
      if (this._validateOnSet()) {
         newVal = validateLexical(s, this._schemaType, _voorVc);
      } else {
         newVal = lex(s, _voorVc);
      }

      if (this._validateOnSet() && newVal != null) {
         validateValue(newVal, this._schemaType, _voorVc);
      }

      this._value = newVal;
   }

   protected void set_GDuration(GDurationSpecification v) {
      if (this._validateOnSet()) {
         validateValue(v, this._schemaType, _voorVc);
      }

      if (v.isImmutable() && v instanceof GDuration) {
         this._value = (GDuration)v;
      } else {
         this._value = new GDuration(v);
      }

   }

   protected String compute_text(NamespaceManager nsm) {
      return this._value == null ? "" : this._value.toString();
   }

   protected void set_nil() {
      this._value = null;
   }

   public GDuration getGDurationValue() {
      this.check_dated();
      return this._value == null ? null : this._value;
   }

   public static GDuration lex(String v, ValidationContext context) {
      GDuration duration = null;

      try {
         duration = new GDuration(v);
      } catch (Exception var4) {
         context.invalid("duration", new Object[]{v});
      }

      return duration;
   }

   public static GDuration validateLexical(String v, SchemaType sType, ValidationContext context) {
      GDuration duration = lex(v, context);
      if (duration != null && sType.hasPatternFacet() && !sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"duration", v, QNameHelper.readable(sType)});
      }

      return duration;
   }

   public static void validateValue(GDurationSpecification v, SchemaType sType, ValidationContext context) {
      XmlAnySimpleType x;
      GDuration g;
      if ((x = sType.getFacet(3)) != null && v.compareToGDuration(g = ((XmlObjectBase)x).gDurationValue()) <= 0) {
         context.invalid("cvc-minExclusive-valid", new Object[]{"duration", v, g, QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(4)) != null && v.compareToGDuration(g = ((XmlObjectBase)x).gDurationValue()) < 0) {
         context.invalid("cvc-minInclusive-valid", new Object[]{"duration", v, g, QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(6)) != null && v.compareToGDuration(g = ((XmlObjectBase)x).gDurationValue()) >= 0) {
         context.invalid("cvc-maxExclusive-valid", new Object[]{"duration", v, g, QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(5)) != null && v.compareToGDuration(g = ((XmlObjectBase)x).gDurationValue()) > 0) {
         context.invalid("cvc-maxInclusive-valid", new Object[]{"duration", v, g, QNameHelper.readable(sType)});
      }

      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         for(int i = 0; i < vals.length; ++i) {
            if (v.compareToGDuration(((XmlObjectBase)vals[i]).gDurationValue()) == 0) {
               return;
            }
         }

         context.invalid("cvc-enumeration-valid", new Object[]{"duration", v, QNameHelper.readable(sType)});
      }

   }

   protected int compare_to(XmlObject d) {
      return this._value.compareToGDuration(((XmlObjectBase)d).gDurationValue());
   }

   protected boolean equal_to(XmlObject d) {
      return this._value.equals(((XmlObjectBase)d).gDurationValue());
   }

   protected int value_hash_code() {
      return this._value.hashCode();
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(lexical, this.schemaType(), ctx);
      validateValue(this.gDurationValue(), this.schemaType(), ctx);
   }
}
