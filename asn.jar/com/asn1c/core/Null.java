package com.asn1c.core;

import java.io.PrintWriter;

public class Null implements ASN1Object, Comparable {
   public static final Null NULL = new Null();

   public Null() {
   }

   public Null(Null var1) {
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      var1.println(var2 + var3 + "NULL" + var4);
   }

   public String toString() {
      return "NULL";
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }

   public int hashCode() {
      return 1249;
   }

   public int compareTo(Object var1) {
      Null var2 = (Null)var1;
      return 0;
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Null;
   }
}
