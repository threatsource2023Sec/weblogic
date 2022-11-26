package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.OCSPRequestExtension;
import java.util.Vector;

/** @deprecated */
public class OCSPAcceptableResponses extends X509V3Extension implements OCSPRequestExtension {
   ASN1Template asn1TemplateValue;
   Vector acceptableResponsesOID;
   /** @deprecated */
   public static final byte[] ID_PKIX_OCSP_BASIC = new byte[]{43, 6, 1, 5, 5, 7, 48, 1, 1};

   /** @deprecated */
   public OCSPAcceptableResponses() {
      this.extensionTypeFlag = 121;
      this.criticality = false;
      this.setSpecialOID(OCSP_ACCEPTABLE_RESPONSES_OID);
      this.extensionTypeString = "OCSPAcceptableResponses";
      this.acceptableResponsesOID = null;
   }

   /** @deprecated */
   public void addAcceptableResponse(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 != null && var3 != 0) {
         byte[] var4 = new byte[var3];
         System.arraycopy(var1, var2, var4, 0, var3);
         if (this.acceptableResponsesOID == null) {
            this.acceptableResponsesOID = new Vector();
         }

         this.acceptableResponsesOID.addElement(var4);
      } else {
         throw new CertificateException("Missing values");
      }
   }

   /** @deprecated */
   public int getAcceptableResponseCount() {
      return this.acceptableResponsesOID != null ? this.acceptableResponsesOID.size() : 0;
   }

   /** @deprecated */
   public byte[] getAcceptableResponse(int var1) throws InvalidParameterException {
      if (this.acceptableResponsesOID == null) {
         return null;
      } else if (this.acceptableResponsesOID.size() <= var1) {
         throw new InvalidParameterException("Specified index is invalid.");
      } else {
         return (byte[])((byte[])this.acceptableResponsesOID.elementAt(var1));
      }
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
               if (this.acceptableResponsesOID == null) {
                  this.acceptableResponsesOID = new Vector();
               }

               byte[] var10 = new byte[var8.dataLen];
               System.arraycopy(var8.data, var8.dataOffset, var10, 0, var8.dataLen);
               this.acceptableResponsesOID.addElement(var10);
            }

         } catch (ASN_Exception var11) {
            throw new CertificateException("Could not decode Extended Key Usage extension.");
         }
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      if (this.acceptableResponsesOID == null) {
         return 0;
      } else {
         int var1 = this.acceptableResponsesOID.size();
         if (var1 == 0) {
            return 0;
         } else {
            Vector var2 = new Vector();

            try {
               OfContainer var3 = new OfContainer(0, true, 0, 12288, new EncodedContainer(1536));
               var2.addElement(var3);

               for(int var4 = 0; var4 < var1; ++var4) {
                  try {
                     EncodedContainer var5 = this.encodeOID(var4);
                     var3.addContainer(var5);
                  } catch (Exception var6) {
                     return 0;
                  }
               }

               ASN1Container[] var8 = new ASN1Container[var2.size()];
               var2.copyInto(var8);
               this.asn1TemplateValue = new ASN1Template(var8);
               return this.asn1TemplateValue.derEncodeInit();
            } catch (ASN_Exception var7) {
               return 0;
            }
         }
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

   private EncodedContainer encodeOID(int var1) throws CertificateException {
      try {
         byte[] var2 = (byte[])this.acceptableResponsesOID.elementAt(var1);
         int var3 = var2.length;
         OIDContainer var4 = new OIDContainer(16777216, true, 0, var2, 0, var3);
         ASN1Container[] var5 = new ASN1Container[]{var4};
         ASN1Template var6 = new ASN1Template(var5);
         int var7 = var6.derEncodeInit();
         byte[] var8 = new byte[var7];
         var7 = var6.derEncode(var8, 0);
         return new EncodedContainer(1536, true, 0, var8, 0, var7);
      } catch (ASN_Exception var9) {
         throw new CertificateException(" Can't encode Acceptable Response Type");
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      OCSPAcceptableResponses var1 = new OCSPAcceptableResponses();
      if (this.acceptableResponsesOID != null) {
         var1.acceptableResponsesOID = new Vector(this.acceptableResponsesOID);
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
      this.asn1TemplateValue = null;
   }
}
