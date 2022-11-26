package com.rsa.certj.cert;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.extensions.X509V3Extension;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

/** @deprecated */
public class X509V3Extensions implements Serializable, Cloneable {
   /** @deprecated */
   protected Vector theExtensions;
   /** @deprecated */
   protected int special;
   private int extFlag;
   /** @deprecated */
   public static final int X509_EXT_TYPE_CERT = 1;
   /** @deprecated */
   public static final int X509_EXT_TYPE_CRL = 2;
   /** @deprecated */
   public static final int X509_EXT_TYPE_CRL_ENTRY = 3;
   /** @deprecated */
   public static final int X509_EXT_TYPE_OCSP_SINGLE = 4;
   /** @deprecated */
   public static final int X509_EXT_TYPE_OCSP_REQUEST = 5;
   private int extFlagMin = 1;
   private int extFlagMax = 5;
   private ASN1Template asn1Template;

   /** @deprecated */
   public X509V3Extensions(int var1) throws CertificateException {
      if (var1 <= this.extFlagMax && var1 >= this.extFlagMin) {
         this.extFlag = var1;
      } else {
         throw new CertificateException("Invalid extensions type.");
      }
   }

   /** @deprecated */
   public X509V3Extensions(byte[] var1, int var2, int var3, int var4) throws CertificateException {
      if (var4 <= this.extFlagMax && var4 >= this.extFlagMin) {
         if (var1 == null) {
            throw new CertificateException("Encoding is null.");
         } else {
            this.extFlag = var4;
            this.setExtensionsBER(var1, var2, var3);
         }
      } else {
         throw new CertificateException("Invalid extensions type.");
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CertificateException {
      if (var0 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            return var1 + ASN1Lengths.determineLength(var0, var1);
         } catch (ASN_Exception var3) {
            throw new CertificateException("Could not read the BER encoding.");
         }
      }
   }

   private void setExtensionsBER(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.reset();

         try {
            OfContainer var4 = new OfContainer(var3, 12288, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();
            if (var6 > 0) {
               this.theExtensions = new Vector();
            }

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               X509V3Extension var9 = X509V3Extension.getInstance(var8.data, var8.dataOffset);
               switch (this.extFlag) {
                  case 1:
                     if (!(var9 instanceof CertExtension)) {
                        throw new CertificateException("Extension of the wrong type");
                     }
                     break;
                  case 2:
                     if (!(var9 instanceof CRLExtension)) {
                        throw new CertificateException("Extension of the wrong type");
                     }
                     break;
                  case 3:
                     if (!(var9 instanceof CRLEntryExtension)) {
                        throw new CertificateException("Extension of the wrong type");
                     }
                     break;
                  case 4:
                     if (!(var9 instanceof OCSPSingleExtension)) {
                        throw new CertificateException("Extension of the wrong type");
                     }
                     break;
                  case 5:
                     if (!(var9 instanceof OCSPRequestExtension)) {
                        throw new CertificateException("Extension of the wrong type");
                     }
               }

               if (this.getExtensionByType(var9.getExtensionType()) != null && var9.getExtensionType() != -1) {
                  throw new CertificateException("Extension of " + var9.getExtensionTypeString() + " type already exists.");
               }

               this.theExtensions.addElement(var9);
            }

         } catch (ASN_Exception var10) {
            throw new CertificateException("Could not read the BER of the Extensions.");
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) {
      return this.encodeInit(var1);
   }

   private int encodeInit(int var1) {
      this.special = var1;

      try {
         OfContainer var2 = new OfContainer(var1, true, 0, 12288, new EncodedContainer(12288));
         int var3 = 0;
         if (this.theExtensions != null) {
            var3 = this.theExtensions.size();
         }

         for(int var4 = 0; var4 < var3; ++var4) {
            X509V3Extension var5 = (X509V3Extension)this.theExtensions.elementAt(var4);
            int var6 = var5.getDERLen(0);
            EncodedContainer var7 = new EncodedContainer(12288, true, 0, (byte[])null, 0, var6);
            var2.addContainer(var7);
         }

         ASN1Container[] var9 = new ASN1Container[]{var2};
         this.asn1Template = new ASN1Template(var9);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var8) {
         this.asn1Template = null;
         return 0;
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) {
      if (var1 == null) {
         return 0;
      } else if ((this.asn1Template == null || var3 != this.special) && this.encodeInit(var3) == 0) {
         return 0;
      } else {
         int var4 = 0;

         try {
            var4 += this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
         } catch (ASN_Exception var8) {
            this.asn1Template = null;
            return 0;
         }

         int var5 = 0;
         if (this.theExtensions != null) {
            var5 = this.theExtensions.size();
         }

         for(int var6 = 0; var6 < var5; ++var6) {
            X509V3Extension var7 = (X509V3Extension)this.theExtensions.elementAt(var6);
            var4 += var7.getDEREncoding(var1, var2 + var4, 0);
         }

         return var4;
      }
   }

   /** @deprecated */
   public int addV3Extension(X509V3Extension var1) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Specified extension is null.");
      } else {
         this.reset();
         if (this.theExtensions == null) {
            this.theExtensions = new Vector();
         }

         switch (this.extFlag) {
            case 1:
               if (!(var1 instanceof CertExtension)) {
                  throw new CertificateException("Extension of the wrong type");
               }
               break;
            case 2:
               if (!(var1 instanceof CRLExtension)) {
                  throw new CertificateException("Extension of the wrong type");
               }
               break;
            case 3:
               if (!(var1 instanceof CRLEntryExtension)) {
                  throw new CertificateException("Extension of the wrong type");
               }
         }

         if (this.getExtensionByType(var1.getExtensionType()) != null && var1.getExtensionType() != -1) {
            throw new CertificateException("Extension of " + var1.getExtensionTypeString() + " type already exists.");
         } else {
            this.theExtensions.addElement(var1);
            return this.theExtensions.indexOf(var1);
         }
      }
   }

   /** @deprecated */
   public int getExtensionCount() {
      return this.theExtensions != null ? this.theExtensions.size() : 0;
   }

   /** @deprecated */
   public int getExtensionsType() {
      return this.extFlag;
   }

   /** @deprecated */
   public X509V3Extension getExtensionByIndex(int var1) throws CertificateException {
      if (this.theExtensions == null) {
         throw new CertificateException(" There is no extensions.");
      } else if (var1 < this.getExtensionCount()) {
         return (X509V3Extension)this.theExtensions.elementAt(var1);
      } else {
         throw new CertificateException("Invalid index");
      }
   }

   /** @deprecated */
   public X509V3Extension getExtensionByType(int var1) throws CertificateException {
      if (this.theExtensions == null) {
         throw new CertificateException("There is no extensions.");
      } else {
         int var2 = this.getExtensionCount();

         try {
            for(int var4 = 0; var4 < var2; ++var4) {
               X509V3Extension var3 = this.getExtensionByIndex(var4);
               if (var3.getExtensionType() == var1) {
                  return (X509V3Extension)var3.clone();
               }
            }

            return null;
         } catch (CloneNotSupportedException var5) {
            throw new CertificateException("Clone Exception");
         }
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (!(var1 instanceof X509V3Extensions)) {
         return false;
      } else {
         int var2 = this.getDERLen(0);
         if (var2 == 0) {
            return false;
         } else {
            byte[] var3 = new byte[var2];
            var2 = this.getDEREncoding(var3, 0, 0);
            if (var2 == 0) {
               return false;
            } else {
               X509V3Extensions var4 = (X509V3Extensions)var1;
               int var5 = var4.getDERLen(0);
               if (var5 == 0) {
                  return false;
               } else {
                  byte[] var6 = new byte[var2];
                  var5 = var4.getDEREncoding(var6, 0, 0);
                  return var5 == 0 ? false : CertJUtils.byteArraysEqual(var3, var6);
               }
            }
         }
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = this.getDERLen(0);
      if (var1 == 0) {
         return 0;
      } else {
         byte[] var2 = new byte[var1];
         this.getDEREncoding(var2, 0, 0);
         return Arrays.hashCode(var2);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      try {
         X509V3Extensions var1 = new X509V3Extensions(this.extFlag);
         if (this.theExtensions != null) {
            var1.theExtensions = new Vector();
            int var2 = this.theExtensions.size();

            for(int var3 = 0; var3 < var2; ++var3) {
               X509V3Extension var4 = (X509V3Extension)this.getExtensionByIndex(var3).clone();
               var1.addV3Extension(var4);
            }
         }

         if (this.asn1Template != null) {
            var1.getDERLen(this.special);
         }

         return var1;
      } catch (CertificateException var5) {
         throw new CloneNotSupportedException("Wrong Extensions type.");
      }
   }

   /** @deprecated */
   protected void reset() {
      this.asn1Template = null;
   }
}
