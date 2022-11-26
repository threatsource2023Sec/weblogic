package com.rsa.certj.provider.path;

import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.extensions.PolicyQualifiers;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.certj.spi.path.CertPathException;
import com.rsa.certj.spi.path.PolicyInformation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

final class ValidPolicyTreeNode {
   private byte[] a;
   private PolicyQualifiers b;
   private Vector c;
   private ValidPolicyTreeNode d;
   private Vector e;

   private ValidPolicyTreeNode(byte[] var1, PolicyQualifiers var2, Vector var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   static ValidPolicyTreeNode a(byte[] var0, PolicyQualifiers var1, Vector var2) throws CertPathException {
      if (var0 != null && var2 != null) {
         return new ValidPolicyTreeNode(var0, var1, var2);
      } else {
         throw new CertPathException("ValidPolicyTreeNode parameters cannot be null!");
      }
   }

   ValidPolicyTreeNode a() {
      return this.d;
   }

   void a(ValidPolicyTreeNode var1) {
      if (var1 != null) {
         if (this.e == null) {
            this.e = new Vector();
         }

         this.e.add(var1);
         var1.d = this;
      }
   }

   void b(ValidPolicyTreeNode var1) {
      if (var1 != null && this.e != null) {
         this.e.remove(var1);
         if (this.e.size() == 0) {
            this.e = null;
         }

      }
   }

   void b() {
      this.e = null;
   }

   Vector c() {
      return this.e;
   }

   boolean d() {
      return this.e != null;
   }

   byte[] e() {
      return this.a;
   }

   Vector f() {
      return this.c;
   }

   ValidPolicyTreeNode a(byte[] var1) {
      if (this.e == null) {
         return null;
      } else {
         Iterator var2 = this.e.iterator();

         ValidPolicyTreeNode var3;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            var3 = (ValidPolicyTreeNode)var2.next();
         } while(!Arrays.equals(var3.e(), var1));

         return var3;
      }
   }

   PolicyQualifiers g() {
      return this.b;
   }

   PolicyInformation h() throws CertPathException {
      return new PolicyInformation(this.a, this.b);
   }

   boolean b(byte[] var1) {
      return CertJUtils.byteArraysEqual(this.a, var1);
   }

   boolean i() {
      return this.b(X509V3Extension.ANY_POLICY_OID);
   }
}
