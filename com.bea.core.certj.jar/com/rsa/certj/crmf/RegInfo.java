package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.asn1.UTF8StringContainer;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Vector;

/** @deprecated */
public class RegInfo implements Serializable, Cloneable {
   /** @deprecated */
   public static final byte[] REG_INFO_OID = new byte[]{43, 6, 1, 5, 5, 7, 5, 2, 1};
   private String valueString;
   private Vector regInfoSeq = new Vector();
   /** @deprecated */
   protected int special;
   /** @deprecated */
   protected ASN1Template asn1Template;

   /** @deprecated */
   public RegInfo() {
   }

   /** @deprecated */
   public RegInfo(String var1, String var2) throws CRMFException {
      this.addNameValuePair(var1, var2, false);
   }

   /** @deprecated */
   public RegInfo(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("RegInfo Encoding is null.");
      } else {
         this.special = var3;

         try {
            OfContainer var4 = new OfContainer(var3, 12288, new EncodedContainer(12288));
            ASN1Container[] var5 = new ASN1Container[]{var4};
            ASN1.berDecode(var1, var2, var5);
            int var6 = var4.getContainerCount();

            for(int var7 = 0; var7 < var6; ++var7) {
               ASN1Container var8 = var4.containerAt(var7);
               SequenceContainer var9 = new SequenceContainer(0);
               EndContainer var10 = new EndContainer();
               OIDContainer var11 = new OIDContainer(16777216);
               UTF8StringContainer var12 = new UTF8StringContainer(0);
               ASN1Container[] var13 = new ASN1Container[]{var9, var11, var12, var10};
               ASN1.berDecode(var8.data, var8.dataOffset, var13);
               byte[] var14 = new byte[var11.dataLen];
               System.arraycopy(var11.data, var11.dataOffset, var14, 0, var11.dataLen);
               if (!CertJUtils.byteArraysEqual(var14, REG_INFO_OID)) {
                  throw new CRMFException("Wrong OID");
               }

               if (var12.data != null) {
                  this.valueString = new String(var12.data, var12.dataOffset, var12.dataLen);
                  this.regInfoSeq.addElement(this.valueString);
               }
            }

         } catch (ASN_Exception var15) {
            throw new CRMFException("Cannot read the BER of the regInfo.", var15);
         }
      }
   }

   /** @deprecated */
   public void addNameValuePair(String var1, String var2, boolean var3) throws CRMFException {
      if (var1 != null && var2 != null) {
         this.valueString = this.urlEncode(var1);
         this.valueString = this.valueString.concat("?");
         this.valueString = this.valueString.concat(this.urlEncode(var2));
         this.valueString = this.valueString.concat("%");
         if (!var3 && !this.regInfoSeq.isEmpty()) {
            String var4 = (String)this.regInfoSeq.lastElement();
            this.regInfoSeq.removeElement(var4);
            var4 = var4.concat(this.valueString);
            this.valueString = var4;
         }

         this.regInfoSeq.addElement(this.valueString);
         this.valueString = null;
      } else {
         throw new CRMFException("Passed in RegInfo values are null.");
      }
   }

   /** @deprecated */
   public String getAttribute(int var1) throws CRMFException {
      if (this.getRegInfoCount() > var1) {
         return (String)this.regInfoSeq.elementAt(var1);
      } else {
         throw new CRMFException("The specified RegInfo index is invalid.");
      }
   }

   /** @deprecated */
   public int getRegInfoCount() {
      return this.regInfoSeq.size();
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 == null) {
         throw new CRMFException("RegInfo Encoding is null.");
      } else if (var0[var1] == 0 && var0[var1 + 1] == 0) {
         return var1 + 2;
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new CRMFException("Unable to determine length of the RegInfo BER", var3);
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws CRMFException {
      return this.encodeInit(var1);
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Specified RegInfo array is null.");
      } else {
         try {
            if (this.asn1Template == null || this.special != var3) {
               this.getDERLen(var3);
            }

            int var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new CRMFException("Unable to encode RegInfo.", var6);
         }
      }
   }

   private int encodeInit(int var1) throws CRMFException {
      this.special = var1;
      Vector var2 = new Vector();

      try {
         OfContainer var3 = new OfContainer(var1, true, 0, 12288, new EncodedContainer(12288));
         var2.addElement(var3);
         int var4 = 0;
         if (this.regInfoSeq != null) {
            var4 = this.regInfoSeq.size();
         }

         for(int var5 = 0; var5 < var4; ++var5) {
            this.valueString = (String)this.regInfoSeq.elementAt(var5);
            byte[] var6 = this.valueString.getBytes();
            SequenceContainer var7 = new SequenceContainer(var1, true, 0);
            EndContainer var8 = new EndContainer();
            OIDContainer var9 = new OIDContainer(16777216, true, 0, REG_INFO_OID, 0, REG_INFO_OID.length);
            UTF8StringContainer var10 = new UTF8StringContainer(0, true, 0, var6, 0, var6.length);
            ASN1Container[] var11 = new ASN1Container[]{var7, var9, var10, var8};
            this.asn1Template = new ASN1Template(var11);
            int var12 = this.asn1Template.derEncodeInit();
            byte[] var13 = new byte[var12];
            var12 = this.asn1Template.derEncode(var13, 0);
            EncodedContainer var14 = new EncodedContainer(0, true, 0, var13, 0, var12);
            var3.addContainer(var14);
         }

         ASN1Container[] var16 = new ASN1Container[var2.size()];
         var2.copyInto(var16);
         this.asn1Template = new ASN1Template(var16);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var15) {
         throw new CRMFException(var15);
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof RegInfo) {
         RegInfo var2 = (RegInfo)var1;
         int var3 = this.regInfoSeq.size();
         int var4 = var2.regInfoSeq.size();
         if (var3 != var4) {
            return false;
         } else {
            for(int var5 = 0; var5 < var3; ++var5) {
               if (!((String)this.regInfoSeq.elementAt(var5)).equals(var2.regInfoSeq.elementAt(var5))) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      return 31 + CertJInternalHelper.hashCodeValue(this.regInfoSeq);
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      RegInfo var1 = new RegInfo();
      var1.regInfoSeq = new Vector(this.regInfoSeq);

      try {
         if (this.asn1Template != null) {
            var1.encodeInit(this.special);
         }

         return var1;
      } catch (CRMFException var3) {
         throw new CloneNotSupportedException(var3.getMessage());
      }
   }

   /** @deprecated */
   private String urlEncode(String var1) {
      return URLEncoder.encode(var1);
   }
}
