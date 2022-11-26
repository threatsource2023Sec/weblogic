package org.apache.xmlbeans.impl.values;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.ValidationContext;
import org.apache.xmlbeans.impl.schema.BuiltinSchemaTypeSystem;
import org.apache.xmlbeans.impl.util.XsTypeConverter;

public abstract class JavaDoubleHolder extends XmlObjectBase {
   double _value;

   public SchemaType schemaType() {
      return BuiltinSchemaTypeSystem.ST_DOUBLE;
   }

   protected String compute_text(NamespaceManager nsm) {
      return serialize(this._value);
   }

   public static String serialize(double d) {
      if (d == Double.POSITIVE_INFINITY) {
         return "INF";
      } else if (d == Double.NEGATIVE_INFINITY) {
         return "-INF";
      } else {
         return d == Double.NaN ? "NaN" : Double.toString(d);
      }
   }

   protected void set_text(String s) {
      this.set_double(validateLexical(s, _voorVc));
   }

   public static double validateLexical(String v, ValidationContext context) {
      try {
         return XsTypeConverter.lexDouble(v);
      } catch (NumberFormatException var3) {
         context.invalid("double", new Object[]{v});
         return Double.NaN;
      }
   }

   protected void set_nil() {
      this._value = 0.0;
   }

   public BigDecimal getBigDecimalValue() {
      this.check_dated();
      return new BigDecimal(this._value);
   }

   public double getDoubleValue() {
      this.check_dated();
      return this._value;
   }

   public float getFloatValue() {
      this.check_dated();
      return (float)this._value;
   }

   protected void set_double(double v) {
      this._value = v;
   }

   protected void set_float(float v) {
      this.set_double((double)v);
   }

   protected void set_long(long v) {
      this.set_double((double)v);
   }

   protected void set_BigDecimal(BigDecimal v) {
      this.set_double(v.doubleValue());
   }

   protected void set_BigInteger(BigInteger v) {
      this.set_double(v.doubleValue());
   }

   protected int compare_to(XmlObject d) {
      return compare(this._value, ((XmlObjectBase)d).doubleValue());
   }

   static int compare(double thisValue, double thatValue) {
      if (thisValue < thatValue) {
         return -1;
      } else if (thisValue > thatValue) {
         return 1;
      } else {
         long thisBits = Double.doubleToLongBits(thisValue);
         long thatBits = Double.doubleToLongBits(thatValue);
         return thisBits == thatBits ? 0 : (thisBits < thatBits ? -1 : 1);
      }
   }

   protected boolean equal_to(XmlObject d) {
      return compare(this._value, ((XmlObjectBase)d).doubleValue()) == 0;
   }

   protected int value_hash_code() {
      long v = Double.doubleToLongBits(this._value);
      return (int)((v >> 32) * 19L + v);
   }
}
