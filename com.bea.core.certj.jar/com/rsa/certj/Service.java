package com.rsa.certj;

import java.util.Iterator;
import java.util.Vector;

/** @deprecated */
public class Service {
   private Vector providers = new Vector();

   /** @deprecated */
   public static Service getInstance(CertJ var0, int var1) {
      switch (var1) {
         case 0:
         case 2:
         case 3:
            return new Service(var0);
         case 1:
            return new DatabaseService(var0);
         case 4:
            return new PKIService(var0);
         default:
            return new Service(var0);
      }
   }

   /** @deprecated */
   protected Service(CertJ var1) {
   }

   /** @deprecated */
   public String[] listProviderNames() {
      int var1 = this.providers.size();
      String[] var2 = new String[var1];
      int var3 = 0;

      ProviderImplementation var5;
      for(Iterator var4 = this.providers.iterator(); var4.hasNext(); var2[var3++] = var5.getName()) {
         var5 = (ProviderImplementation)var4.next();
      }

      return var2;
   }

   /** @deprecated */
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getClass().toString());
      var1.append('[');
      boolean var2 = true;

      ProviderImplementation var4;
      for(Iterator var3 = this.providers.iterator(); var3.hasNext(); var1.append(var4.getName())) {
         var4 = (ProviderImplementation)var3.next();
         if (!var2) {
            var1.append(',');
         } else {
            var2 = false;
         }
      }

      var1.append(']');
      return var1.toString();
   }

   /** @deprecated */
   public void unbind() {
   }

   /** @deprecated */
   protected int getProviderCount() {
      return this.providers.size();
   }

   /** @deprecated */
   protected ProviderImplementation getProviderAt(int var1) {
      return var1 >= 0 && var1 < this.providers.size() ? (ProviderImplementation)this.providers.elementAt(var1) : null;
   }

   /** @deprecated */
   protected void addProvider(ProviderImplementation var1) {
      this.providers.addElement(var1);
   }
}
