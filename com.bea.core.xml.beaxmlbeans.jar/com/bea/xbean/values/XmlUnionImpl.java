package com.bea.xbean.values;

import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.ValidationContext;
import com.bea.xbean.schema.SchemaTypeImpl;
import com.bea.xml.GDate;
import com.bea.xml.GDateSpecification;
import com.bea.xml.GDuration;
import com.bea.xml.GDurationSpecification;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlObject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;

public class XmlUnionImpl extends XmlObjectBase implements XmlAnySimpleType {
   private SchemaType _schemaType;
   private XmlAnySimpleType _value;
   private String _textvalue = "";
   private static final int JAVA_NUMBER = 47;
   private static final int JAVA_DATE = 48;
   private static final int JAVA_CALENDAR = 49;
   private static final int JAVA_BYTEARRAY = 50;
   private static final int JAVA_LIST = 51;

   public XmlUnionImpl(SchemaType type, boolean complex) {
      this._schemaType = type;
      this.initComplexType(complex, false);
   }

   public SchemaType schemaType() {
      return this._schemaType;
   }

   public SchemaType instanceType() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).instanceType();
   }

   protected String compute_text(NamespaceManager nsm) {
      return this._textvalue;
   }

   protected boolean is_defaultable_ws(String v) {
      try {
         XmlAnySimpleType savedValue = this._value;
         this.set_text(v);
         this._value = savedValue;
         return false;
      } catch (XmlValueOutOfRangeException var3) {
         return true;
      }
   }

   protected void set_text(String s) {
      if (!this._schemaType.matchPatternFacet(s) && this._validateOnSet()) {
         throw new XmlValueOutOfRangeException("cvc-datatype-valid.1.1", new Object[]{"string", s, QNameHelper.readable(this._schemaType)});
      } else {
         String original = this._textvalue;
         this._textvalue = s;
         SchemaType[] members = this._schemaType.getUnionConstituentTypes();

         assert members != null;

         boolean pushed = false;
         if (this.has_store()) {
            NamespaceContext.push(new NamespaceContext(this.get_store()));
            pushed = true;
         }

         try {
            for(boolean validate = true; validate || !this._validateOnSet(); validate = false) {
               for(int i = 0; i < members.length; ++i) {
                  try {
                     XmlAnySimpleType newval = ((SchemaTypeImpl)members[i]).newValue(s, validate);
                     if (check(newval, this._schemaType)) {
                        this._value = newval;
                        return;
                     }
                  } catch (XmlValueOutOfRangeException var12) {
                  } catch (Exception var13) {
                     throw new RuntimeException("Troublesome union exception caused by unexpected " + var13, var13);
                  }
               }

               if (!validate) {
                  break;
               }
            }
         } finally {
            if (pushed) {
               NamespaceContext.pop();
            }

         }

         this._textvalue = original;
         throw new XmlValueOutOfRangeException("cvc-datatype-valid.1.2.3", new Object[]{s, QNameHelper.readable(this._schemaType)});
      }
   }

   protected void set_nil() {
      this._value = null;
      this._textvalue = null;
   }

   protected int get_wscanon_rule() {
      return 1;
   }

   public float getFloatValue() {
      this.check_dated();
      return this._value == null ? 0.0F : ((SimpleValue)this._value).getFloatValue();
   }

   public double getDoubleValue() {
      this.check_dated();
      return this._value == null ? 0.0 : ((SimpleValue)this._value).getDoubleValue();
   }

   public BigDecimal getBigDecimalValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).getBigDecimalValue();
   }

   public BigInteger getBigIntegerValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).getBigIntegerValue();
   }

   public byte getByteValue() {
      this.check_dated();
      return this._value == null ? 0 : ((SimpleValue)this._value).getByteValue();
   }

   public short getShortValue() {
      this.check_dated();
      return this._value == null ? 0 : ((SimpleValue)this._value).getShortValue();
   }

   public int getIntValue() {
      this.check_dated();
      return this._value == null ? 0 : ((SimpleValue)this._value).getIntValue();
   }

   public long getLongValue() {
      this.check_dated();
      return this._value == null ? 0L : ((SimpleValue)this._value).getLongValue();
   }

   public byte[] getByteArrayValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).getByteArrayValue();
   }

   public boolean getBooleanValue() {
      this.check_dated();
      return this._value == null ? false : ((SimpleValue)this._value).getBooleanValue();
   }

   public Calendar getCalendarValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).getCalendarValue();
   }

   public Date getDateValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).getDateValue();
   }

   public GDate getGDateValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).getGDateValue();
   }

   public GDuration getGDurationValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).getGDurationValue();
   }

   public QName getQNameValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).getQNameValue();
   }

   public List getListValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).getListValue();
   }

   public List xgetListValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).xgetListValue();
   }

   public StringEnumAbstractBase getEnumValue() {
      this.check_dated();
      return this._value == null ? null : ((SimpleValue)this._value).getEnumValue();
   }

   public String getStringValue() {
      this.check_dated();
      return this._value == null ? null : this._value.getStringValue();
   }

   static boolean lexical_overlap(int source, int target) {
      if (source == target) {
         return true;
      } else if (source != 2 && target != 2 && source != 12 && target != 12 && source != 6 && target != 6) {
         switch (source) {
            case 3:
               switch (target) {
                  case 7:
                  case 8:
                     return true;
                  default:
                     return false;
               }
            case 4:
               switch (target) {
                  case 3:
                  case 5:
                  case 7:
                  case 8:
                  case 9:
                  case 10:
                  case 11:
                  case 13:
                  case 18:
                     return true;
                  case 4:
                  case 6:
                  case 12:
                  case 14:
                  case 15:
                  case 16:
                  case 17:
                  default:
                     return false;
               }
            case 5:
               switch (target) {
                  case 3:
                  case 4:
                  case 7:
                  case 8:
                  case 9:
                  case 10:
                  case 11:
                  case 18:
                     return true;
                  case 5:
                  case 6:
                  case 12:
                  case 13:
                  case 14:
                  case 15:
                  case 16:
                  case 17:
                  default:
                     return false;
               }
            case 6:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            default:
               return false;
            case 7:
            case 8:
               switch (target) {
                  case 3:
                  case 4:
                  case 5:
                  case 7:
                  case 8:
                  case 13:
                     return true;
                  case 6:
                  case 9:
                  case 10:
                  case 11:
                  case 12:
                  default:
                     return false;
               }
            case 9:
            case 10:
            case 11:
            case 18:
               switch (target) {
                  case 4:
                  case 5:
                  case 9:
                  case 10:
                  case 11:
                  case 18:
                     return true;
                  case 6:
                  case 7:
                  case 8:
                  case 12:
                  case 13:
                  case 14:
                  case 15:
                  case 16:
                  case 17:
                  default:
                     return false;
               }
            case 13:
               switch (target) {
                  case 4:
                  case 7:
                  case 8:
                     return true;
                  case 5:
                  case 6:
                  default:
                     return false;
               }
         }
      } else {
         return true;
      }
   }

   private static boolean logical_overlap(SchemaType type, int javacode) {
      assert type.getSimpleVariety() != 2;

      if (javacode <= 46) {
         if (type.getSimpleVariety() != 1) {
            return false;
         } else {
            return type.getPrimitiveType().getBuiltinTypeCode() == javacode;
         }
      } else {
         switch (javacode) {
            case 47:
               if (type.getSimpleVariety() != 1) {
                  return false;
               } else {
                  switch (type.getPrimitiveType().getBuiltinTypeCode()) {
                     case 9:
                     case 10:
                     case 11:
                     case 18:
                     case 20:
                     case 21:
                        return true;
                     case 12:
                     case 13:
                     case 14:
                     case 15:
                     case 16:
                     case 17:
                     case 19:
                     default:
                        return false;
                  }
               }
            case 48:
               if (type.getSimpleVariety() != 1) {
                  return false;
               } else {
                  switch (type.getPrimitiveType().getBuiltinTypeCode()) {
                     case 14:
                     case 16:
                        return true;
                     default:
                        return false;
                  }
               }
            case 49:
               if (type.getSimpleVariety() != 1) {
                  return false;
               } else {
                  switch (type.getPrimitiveType().getBuiltinTypeCode()) {
                     case 14:
                     case 15:
                     case 16:
                     case 17:
                     case 18:
                     case 19:
                     case 20:
                     case 21:
                        return true;
                     default:
                        return false;
                  }
               }
            case 50:
               if (type.getSimpleVariety() != 1) {
                  return false;
               } else {
                  switch (type.getPrimitiveType().getBuiltinTypeCode()) {
                     case 4:
                     case 5:
                        return true;
                     default:
                        return false;
                  }
               }
            case 51:
               return type.getSimpleVariety() == 3;
            default:
               assert false : "missing case";

               return false;
         }
      }
   }

   private void set_primitive(int typecode, Object val) {
      SchemaType[] members = this._schemaType.getUnionConstituentTypes();

      assert members != null;

      boolean pushed = false;
      if (this.has_store()) {
         NamespaceContext.push(new NamespaceContext(this.get_store()));
         pushed = true;
      }

      try {
         boolean validate = true;

         XmlAnySimpleType newval;
         label138:
         while(true) {
            if (!validate && this._validateOnSet()) {
               throw new XmlValueOutOfRangeException("cvc-datatype-valid.1.2.3", new Object[]{val.toString(), QNameHelper.readable(this._schemaType)});
            }

            for(int i = 0; i < members.length; ++i) {
               if (logical_overlap(members[i], typecode)) {
                  try {
                     newval = ((SchemaTypeImpl)members[i]).newValue(val, validate);
                     break label138;
                  } catch (XmlValueOutOfRangeException var13) {
                  } catch (Exception var14) {
                     assert false : "Unexpected " + var14;
                  }
               }
            }

            if (!validate) {
               throw new XmlValueOutOfRangeException("cvc-datatype-valid.1.2.3", new Object[]{val.toString(), QNameHelper.readable(this._schemaType)});
            }

            validate = false;
         }

         this._value = newval;
         this._textvalue = this._value.stringValue();
      } finally {
         if (pushed) {
            NamespaceContext.pop();
         }

      }
   }

   protected void set_boolean(boolean v) {
      this.set_primitive(3, new Boolean(v));
   }

   protected void set_byte(byte v) {
      this.set_primitive(47, new Byte(v));
   }

   protected void set_short(short v) {
      this.set_primitive(47, new Short(v));
   }

   protected void set_int(int v) {
      this.set_primitive(47, new Integer(v));
   }

   protected void set_long(long v) {
      this.set_primitive(47, new Long(v));
   }

   protected void set_float(float v) {
      this.set_primitive(47, new Float(v));
   }

   protected void set_double(double v) {
      this.set_primitive(47, new Double(v));
   }

   protected void set_ByteArray(byte[] b) {
      this.set_primitive(50, b);
   }

   protected void set_hex(byte[] b) {
      this.set_primitive(50, b);
   }

   protected void set_b64(byte[] b) {
      this.set_primitive(50, b);
   }

   protected void set_BigInteger(BigInteger v) {
      this.set_primitive(47, v);
   }

   protected void set_BigDecimal(BigDecimal v) {
      this.set_primitive(47, v);
   }

   protected void set_QName(QName v) {
      this.set_primitive(7, v);
   }

   protected void set_Calendar(Calendar c) {
      this.set_primitive(49, c);
   }

   protected void set_Date(Date d) {
      this.set_primitive(48, d);
   }

   protected void set_GDate(GDateSpecification d) {
      int btc = d.getBuiltinTypeCode();
      if (btc <= 0) {
         throw new XmlValueOutOfRangeException();
      } else {
         this.set_primitive(btc, d);
      }
   }

   protected void set_GDuration(GDurationSpecification d) {
      this.set_primitive(13, d);
   }

   protected void set_enum(StringEnumAbstractBase e) {
      this.set_primitive(12, e);
   }

   protected void set_list(List v) {
      this.set_primitive(51, v);
   }

   protected void set_xmlfloat(XmlObject v) {
      this.set_primitive(9, v);
   }

   protected void set_xmldouble(XmlObject v) {
      this.set_primitive(10, v);
   }

   protected void set_xmldecimal(XmlObject v) {
      this.set_primitive(11, v);
   }

   protected void set_xmlduration(XmlObject v) {
      this.set_primitive(13, v);
   }

   protected void set_xmldatetime(XmlObject v) {
      this.set_primitive(14, v);
   }

   protected void set_xmltime(XmlObject v) {
      this.set_primitive(15, v);
   }

   protected void set_xmldate(XmlObject v) {
      this.set_primitive(16, v);
   }

   protected void set_xmlgyearmonth(XmlObject v) {
      this.set_primitive(17, v);
   }

   protected void set_xmlgyear(XmlObject v) {
      this.set_primitive(18, v);
   }

   protected void set_xmlgmonthday(XmlObject v) {
      this.set_primitive(19, v);
   }

   protected void set_xmlgday(XmlObject v) {
      this.set_primitive(20, v);
   }

   protected void set_xmlgmonth(XmlObject v) {
      this.set_primitive(21, v);
   }

   private static boolean check(XmlObject v, SchemaType sType) {
      XmlObject[] vals = sType.getEnumerationValues();
      if (vals != null) {
         for(int i = 0; i < vals.length; ++i) {
            if (vals[i].valueEquals(v)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   protected boolean equal_to(XmlObject xmlobj) {
      return this._value.valueEquals(xmlobj);
   }

   protected int value_hash_code() {
      return this._value.hashCode();
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
      try {
         this.check_dated();
      } catch (Exception var4) {
         ctx.invalid("union", new Object[]{"'" + lexical + "' does not match any of the member types for " + QNameHelper.readable(this.schemaType())});
         return;
      }

      if (this._value == null) {
         ctx.invalid("union", new Object[]{"'" + lexical + "' does not match any of the member types for " + QNameHelper.readable(this.schemaType())});
      } else {
         ((XmlObjectBase)this._value).validate_simpleval(lexical, ctx);
      }
   }
}
