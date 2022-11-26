package com.rsa.certj.spi.pki;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.asn1.UTF8StringContainer;

/** @deprecated */
public class PKIStatusInfo {
   /** @deprecated */
   public static final int PKI_STATUS_GRANTED = 0;
   /** @deprecated */
   public static final int PKI_STATUS_GRANTED_MODS = 1;
   /** @deprecated */
   public static final int PKI_STATUS_REJECTED = 2;
   /** @deprecated */
   public static final int PKI_STATUS_WAITING = 3;
   /** @deprecated */
   public static final int PKI_STATUS_WARNING_REVOCATION = 4;
   /** @deprecated */
   public static final int PKI_REVOCATION = 5;
   /** @deprecated */
   public static final int PKI_STATUS_REVOCATION = 5;
   /** @deprecated */
   public static final int PKI_STATUS_WARNING_KEY_UPDATE = 6;
   /** @deprecated */
   public static final int PKI_FAIL_BAD_ALG = Integer.MIN_VALUE;
   /** @deprecated */
   public static final int PKI_FAIL_BAD_MESSAGE_CHECK = 1073741824;
   /** @deprecated */
   public static final int PKI_FAIL_BAD_REQUEST = 536870912;
   /** @deprecated */
   public static final int PKI_FAIL_BAD_TIME = 268435456;
   /** @deprecated */
   public static final int PKI_FAIL_BAD_CERT_ID = 134217728;
   /** @deprecated */
   public static final int PKI_FAIL_BAD_DATA_FORMAT = 67108864;
   /** @deprecated */
   public static final int PKI_FAIL_WRONG_AUTHORITY = 33554432;
   /** @deprecated */
   public static final int PKI_FAIL_INCORRECT_DATA = 16777216;
   /** @deprecated */
   public static final int PKI_FAIL_MISSING_TIMESTAMP = 8388608;
   /** @deprecated */
   public static final int PKI_FAIL_BAD_POP = 4194304;
   /** @deprecated */
   public static final int PKI_FAIL_SERVER_ERROR = 2097152;
   /** @deprecated */
   public static final int PKI_FAIL_REMOTE_SERVER_ERROR = 1048576;
   /** @deprecated */
   public static final int PKI_FAIL_CERT_REVOKED = 524288;
   /** @deprecated */
   public static final int PKI_FAIL_CERT_CONFIRMED = 262144;
   /** @deprecated */
   public static final int PKI_FAIL_WRONG_INTEGRITY = 131072;
   /** @deprecated */
   public static final int PKI_FAIL_BAD_RECIPIENT_NONCE = 65536;
   /** @deprecated */
   public static final int PKI_FAIL_TIME_NOT_AVALIABLE = 32768;
   /** @deprecated */
   public static final int PKI_FAIL_UNACCEPTED_POLICY = 16384;
   /** @deprecated */
   public static final int PKI_FAIL_UNACCEPTED_EXTENSION = 8192;
   /** @deprecated */
   public static final int PKI_FAIL_ADD_INFO_NOT_AVAILABLE = 4096;
   /** @deprecated */
   public static final int PKI_FAIL_BAD_SENDER_NONCE = 2048;
   /** @deprecated */
   public static final int PKI_FAIL_BAD_CERT_TEMPLATE = 1024;
   /** @deprecated */
   public static final int PKI_FAIL_SIGNER_NOT_TRUSTED = 512;
   /** @deprecated */
   public static final int PKI_FAIL_TRANSACTION_ID_IN_USE = 256;
   /** @deprecated */
   public static final int PKI_FAIL_UNSUPPORTED_VERSION = 128;
   private int status;
   private int failInfo;
   private String[] statusStrings;
   private int failInfoAux;
   private ASN1Template asn1Template;

   /** @deprecated */
   public PKIStatusInfo(byte[] var1, int var2, int var3) throws PKIException {
      IntegerContainer var4;
      OfContainer var5;
      BitStringContainer var6;
      try {
         SequenceContainer var7 = new SequenceContainer(var3);
         var4 = new IntegerContainer(0);
         var5 = new OfContainer(65536, 12288, new EncodedContainer(3072));
         var6 = new BitStringContainer(65536);
         EndContainer var8 = new EndContainer();
         ASN1Container[] var9 = new ASN1Container[]{var7, var4, var5, var6, var8};
         ASN1.berDecode(var1, var2, var9);
      } catch (ASN_Exception var13) {
         throw new PKIException("PKIStatusInfo.PKIStatusInfo: decoding PKIStatusInfo failed.", var13);
      }

      try {
         this.status = var4.getValueAsInt();
      } catch (ASN_Exception var12) {
         throw new PKIException("CertResponse.CertResponse: unable to get PKIStatusInfo.status as int.", var12);
      }

      if (var5.dataPresent) {
         int var15 = var5.getContainerCount();
         this.statusStrings = new String[var15];

         for(int var16 = 0; var16 < var15; ++var16) {
            try {
               ASN1Container var17 = var5.containerAt(var16);
               UTF8StringContainer var10 = new UTF8StringContainer(0);
               ASN1Container[] var11 = new ASN1Container[]{var10};
               ASN1.berDecode(var17.data, var17.dataOffset, var11);
               if (var10.dataPresent && var10.dataLen != 0) {
                  this.statusStrings[var16] = new String(var10.data, var10.dataOffset, var10.dataLen);
               } else {
                  this.statusStrings[var16] = null;
               }
            } catch (ASN_Exception var14) {
               throw new PKIException("PKIStatusInfo.PKIStatusInfo: unable to extract and decode a component of statusString.", var14);
            }
         }
      }

      if (var6.dataPresent) {
         this.failInfo = this.bitStringToInt(var6);
      }

   }

   /** @deprecated */
   public PKIStatusInfo(int var1, int var2, String[] var3, int var4) {
      this.status = var1;
      this.failInfo = var2;
      this.statusStrings = var3;
      this.failInfoAux = var4;
   }

   /** @deprecated */
   public int getStatus() {
      return this.status;
   }

   /** @deprecated */
   public int getFailInfo() {
      return this.failInfo;
   }

   /** @deprecated */
   public String[] getStatusStrings() {
      return this.statusStrings == null ? null : (String[])((String[])this.statusStrings.clone());
   }

   /** @deprecated */
   public int getFailInfoAux() {
      return this.failInfoAux;
   }

   /** @deprecated */
   public int getDERLen(int var1) throws PKIException {
      try {
         SequenceContainer var2 = new SequenceContainer(var1, true, 0);
         IntegerContainer var3 = new IntegerContainer(0, true, 0, this.status);
         OfContainer var4;
         if (this.statusStrings == null) {
            var4 = new OfContainer(65536, false, 0, 12288, (ASN1Container)null);
         } else {
            var4 = new OfContainer(65536, true, 0, 12288, new UTF8StringContainer(0));

            for(int var5 = 0; var5 < this.statusStrings.length; ++var5) {
               byte[] var6 = this.statusStrings[var5].getBytes();

               try {
                  var4.addContainer(new UTF8StringContainer(0, true, 0, var6, 0, var6.length));
               } catch (ASN_Exception var8) {
                  throw new PKIException("PKIStatusInfo.getDERLen: failed to add an element of statusString.", var8);
               }
            }
         }

         BitStringContainer var10;
         if (this.failInfo == 0) {
            var10 = new BitStringContainer(65536, false, 0, 0, 0, false);
         } else {
            var10 = new BitStringContainer(65536, true, 0, this.failInfo, 32, false);
         }

         EndContainer var11 = new EndContainer();
         ASN1Container[] var7 = new ASN1Container[]{var2, var3, var4, var10, var11};
         this.asn1Template = new ASN1Template(var7);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var9) {
         this.asn1Template = null;
         throw new PKIException("PKIStatusInfo.getDERLen: failed to encode PKIStatusInfo.", var9);
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws PKIException {
      if (var1 == null) {
         throw new PKIException("PKIStatus.getDEREncoding: der should not be null.");
      } else if (var2 < 0) {
         throw new PKIException("PKIStatus.getDEREncoding: offset should not be a negative number.");
      } else if (this.asn1Template == null && var1.length - var2 < this.getDERLen(var3)) {
         throw new PKIException("PKIStatus.getDEREncoding: der is too small to hold the DER-encoding. Use getDERLen to find out how big it should be.");
      } else {
         int var4;
         try {
            var4 = this.asn1Template.derEncode(var1, var2);
         } catch (ASN_Exception var8) {
            throw new PKIException("PKIStatus.getDEREncoding: unable to encode PKIStatusInfo.", var8);
         } finally {
            this.asn1Template = null;
         }

         return var4;
      }
   }

   private int bitStringToInt(BitStringContainer var1) {
      int var2 = 0;

      int var3;
      for(var3 = 0; var3 < var1.dataLen; ++var3) {
         var2 = (var2 << 8) + var1.data[var1.dataOffset + var3];
      }

      for(var3 = 0; var3 < 4 - var1.dataLen; ++var3) {
         var2 <<= 8;
      }

      return var2;
   }
}
