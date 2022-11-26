package org.python.bouncycastle.asn1.eac;

import java.util.Enumeration;
import java.util.Hashtable;

public class Flags {
   int value = 0;

   public Flags() {
   }

   public Flags(int var1) {
      this.value = var1;
   }

   public void set(int var1) {
      this.value |= var1;
   }

   public boolean isSet(int var1) {
      return (this.value & var1) != 0;
   }

   public int getFlags() {
      return this.value;
   }

   String decode(Hashtable var1) {
      StringJoiner var2 = new StringJoiner(" ");
      Enumeration var3 = var1.keys();

      while(var3.hasMoreElements()) {
         Integer var4 = (Integer)var3.nextElement();
         if (this.isSet(var4)) {
            var2.add((String)var1.get(var4));
         }
      }

      return var2.toString();
   }

   private class StringJoiner {
      String mSeparator;
      boolean First = true;
      StringBuffer b = new StringBuffer();

      public StringJoiner(String var2) {
         this.mSeparator = var2;
      }

      public void add(String var1) {
         if (this.First) {
            this.First = false;
         } else {
            this.b.append(this.mSeparator);
         }

         this.b.append(var1);
      }

      public String toString() {
         return this.b.toString();
      }
   }
}
