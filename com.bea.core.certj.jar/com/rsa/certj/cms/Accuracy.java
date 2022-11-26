package com.rsa.certj.cms;

/** @deprecated */
public final class Accuracy {
   private final long a;
   private final int b;
   private final int c;

   /** @deprecated */
   public Accuracy(long var1, int var3, int var4) {
      this.a(var1, var3, var4);
      this.a = var1;
      this.b = var3;
      this.c = var4;
   }

   private void a(long var1, int var3, int var4) {
      if (var1 < 0L) {
         throw new IllegalArgumentException("Accuracy seconds cannot be negative.");
      } else if (var3 < 0 || var3 > 1000 || var4 < 0 || var4 > 1000) {
         throw new IllegalArgumentException("Accuracy millis and micros must be between 0 and 999.");
      }
   }

   /** @deprecated */
   public long getSeconds() {
      return this.a;
   }

   /** @deprecated */
   public int getMillis() {
      return this.b;
   }

   /** @deprecated */
   public int getMicros() {
      return this.c;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof Accuracy)) {
         return false;
      } else {
         Accuracy var2 = (Accuracy)var1;
         return this.a == var2.a && this.b == var2.b && this.c == var2.c;
      }
   }

   /** @deprecated */
   public int hashCode() {
      return (int)(this.a << 20) | this.b << 10 | this.c;
   }
}
