package com.asn1c.core;

import java.io.PrintWriter;

public class Open implements ASN1Object, Comparable {
   protected Object value;
   protected UnitString encoded;

   public Open(Object var1, UnitString var2) {
      if ((var2.bitLength() & 7) != 0) {
         throw new IllegalArgumentException();
      } else {
         this.value = var1;
         this.encoded = var2;
      }
   }

   public Open(UnitString var1) {
      this((Object)null, var1);
   }

   public Open() {
      this((Object)null, (UnitString)null);
   }

   public Open(Open var1) {
      this.value = var1.value;
      this.encoded = var1.encoded;
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      var1.println(var2 + var3 + this.toString() + var4);
   }

   public String toString() {
      return String.valueOf(this.value);
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object var1) {
      this.value = var1;
   }

   public UnitString getEncoded() {
      return this.encoded;
   }

   public void setEncoded(UnitString var1) {
      if ((var1.bitLength() & 7) != 0) {
         throw new IllegalArgumentException();
      } else {
         this.encoded = var1;
      }
   }

   public int compareTo(Object var1) {
      Open var2 = (Open)var1;
      return this.encoded.compareTo(var2.encoded);
   }

   public boolean equals(Object var1) {
      return this.compareTo(var1) == 0;
   }
}
