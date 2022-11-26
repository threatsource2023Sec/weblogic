package com.bea.xbean.values;

import com.bea.xbean.common.ValidationContext;
import com.bea.xbean.schema.BuiltinSchemaTypeSystem;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlObject;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class JavaIntegerHolder extends XmlObjectBase {
   private BigInteger _value;
   private static BigInteger _maxlong = BigInteger.valueOf(Long.MAX_VALUE);
   private static BigInteger _minlong = BigInteger.valueOf(Long.MIN_VALUE);

   public SchemaType schemaType() {
      return BuiltinSchemaTypeSystem.ST_INTEGER;
   }

   protected String compute_text(NamespaceManager nsm) {
      return this._value.toString();
   }

   protected void set_text(String s) {
      this.set_BigInteger(lex(s, _voorVc));
   }

   public static BigInteger lex(String s, ValidationContext vc) {
      if (s.length() > 0 && s.charAt(0) == '+') {
         s = s.substring(1);
      }

      try {
         return new BigInteger(s);
      } catch (Exception var3) {
         vc.invalid("integer", new Object[]{s});
         return null;
      }
   }

   protected void set_nil() {
      this._value = null;
   }

   public BigDecimal getBigDecimalValue() {
      this.check_dated();
      return this._value == null ? null : new BigDecimal(this._value);
   }

   public BigInteger getBigIntegerValue() {
      this.check_dated();
      return this._value;
   }

   protected void set_BigDecimal(BigDecimal v) {
      this._value = v.toBigInteger();
   }

   protected void set_BigInteger(BigInteger v) {
      this._value = v;
   }

   protected int compare_to(XmlObject i) {
      return ((SimpleValue)i).instanceType().getDecimalSize() > 1000000 ? -i.compareTo(this) : this._value.compareTo(((XmlObjectBase)i).bigIntegerValue());
   }

   protected boolean equal_to(XmlObject i) {
      return ((SimpleValue)i).instanceType().getDecimalSize() > 1000000 ? i.valueEquals(this) : this._value.equals(((XmlObjectBase)i).bigIntegerValue());
   }

   protected int value_hash_code() {
      if (this._value.compareTo(_maxlong) <= 0 && this._value.compareTo(_minlong) >= 0) {
         long longval = this._value.longValue();
         return (int)((longval >> 32) * 19L + longval);
      } else {
         return this._value.hashCode();
      }
   }
}
