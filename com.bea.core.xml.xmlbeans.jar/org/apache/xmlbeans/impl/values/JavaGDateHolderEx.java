package org.apache.xmlbeans.impl.values;

import java.util.Calendar;
import java.util.Date;
import org.apache.xmlbeans.GDate;
import org.apache.xmlbeans.GDateBuilder;
import org.apache.xmlbeans.GDateSpecification;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.ValidationContext;

public abstract class JavaGDateHolderEx extends XmlObjectBase {
   private SchemaType _schemaType;
   private GDate _value;

   public JavaGDateHolderEx(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }

   protected String compute_text(NamespaceManager nsm) {
      return this._value == null ? "" : this._value.toString();
   }

   protected void set_text(String s) {
      GDate newVal;
      if (this._validateOnSet()) {
         newVal = validateLexical(s, this._schemaType, _voorVc);
      } else {
         newVal = lex(s, this._schemaType, _voorVc);
      }

      if (this._validateOnSet() && newVal != null) {
         validateValue(newVal, this._schemaType, _voorVc);
      }

      this._value = newVal;
   }

   public static GDate lex(String v, SchemaType sType, ValidationContext context) {
      GDate date = null;

      try {
         date = new GDate(v);
      } catch (Exception var5) {
         context.invalid("date", new Object[]{v});
      }

      if (date != null) {
         if (date.getBuiltinTypeCode() != sType.getPrimitiveType().getBuiltinTypeCode()) {
            context.invalid("date", new Object[]{"wrong type: " + v});
            date = null;
         } else if (!date.isValid()) {
            context.invalid("date", new Object[]{v});
            date = null;
         }
      }

      return date;
   }

   public static GDate validateLexical(String v, SchemaType sType, ValidationContext context) {
      GDate date = lex(v, sType, context);
      if (date != null && sType.hasPatternFacet() && !sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1", new Object[]{"date", v, QNameHelper.readable(sType)});
      }

      return date;
   }

   public static void validateValue(GDateSpecification v, SchemaType sType, ValidationContext context) {
      if (v.getBuiltinTypeCode() != sType.getPrimitiveType().getBuiltinTypeCode()) {
         context.invalid("date", new Object[]{"Date (" + v + ") does not have the set of fields required for " + QNameHelper.readable(sType)});
      }

      XmlAnySimpleType x;
      GDate g;
      if ((x = sType.getFacet(3)) != null && v.compareToGDate(g = ((XmlObjectBase)x).gDateValue()) <= 0) {
         context.invalid("cvc-minExclusive-valid", new Object[]{"date", v, g, QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(4)) != null && v.compareToGDate(g = ((XmlObjectBase)x).gDateValue()) < 0) {
         context.invalid("cvc-minInclusive-valid", new Object[]{"date", v, g, QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(6)) != null && v.compareToGDate(g = ((XmlObjectBase)x).gDateValue()) >= 0) {
         context.invalid("cvc-maxExclusive-valid", new Object[]{"date", v, g, QNameHelper.readable(sType)});
      }

      if ((x = sType.getFacet(5)) != null && v.compareToGDate(g = ((XmlObjectBase)x).gDateValue()) > 0) {
         context.invalid("cvc-maxInclusive-valid", new Object[]{"date", v, g, QNameHelper.readable(sType)});
      }

      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         for(int i = 0; i < vals.length; ++i) {
            if (v.compareToGDate(((XmlObjectBase)vals[i]).gDateValue()) == 0) {
               return;
            }
         }

         context.invalid("cvc-enumeration-valid", new Object[]{"date", v, QNameHelper.readable(sType)});
      }

   }

   protected void set_nil() {
      this._value = null;
   }

   public int getIntValue() {
      int code = this.schemaType().getPrimitiveType().getBuiltinTypeCode();
      if (code != 20 && code != 21 && code != 18) {
         throw new XmlValueOutOfRangeException();
      } else {
         this.check_dated();
         if (this._value == null) {
            return 0;
         } else {
            switch (code) {
               case 18:
                  return this._value.getYear();
               case 19:
               default:
                  assert false;

                  throw new IllegalStateException();
               case 20:
                  return this._value.getDay();
               case 21:
                  return this._value.getMonth();
            }
         }
      }
   }

   public GDate getGDateValue() {
      this.check_dated();
      return this._value == null ? null : this._value;
   }

   public Calendar getCalendarValue() {
      this.check_dated();
      return this._value == null ? null : this._value.getCalendar();
   }

   public Date getDateValue() {
      this.check_dated();
      return this._value == null ? null : this._value.getDate();
   }

   protected void set_int(int v) {
      int code = this.schemaType().getPrimitiveType().getBuiltinTypeCode();
      if (code != 20 && code != 21 && code != 18) {
         throw new XmlValueOutOfRangeException();
      } else {
         GDateBuilder value = new GDateBuilder();
         switch (code) {
            case 18:
               value.setYear(v);
            case 19:
            default:
               break;
            case 20:
               value.setDay(v);
               break;
            case 21:
               value.setMonth(v);
         }

         if (this._validateOnSet()) {
            validateValue(value, this._schemaType, _voorVc);
         }

         this._value = value.toGDate();
      }
   }

   protected void set_GDate(GDateSpecification v) {
      int code = this.schemaType().getPrimitiveType().getBuiltinTypeCode();
      GDate candidate;
      if (((GDateSpecification)v).isImmutable() && v instanceof GDate && ((GDateSpecification)v).getBuiltinTypeCode() == code) {
         candidate = (GDate)v;
      } else {
         if (((GDateSpecification)v).getBuiltinTypeCode() != code) {
            GDateBuilder gDateBuilder = new GDateBuilder((GDateSpecification)v);
            gDateBuilder.setBuiltinTypeCode(code);
            v = gDateBuilder;
         }

         candidate = new GDate((GDateSpecification)v);
      }

      if (this._validateOnSet()) {
         validateValue(candidate, this._schemaType, _voorVc);
      }

      this._value = candidate;
   }

   protected void set_Calendar(Calendar c) {
      int code = this.schemaType().getPrimitiveType().getBuiltinTypeCode();
      GDateBuilder gDateBuilder = new GDateBuilder(c);
      gDateBuilder.setBuiltinTypeCode(code);
      GDate value = gDateBuilder.toGDate();
      if (this._validateOnSet()) {
         validateValue(value, this._schemaType, _voorVc);
      }

      this._value = value;
   }

   protected void set_Date(Date v) {
      int code = this.schemaType().getPrimitiveType().getBuiltinTypeCode();
      if ((code == 16 || code == 14) && v != null) {
         GDateBuilder gDateBuilder = new GDateBuilder(v);
         gDateBuilder.setBuiltinTypeCode(code);
         GDate value = gDateBuilder.toGDate();
         if (this._validateOnSet()) {
            validateValue(value, this._schemaType, _voorVc);
         }

         this._value = value;
      } else {
         throw new XmlValueOutOfRangeException();
      }
   }

   protected int compare_to(XmlObject obj) {
      return this._value.compareToGDate(((XmlObjectBase)obj).gDateValue());
   }

   protected boolean equal_to(XmlObject obj) {
      return this._value.equals(((XmlObjectBase)obj).gDateValue());
   }

   protected int value_hash_code() {
      return this._value.hashCode();
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      validateLexical(lexical, this.schemaType(), ctx);
      validateValue(this.gDateValue(), this.schemaType(), ctx);
   }
}
