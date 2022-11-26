package org.apache.xmlbeans.impl.values;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.ValidationContext;
import org.apache.xmlbeans.impl.schema.BuiltinSchemaTypeSystem;
import org.apache.xmlbeans.impl.util.XsTypeConverter;

public abstract class JavaFloatHolder extends XmlObjectBase {
   private float _value;

   public SchemaType schemaType() {
      return BuiltinSchemaTypeSystem.ST_FLOAT;
   }

   protected String compute_text(NamespaceManager nsm) {
      return serialize(this._value);
   }

   public static String serialize(float f) {
      if (f == Float.POSITIVE_INFINITY) {
         return "INF";
      } else if (f == Float.NEGATIVE_INFINITY) {
         return "-INF";
      } else {
         return f == Float.NaN ? "NaN" : Float.toString(f);
      }
   }

   protected void set_text(String s) {
      this.set_float(validateLexical(s, _voorVc));
   }

   public static float validateLexical(String v, ValidationContext context) {
      try {
         return XsTypeConverter.lexFloat(v);
      } catch (NumberFormatException var3) {
         context.invalid("float", new Object[]{v});
         return Float.NaN;
      }
   }

   protected void set_nil() {
      this._value = 0.0F;
   }

   public BigDecimal getBigDecimalValue() {
      this.check_dated();
      return new BigDecimal((double)this._value);
   }

   public double getDoubleValue() {
      this.check_dated();
      return (double)this._value;
   }

   public float getFloatValue() {
      this.check_dated();
      return this._value;
   }

   protected void set_double(double v) {
      this.set_float((float)v);
   }

   protected void set_float(float v) {
      this._value = v;
   }

   protected void set_long(long v) {
      this.set_float((float)v);
   }

   protected void set_BigDecimal(BigDecimal v) {
      this.set_float(v.floatValue());
   }

   protected void set_BigInteger(BigInteger v) {
      this.set_float(v.floatValue());
   }

   protected int compare_to(XmlObject f) {
      return compare(this._value, ((XmlObjectBase)f).floatValue());
   }

   static int compare(float thisValue, float thatValue) {
      if (thisValue < thatValue) {
         return -1;
      } else if (thisValue > thatValue) {
         return 1;
      } else {
         int thisBits = Float.floatToIntBits(thisValue);
         int thatBits = Float.floatToIntBits(thatValue);
         return thisBits == thatBits ? 0 : (thisBits < thatBits ? -1 : 1);
      }
   }

   protected boolean equal_to(XmlObject f) {
      return compare(this._value, ((XmlObjectBase)f).floatValue()) == 0;
   }

   protected int value_hash_code() {
      return Float.floatToIntBits(this._value);
   }
}
