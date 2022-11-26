package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.asn1.UTF8StringContainer;
import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIStatusInfo;

/** @deprecated */
public final class CMPErrorMessage extends CMPResponseCommon {
   private boolean errorCodePresent;
   private int errorCode;
   private String[] errorDetails;

   private CMPErrorMessage(PKIHeader var1, PKIStatusInfo var2, boolean var3, int var4, String[] var5) throws CMPException {
      super(23, var1, var2);
      this.errorCodePresent = var3;
      this.errorCode = var4;
      this.errorDetails = var5;
   }

   static CMPErrorMessage berDecodeBody(PKIHeader var0, byte[] var1, int var2) throws CMPException {
      EncodedContainer var3;
      IntegerContainer var4;
      OfContainer var5;
      try {
         var3 = new EncodedContainer(12288);
         var4 = new IntegerContainer(65536);
         var5 = new OfContainer(65536, 12288, new EncodedContainer(3072));
         ASN1Container[] var6 = new ASN1Container[]{new SequenceContainer(10551319), var3, var4, var5, new EndContainer()};
         ASN1.berDecode(var1, var2, var6);
      } catch (ASN_Exception var17) {
         throw new CMPException("CMPErrorMessage.berDecodeBody: decoding ErrorMsgContent failed.", var17);
      }

      PKIStatusInfo var19;
      try {
         var19 = new PKIStatusInfo(var3.data, var3.dataOffset, 0);
      } catch (PKIException var16) {
         throw new CMPException("CMPErrorMessage$ErrorMsgContent.ErrorMsgContent: unable to instantiate PKIStatusInfo.", var16);
      }

      boolean var7 = var4.dataPresent;
      int var8 = -1;
      if (var7) {
         try {
            var8 = var4.getValueAsInt();
         } catch (ASN_Exception var15) {
            throw new CMPException("CMPErrorMessage$ErrorMsgContent.ErrorMsgContent: unable to get ErrorMsgContent.errorCode as int.", var15);
         }
      }

      String[] var9 = null;
      if (var5.dataPresent) {
         int var10 = var5.getContainerCount();
         var9 = new String[var10];

         for(int var11 = 0; var11 < var10; ++var11) {
            try {
               ASN1Container var12 = var5.containerAt(var11);
               UTF8StringContainer var13 = new UTF8StringContainer(0);
               ASN1Container[] var14 = new ASN1Container[]{var13};
               ASN1.berDecode(var12.data, var12.dataOffset, var14);
               if (var13.dataPresent && var13.dataLen != 0) {
                  var9[var11] = new String(var13.data, var13.dataOffset, var13.dataLen);
               } else {
                  var9[var11] = null;
               }
            } catch (ASN_Exception var18) {
               throw new CMPException("CMPErrorMessage$ErrorMsgContent.ErrorMsgContent: unable to extract and decode an errorDetails.", var18);
            }
         }
      }

      return new CMPErrorMessage(var0, var19, var7, var8, var9);
   }

   /** @deprecated */
   public boolean errorCodePresent() {
      return this.errorCodePresent;
   }

   /** @deprecated */
   public int getErrorCode() throws CMPException {
      if (!this.errorCodePresent) {
         throw new CMPException("CMPErrorMessage.getErrorCode: error code not returned.");
      } else {
         return this.errorCode;
      }
   }

   /** @deprecated */
   public String[] getErrorDetails() {
      return this.errorDetails;
   }
}
