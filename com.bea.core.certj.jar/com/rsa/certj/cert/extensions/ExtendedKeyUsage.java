package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import java.util.Vector;

/** @deprecated */
public class ExtendedKeyUsage extends X509V3Extension implements CertExtension {
   /** @deprecated */
   public static final byte[] ID_KP_SERVER_AUTH = new byte[]{43, 6, 1, 5, 5, 7, 3, 1};
   /** @deprecated */
   public static final byte[] ID_KP_CLIENT_AUTH = new byte[]{43, 6, 1, 5, 5, 7, 3, 2};
   /** @deprecated */
   public static final byte[] ID_KP_CODE_SIGNING = new byte[]{43, 6, 1, 5, 5, 7, 3, 3};
   /** @deprecated */
   public static final byte[] ID_KP_EMAIL_PROTECTION = new byte[]{43, 6, 1, 5, 5, 7, 3, 4};
   /** @deprecated */
   public static final byte[] ID_KP_TIME_STAMPING = new byte[]{43, 6, 1, 5, 5, 7, 3, 8};
   /** @deprecated */
   public static final byte[] ID_KP_OCSP_SIGNING = new byte[]{43, 6, 1, 5, 5, 7, 3, 9};
   private Vector[] keyPurposeID = this.createVectorArray(3);
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public ExtendedKeyUsage() {
      this.extensionTypeFlag = 37;
      this.criticality = false;
      this.setStandardOID(37);
      this.extensionTypeString = "ExtendedKeyUsage";
   }

   /** @deprecated */
   public ExtendedKeyUsage(byte[] var1, int var2, int var3, boolean var4) {
      this.extensionTypeFlag = 37;
      this.criticality = var4;
      this.setStandardOID(37);
      if (var1 != null && var3 != 0) {
         this.keyPurposeID[0].addElement(var1);
         this.keyPurposeID[1].addElement(new Integer(var2));
         this.keyPurposeID[2].addElement(new Integer(var3));
      }

      this.extensionTypeString = "ExtendedKeyUsage";
   }

   private Vector[] createVectorArray(int var1) {
      Vector[] var2 = new Vector[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = new Vector();
      }

      return var2;
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            OfContainer var3 = new OfContainer(this.special, 12288, new EncodedContainer(1536));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               OIDContainer var8 = new OIDContainer(16777216);
               ASN1Container[] var9 = new ASN1Container[]{var8};
               ASN1.berDecode(var7.data, var7.dataOffset, var9);
               this.keyPurposeID[0].addElement(var8.data);
               this.keyPurposeID[1].addElement(new Integer(var8.dataOffset));
               this.keyPurposeID[2].addElement(new Integer(var8.dataLen));
            }

         } catch (ASN_Exception var10) {
            throw new CertificateException("Could not decode Extended Key Usage extension.");
         }
      }
   }

   /** @deprecated */
   public void addExtendedKeyUsage(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.keyPurposeID[0].addElement(var1);
         this.keyPurposeID[1].addElement(new Integer(var2));
         this.keyPurposeID[2].addElement(new Integer(var3));
      }

   }

   /** @deprecated */
   public byte[] getExtendedKeyUsage(int var1) throws CertificateException {
      if (var1 >= this.keyPurposeID[0].size()) {
         throw new CertificateException(" Invalid Index ");
      } else if (this.keyPurposeID[0].elementAt(var1) == null) {
         return null;
      } else {
         Integer var2 = (Integer)this.keyPurposeID[1].elementAt(var1);
         Integer var3 = (Integer)this.keyPurposeID[2].elementAt(var1);
         byte[] var4 = new byte[var3];
         System.arraycopy(this.keyPurposeID[0].elementAt(var1), var2, var4, 0, var3);
         return var4;
      }
   }

   /** @deprecated */
   public int getKeyUsageCount() {
      return this.keyPurposeID[0].size();
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      Vector var1 = new Vector();

      try {
         OfContainer var2 = new OfContainer(this.special, true, 0, 12288, new EncodedContainer(1536));
         var1.addElement(var2);

         for(int var3 = 0; var3 < this.keyPurposeID[0].size(); ++var3) {
            try {
               EncodedContainer var4 = this.encodeOID(var3);
               var2.addContainer(var4);
            } catch (Exception var5) {
               return 0;
            }
         }

         ASN1Container[] var7 = new ASN1Container[var1.size()];
         var1.copyInto(var7);
         this.asn1TemplateValue = new ASN1Template(var7);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var6) {
         return 0;
      }
   }

   private EncodedContainer encodeOID(int var1) throws NameException {
      try {
         int var2 = (Integer)this.keyPurposeID[1].elementAt(var1);
         int var3 = (Integer)this.keyPurposeID[2].elementAt(var1);
         OIDContainer var4 = new OIDContainer(16777216, true, 0, (byte[])((byte[])this.keyPurposeID[0].elementAt(var1)), var2, var3);
         ASN1Container[] var5 = new ASN1Container[]{var4};
         ASN1Template var6 = new ASN1Template(var5);
         int var7 = var6.derEncodeInit();
         byte[] var8 = new byte[var7];
         var7 = var6.derEncode(var8, 0);
         return new EncodedContainer(1536, true, 0, var8, 0, var7);
      } catch (ASN_Exception var9) {
         throw new NameException(" Can't encode Extended Key Usage");
      }
   }

   /** @deprecated */
   public int derEncodeValue(byte[] var1, int var2) {
      if (var1 == null) {
         return 0;
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         return 0;
      } else {
         try {
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      ExtendedKeyUsage var1 = new ExtendedKeyUsage();

      for(int var2 = 0; var2 < this.keyPurposeID.length; ++var2) {
         for(int var3 = 0; var3 < this.keyPurposeID[var2].size(); ++var3) {
            var1.keyPurposeID[var2].addElement(this.keyPurposeID[var2].elementAt(var3));
         }
      }

      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.keyPurposeID = null;
      this.asn1TemplateValue = null;
   }
}
