package com.bea.xbean.values;

import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.ValidationContext;
import com.bea.xbean.schema.BuiltinSchemaTypeSystem;
import com.bea.xbean.util.Base64;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlBase64Binary;
import com.bea.xml.XmlObject;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public abstract class JavaBase64Holder extends XmlObjectBase {
   protected byte[] _value;
   protected boolean _hashcached = false;
   protected int hashcode = 0;
   protected static MessageDigest md5;

   public SchemaType schemaType() {
      return BuiltinSchemaTypeSystem.ST_BASE_64_BINARY;
   }

   protected String compute_text(NamespaceManager nsm) {
      return new String(Base64.encode(this._value));
   }

   protected void set_text(String s) {
      this._hashcached = false;
      if (this._validateOnSet()) {
         this._value = validateLexical(s, this.schemaType(), _voorVc);
      } else {
         this._value = lex(s, _voorVc);
      }

   }

   protected void set_nil() {
      this._hashcached = false;
      this._value = null;
   }

   public static byte[] lex(String v, ValidationContext c) {
      byte[] vBytes = null;

      try {
         vBytes = v.getBytes("UTF-8");
      } catch (UnsupportedEncodingException var4) {
      }

      byte[] bytes = Base64.decode(vBytes);
      if (bytes == null) {
         c.invalid("base64Binary", new Object[]{"not encoded properly"});
      }

      return bytes;
   }

   public static byte[] validateLexical(String v, SchemaType sType, ValidationContext context) {
      byte[] bytes = lex(v, context);
      if (bytes == null) {
         return null;
      } else if (!sType.matchPatternFacet(v)) {
         context.invalid("cvc-datatype-valid.1.1b", new Object[]{"base 64", QNameHelper.readable(sType)});
         return null;
      } else {
         return bytes;
      }
   }

   public byte[] getByteArrayValue() {
      this.check_dated();
      if (this._value == null) {
         return null;
      } else {
         byte[] result = new byte[this._value.length];
         System.arraycopy(this._value, 0, result, 0, this._value.length);
         return result;
      }
   }

   protected void set_ByteArray(byte[] ba) {
      this._hashcached = false;
      this._value = new byte[ba.length];
      System.arraycopy(ba, 0, this._value, 0, ba.length);
   }

   protected boolean equal_to(XmlObject i) {
      byte[] ival = ((XmlBase64Binary)i).getByteArrayValue();
      return Arrays.equals(this._value, ival);
   }

   protected int value_hash_code() {
      if (this._hashcached) {
         return this.hashcode;
      } else {
         this._hashcached = true;
         if (this._value == null) {
            return this.hashcode = 0;
         } else {
            byte[] res = md5.digest(this._value);
            return this.hashcode = res[0] << 24 + res[1] << 16 + res[2] << 8 + res[3];
         }
      }
   }

   static {
      try {
         md5 = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException var1) {
         throw new IllegalStateException("Cannot find MD5 hash Algorithm");
      }
   }
}
