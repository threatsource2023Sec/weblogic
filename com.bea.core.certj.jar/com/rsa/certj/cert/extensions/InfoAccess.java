package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import java.util.Vector;

/** @deprecated */
public abstract class InfoAccess extends X509V3Extension implements CertExtension {
   private Vector accessLocation = new Vector();
   private Vector accessMethod = new Vector();
   ASN1Template asn1TemplateValue;

   InfoAccess(int var1, boolean var2, byte[] var3, String var4) {
      this.extensionTypeFlag = var1;
      this.criticality = var2;
      this.setSpecialOID(var3);
      this.extensionTypeString = var4;
   }

   /** @deprecated */
   public void addAccessDescription(byte[] var1, int var2, int var3, GeneralName var4) throws CertificateException {
      if (var1 != null && var3 > 0 && var4 != null) {
         byte[] var5 = new byte[var3];
         System.arraycopy(var1, var2, var5, 0, var3);
         this.accessMethod.addElement(var5);
         this.accessLocation.addElement(var4);
      } else {
         throw new CertificateException("Missing values");
      }
   }

   /** @deprecated */
   public GeneralName getAccessLocation(int var1) throws CertificateException {
      if (var1 < this.accessLocation.size()) {
         return (GeneralName)this.accessLocation.elementAt(var1);
      } else {
         throw new CertificateException("Index is invalid.");
      }
   }

   /** @deprecated */
   public byte[] getAccessMethod(int var1) throws CertificateException {
      if (var1 < this.accessMethod.size()) {
         return (byte[])((byte[])this.accessMethod.elementAt(var1));
      } else {
         throw new CertificateException("Index is invalid.");
      }
   }

   /** @deprecated */
   public int getAccessDescriptionCount() {
      return this.accessMethod.size();
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            OfContainer var3 = new OfContainer(0, 12288, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               SequenceContainer var8 = new SequenceContainer(0);
               EndContainer var9 = new EndContainer();
               OIDContainer var10 = new OIDContainer(16777216);
               EncodedContainer var11 = new EncodedContainer(65280);
               ASN1Container[] var12 = new ASN1Container[]{var8, var10, var11, var9};
               ASN1.berDecode(var7.data, var7.dataOffset, var12);
               if (var10.data != null && var10.dataLen != 0) {
                  byte[] var13 = new byte[var10.dataLen];
                  System.arraycopy(var10.data, var10.dataOffset, var13, 0, var10.dataLen);
                  this.accessMethod.addElement(var13);
               }

               this.accessLocation.addElement(new GeneralName(var11.data, var11.dataOffset, 0));
            }

         } catch (ASN_Exception var14) {
            throw new CertificateException("Could not decode AuthorityInfoAccess extension.");
         } catch (NameException var15) {
            throw new CertificateException("Could not decode AuthorityInfoAccess extension.");
         }
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      if (this.accessLocation.isEmpty()) {
         return 0;
      } else {
         try {
            Vector var1 = new Vector();
            OfContainer var2 = new OfContainer(0, true, 0, 12288, new EncodedContainer(12288));
            var1.addElement(var2);

            for(int var3 = 0; var3 < this.accessMethod.size(); ++var3) {
               EncodedContainer var4 = this.encodeDescription(var3);
               var2.addContainer(var4);
            }

            ASN1Container[] var6 = new ASN1Container[var1.size()];
            var1.copyInto(var6);
            this.asn1TemplateValue = new ASN1Template(var6);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (Exception var5) {
            return 0;
         }
      }
   }

   private EncodedContainer encodeDescription(int var1) throws CertificateException {
      SequenceContainer var3 = new SequenceContainer(0, true, 0);
      EndContainer var4 = new EndContainer();
      byte[] var5 = (byte[])((byte[])this.accessMethod.elementAt(var1));

      try {
         OIDContainer var6 = new OIDContainer(16777216, true, 0, var5, 0, var5.length);
         GeneralName var7 = (GeneralName)this.accessLocation.elementAt(var1);
         byte[] var8 = new byte[var7.getDERLen(0)];
         int var9 = var7.getDEREncoding(var8, 0, 0);
         EncodedContainer var10 = new EncodedContainer(0, true, 0, var8, 0, var9);
         ASN1Container[] var11 = new ASN1Container[]{var3, var6, var10, var4};
         ASN1Template var12 = new ASN1Template(var11);
         int var13 = var12.derEncodeInit();
         byte[] var14 = new byte[var13];
         var13 = var12.derEncode(var14, 0);
         EncodedContainer var2 = new EncodedContainer(12288, true, 0, var14, 0, var13);
         return var2;
      } catch (ASN_Exception var15) {
         throw new CertificateException(" Can't encode Access Description");
      } catch (NameException var16) {
         throw new CertificateException("Could not encode Access Description ");
      }
   }

   /** @deprecated */
   public int derEncodeValue(byte[] var1, int var2) {
      if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         return 0;
      } else {
         try {
            return this.asn1TemplateValue.derEncode(var1, var2);
         } catch (ASN_Exception var4) {
            return 0;
         }
      }
   }

   /** @deprecated */
   protected void copyValues(X509V3Extension var1) {
      InfoAccess var2 = (InfoAccess)var1;

      for(int var3 = 0; var3 < this.accessMethod.size(); ++var3) {
         var2.accessMethod.addElement(this.accessMethod.elementAt(var3));
         var2.accessLocation.addElement(this.accessLocation.elementAt(var3));
      }

      if (this.asn1TemplateValue != null) {
         var2.derEncodeValueInit();
      }

      super.copyValues(var2);
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.accessMethod = new Vector();
      this.accessLocation = new Vector();
   }
}
