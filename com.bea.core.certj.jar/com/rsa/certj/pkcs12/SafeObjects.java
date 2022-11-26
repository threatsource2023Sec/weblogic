package com.rsa.certj.pkcs12;

import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.attributes.FriendlyName;
import com.rsa.certj.cert.attributes.LocalKeyID;
import com.rsa.certj.cert.attributes.X501Attribute;
import com.rsa.jsafe.JSAFE_PrivateKey;
import java.io.Serializable;
import java.util.Vector;

final class SafeObjects implements Serializable, Cloneable {
   Vector a = new Vector();
   Vector b = new Vector();
   Vector c = new Vector();
   private Vector d = new Vector();
   private Vector e = new Vector();
   private Vector f = new Vector();
   private Vector g = new Vector();

   SafeObjects() {
   }

   SafeObjects(Certificate[] var1, CRL[] var2, JSAFE_PrivateKey[] var3, X501Attributes[] var4, X501Attributes[] var5, X501Attributes[] var6, String[] var7) {
      int var8;
      if (var1 != null) {
         for(var8 = 0; var8 < var1.length; ++var8) {
            this.d.addElement(var1[var8]);
            if (var4 != null && var8 < var4.length) {
               this.a.addElement(var4[var8]);
            } else {
               this.a.addElement((Object)null);
            }
         }
      }

      if (var2 != null) {
         for(var8 = 0; var8 < var2.length; ++var8) {
            this.f.addElement(var2[var8]);
            if (var5 != null && var8 < var5.length) {
               this.c.addElement(var5[var8]);
            } else {
               this.c.addElement((Object)null);
            }
         }
      }

      if (var3 != null) {
         for(var8 = 0; var8 < var3.length; ++var8) {
            this.e.addElement(var3[var8]);
            if (var6 != null && var8 < var6.length) {
               this.b.addElement(var6[var8]);
            } else {
               this.b.addElement((Object)null);
            }

            if (var7 != null && var8 < var7.length) {
               this.g.addElement(var7[var8]);
            } else {
               this.g.addElement((Object)null);
            }
         }
      }

   }

   X509Certificate a(X501Attributes var1) throws PKCS12Exception {
      try {
         for(int var2 = 0; var2 < this.a.size(); ++var2) {
            X501Attributes var3 = (X501Attributes)this.a.elementAt(var2);
            if (var3 != null && this.a(var1, var3)) {
               return (X509Certificate)this.d.elementAt(var2);
            }
         }

         throw new PKCS12Exception("No Certificate found for Private Key");
      } catch (Exception var4) {
         throw new PKCS12Exception(var4);
      }
   }

   boolean a(X501Attributes var1, X501Attributes var2) throws PKCS12Exception {
      try {
         for(int var3 = 0; var3 < var1.getAttributeCount(); ++var3) {
            X501Attribute var4 = var1.getAttributeByIndex(var3);

            for(int var5 = 0; var5 < var2.getAttributeCount(); ++var5) {
               X501Attribute var6 = var2.getAttributeByIndex(var5);
               if (var4.getAttributeType() == var6.getAttributeType()) {
                  int var7 = var4.getAttributeType();
                  switch (var7) {
                     case 3:
                        FriendlyName var8 = (FriendlyName)var4;
                        FriendlyName var9 = (FriendlyName)var6;
                        if (var8.getFriendlyName().equals(var9.getFriendlyName())) {
                           return true;
                        }
                        break;
                     case 4:
                        LocalKeyID var10 = (LocalKeyID)var4;
                        LocalKeyID var11 = (LocalKeyID)var6;
                        byte[] var12 = var10.getLocalKeyID();
                        byte[] var13 = var11.getLocalKeyID();
                        if (var12.length == var13.length) {
                           for(int var14 = 0; var14 < var13.length && var12[var14] == var13[var14]; ++var14) {
                           }

                           return true;
                        }
                  }
               }
            }
         }

         return false;
      } catch (Exception var15) {
         throw new PKCS12Exception(var15);
      }
   }

   Vector a() {
      return this.d;
   }

   Vector b() {
      return this.f;
   }

   Vector c() {
      return this.e;
   }

   Vector d() {
      return this.a;
   }

   Vector e() {
      return this.b;
   }

   Vector f() {
      return this.c;
   }

   Vector g() {
      return this.g;
   }
}
