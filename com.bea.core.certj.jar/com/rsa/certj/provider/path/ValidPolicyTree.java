package com.rsa.certj.provider.path;

import com.rsa.certj.cert.extensions.PolicyQualifiers;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.certj.spi.path.CertPathException;
import java.util.Iterator;
import java.util.Vector;

final class ValidPolicyTree {
   private ValidPolicyTreeNode a;

   ValidPolicyTree() {
      Vector var1 = new Vector();
      var1.add(X509V3Extension.ANY_POLICY_OID);
      PolicyQualifiers var2 = new PolicyQualifiers();

      try {
         this.a = ValidPolicyTreeNode.a(X509V3Extension.ANY_POLICY_OID, var2, var1);
      } catch (CertPathException var4) {
         throw new IllegalStateException("Internal error!");
      }
   }

   static void a(ValidPolicyTreeNode var0) {
      if (var0 != null) {
         ValidPolicyTreeNode var1 = var0.a();
         if (var1 != null) {
            var1.b(var0);
            Vector var2 = var1.c();
            if (var2 == null) {
               a(var1);
            }

         }
      }
   }

   void a(int var1) {
      if (var1 >= 1 && this.a.d()) {
         Vector var2 = new Vector(this.a.c());
         this.a(var2, 1, var1);
      }
   }

   Vector b(int var1) {
      Vector var2 = new Vector();
      if (var1 < 0) {
         return var2;
      } else {
         Vector var3 = new Vector();
         var3.add(this.a);
         this.a(var3, 0, var2, var1);
         return var2;
      }
   }

   ValidPolicyTreeNode a() {
      return this.a;
   }

   private void a(Vector var1, int var2, int var3) {
      if (var1 != null && var2 <= var3) {
         Vector var4 = new Vector(var1);
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            ValidPolicyTreeNode var6 = (ValidPolicyTreeNode)var5.next();
            Vector var7 = var6.c();
            this.a(var7, var2 + 1, var3);
            if (var6.c() == null) {
               ValidPolicyTreeNode var8 = var6.a();
               var8.b(var6);
            }
         }

      }
   }

   private void a(Vector var1, int var2, Vector var3, int var4) {
      if (var1 != null) {
         if (var2 == var4) {
            var3.addAll(var1);
         } else {
            Iterator var5 = var1.iterator();

            while(var5.hasNext()) {
               ValidPolicyTreeNode var6 = (ValidPolicyTreeNode)var5.next();
               this.a(var6.c(), var2 + 1, var3, var4);
            }

         }
      }
   }
}
