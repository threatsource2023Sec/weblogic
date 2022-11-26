package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.crmf.CRMFException;
import com.rsa.certj.crmf.OldCertID;
import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIStatusInfo;
import java.util.Vector;

/** @deprecated */
public final class CMPRevokeResponseMessage extends CMPResponseCommon {
   private Vector statusArray = new Vector();
   private Vector certIdArray = new Vector();
   private X509CRL[] crls;

   private CMPRevokeResponseMessage(PKIHeader var1) {
      super(12, var1, (PKIStatusInfo)null);
   }

   static CMPRevokeResponseMessage berDecodeBody(PKIHeader var0, byte[] var1, int var2) throws CMPException {
      CMPRevokeResponseMessage var3 = new CMPRevokeResponseMessage(var0);
      var3.berDecode(var1, var2, 10551308);
      return var3;
   }

   /** @deprecated */
   public X509CRL[] getCRLs() {
      return this.crls;
   }

   /** @deprecated */
   public int getStatusCount() {
      return this.statusArray.size();
   }

   /** @deprecated */
   public PKIStatusInfo getStatusAt(int var1) throws CMPException {
      if (var1 >= this.statusArray.size()) {
         throw new CMPException("CMPrevokeResponseMessage.getStatusAt: index out of range.");
      } else {
         return (PKIStatusInfo)this.statusArray.elementAt(var1);
      }
   }

   /** @deprecated */
   public OldCertID getCertIdAt(int var1) throws CMPException {
      if (var1 >= this.statusArray.size()) {
         throw new CMPException("CMPrevokeResponseMessage.getCertIdAt: index out of range.");
      } else {
         return (OldCertID)this.certIdArray.elementAt(var1);
      }
   }

   private void berDecode(byte[] var1, int var2, int var3) throws CMPException {
      OfContainer var4;
      OfContainer var5;
      OfContainer var6;
      try {
         var4 = new OfContainer(0, 12288, new EncodedContainer(12288));
         var5 = new OfContainer(10551296, 12288, new EncodedContainer(12288));
         var6 = new OfContainer(10551297, 12288, new EncodedContainer(12288));
         ASN1Container[] var7 = new ASN1Container[]{new SequenceContainer(var3), var4, var5, var6, new EndContainer()};
         ASN1.berDecode(var1, var2, var7);
      } catch (ASN_Exception var19) {
         throw new CMPException("CMPRevokeResponseMessage.berDecode: decoding RevRepContent failed.", var19);
      }

      int var21 = var4.getContainerCount();
      int var8 = 0;
      if (var5.dataPresent) {
         var8 = var5.getContainerCount();
      }

      if (var8 != 0 && var21 < var8) {
         throw new CMPException("CMPRevokeResponseMessage.berDecode: more revCerts items than stauts items in RevRepContent.");
      } else {
         int var9;
         ASN1Container var11;
         for(var9 = 0; var9 < var21; ++var9) {
            var11 = null;

            ASN1Container var10;
            try {
               var10 = var4.containerAt(var9);
               if (var5.dataPresent && var9 < var8) {
                  var11 = var5.containerAt(var9);
               }
            } catch (ASN_Exception var20) {
               throw new CMPException("CMPRevokeResponseMessage.berDecode: unable to extract OfContainer component.", var20);
            }

            PKIStatusInfo var12;
            try {
               var12 = new PKIStatusInfo(var10.data, var10.dataOffset, 0);
            } catch (PKIException var18) {
               throw new CMPException("CMPRevokeResponseMessage.berDecode: unable to instantiate PKIStatusInfo.", var18);
            }

            OldCertID var13 = null;
            if (var11 != null) {
               try {
                  var13 = new OldCertID();
                  var13.decodeValue(var11.data, var11.dataOffset);
               } catch (CRMFException var17) {
                  throw new CMPException("CMPRevokeResponseMessage.berDecode: unable to decode CertId.", var17);
               }
            }

            this.addOneStatus(var12, var13);
         }

         if (var6.dataPresent) {
            var9 = var6.getContainerCount();
            this.crls = new X509CRL[var9];

            for(int var22 = 0; var22 < var9; ++var22) {
               try {
                  var11 = var6.containerAt(var22);
               } catch (ASN_Exception var16) {
                  throw new CMPException("CMPRevokeResponseMessage.berDecode: unable to get OfContainer component.", var16);
               }

               try {
                  this.crls[var22] = new X509CRL(var11.data, var11.dataOffset, 0);
               } catch (CertificateException var15) {
                  throw new CMPException("CMPRevokeResponseMessage.berDecode: unable to decode CRL.", var15);
               }
            }
         }

      }
   }

   private void addOneStatus(PKIStatusInfo var1, OldCertID var2) throws CMPException {
      if (var1 == null) {
         throw new CMPException("CMPrevokeResponseMessage.addOneStatus: status should not be null.");
      } else {
         this.statusArray.addElement(var1);
         this.certIdArray.addElement(var2);
      }
   }
}
