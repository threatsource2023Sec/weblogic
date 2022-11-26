package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.GenTimeContainer;
import com.rsa.asn1.IA5StringContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.OCSPSingleExtension;
import java.util.Date;

/** @deprecated */
public class CRLReference extends X509V3Extension implements OCSPSingleExtension {
   private static final int ENCODING_TAG_SPECIAL = 10551296;
   ASN1Template asn1TemplateValue;
   private String crlUrl;
   private byte[] crlNum;
   private int crlNumLen;
   private Date crlTime;

   /** @deprecated */
   public CRLReference() {
      this.extensionTypeFlag = 119;
      this.criticality = false;
      this.setSpecialOID(CRL_REFERENCE_OID);
      this.extensionTypeString = "CRLReference";
      this.crlUrl = null;
      this.crlNum = null;
      this.crlNumLen = 0;
      this.crlTime = null;
   }

   /** @deprecated */
   public CRLReference(String var1, byte[] var2, int var3, int var4, Date var5) {
      this.extensionTypeFlag = 119;
      this.criticality = false;
      this.setSpecialOID(CRL_REFERENCE_OID);
      this.extensionTypeString = "CRLReference";
      this.setURL(var1);
      this.setCRLTime(var5);
      this.setCRLNumber(var2, var3, var4);
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      try {
         SequenceContainer var1 = new SequenceContainer(0, true, 0);
         EndContainer var2 = new EndContainer();
         IA5StringContainer var3 = null;
         IntegerContainer var4 = null;
         GenTimeContainer var5 = null;
         boolean var6 = false;
         boolean var7 = false;
         boolean var8 = false;
         if (this.crlUrl != null) {
            var3 = new IA5StringContainer(10551296, true, 0, this.crlUrl);
            var6 = true;
         }

         if (this.crlNum != null) {
            var4 = new IntegerContainer(10551297, true, 0, this.crlNum, 0, this.crlNumLen, true);
            var7 = true;
         }

         if (this.crlTime != null) {
            var5 = new GenTimeContainer(10551298, true, 0, this.crlTime);
            var8 = true;
         }

         if (!var6 && !var7 && !var8) {
            throw new CertificateException("Could not encode CRLReference extension, values not set.");
         } else if (var6 && var7 && var8) {
            throw new CertificateException("Could not encode CRLReference extension.");
         } else {
            ASN1Container[] var9;
            if (var6) {
               var9 = new ASN1Container[]{var1, var3, var2};
               this.asn1TemplateValue = new ASN1Template(var9);
            }

            if (var7) {
               var9 = new ASN1Container[]{var1, var4, var2};
               this.asn1TemplateValue = new ASN1Template(var9);
            }

            if (var8) {
               var9 = new ASN1Container[]{var1, var5, var2};
               this.asn1TemplateValue = new ASN1Template(var9);
            }

            return this.asn1TemplateValue.derEncodeInit();
         }
      } catch (ASN_Exception var10) {
         return 0;
      } catch (CertificateException var11) {
         return 0;
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
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            SequenceContainer var3 = new SequenceContainer(0);
            IA5StringContainer var4 = new IA5StringContainer(10551296);
            IntegerContainer var5 = new IntegerContainer(10551297);
            GenTimeContainer var6 = new GenTimeContainer(10551298);
            EndContainer var7 = new EndContainer();
            ASN1Container[] var8 = new ASN1Container[]{var3, var4, var5, var6, var7};
            ASN1.berDecode(var1, var2, var8);
            if (var4.dataPresent) {
               this.setURL(var4.getValueAsString());
            } else {
               this.crlUrl = null;
            }

            if (var5.dataPresent) {
               this.crlNum = new byte[var5.dataLen];
               System.arraycopy(var5.data, var5.dataOffset, this.crlNum, 0, var5.dataLen);
               this.crlNumLen = var5.dataLen;
            } else {
               this.crlNum = null;
            }

            if (var6.dataPresent) {
               this.setCRLTime(var6.theTime);
            } else {
               this.crlTime = null;
            }

         } catch (ASN_Exception var9) {
            throw new CertificateException("Could not decode CRLReference extension.");
         }
      }
   }

   /** @deprecated */
   public String getURL() {
      return this.crlUrl;
   }

   /** @deprecated */
   public void setURL(String var1) {
      if (var1 == null) {
         this.crlUrl = null;
      } else {
         this.crlUrl = var1;
      }

   }

   /** @deprecated */
   public byte[] getCRLNumber() {
      return this.crlNum;
   }

   /** @deprecated */
   public void setCRLNumber(byte[] var1, int var2, int var3) {
      if (this.crlNum != null && var3 != 0) {
         this.crlNum = new byte[var3];
         System.arraycopy(var1, var2, this.crlNum, 0, var3);
         this.crlNumLen = var3;
      } else {
         this.crlNum = null;
         this.crlNumLen = 0;
      }

   }

   /** @deprecated */
   public Date getCRLTime() {
      return this.crlTime;
   }

   /** @deprecated */
   public void setCRLTime(Date var1) {
      this.crlTime = var1 == null ? null : new Date(var1.getTime());
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      CRLReference var1 = new CRLReference();
      if (this.crlUrl != null) {
         var1.crlUrl = this.crlUrl;
      }

      if (this.crlTime != null) {
         var1.crlTime = new Date(this.crlTime.getTime());
      }

      if (this.crlNum != null) {
         var1.crlNumLen = this.crlNumLen;
         var1.crlNum = new byte[var1.crlNumLen];
         System.arraycopy(this.crlNum, 0, var1.crlNum, 0, var1.crlNumLen);
      }

      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }
}
