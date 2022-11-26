package com.rsa.certj.provider.revocation.ocsp;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.EnumeratedContainer;
import com.rsa.asn1.GenTimeContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.ExtendedKeyUsage;
import com.rsa.certj.cert.extensions.OCSPAcceptableResponses;
import com.rsa.certj.cert.extensions.OCSPNonce;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.revocation.CertRevocationInfo;
import com.rsa.certj.spi.revocation.CertStatusException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Parameters;
import com.rsa.jsafe.JSAFE_Signature;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Vector;

/** @deprecated */
public final class OCSPResponse {
   private CertJ certJ;
   private DatabaseService database;
   private byte[] nonce;
   private String sigAlg;
   private OCSPCertID[] certIDs;
   private X509Certificate checkCert;
   private X509Certificate caCert;
   private X509Certificate designatedResponder;
   private X509Certificate actualResponder;
   private Vector potentialResponderCerts;
   private long validationTime;
   private int tolerance;
   private CertRevocationInfo[] revInfos;
   private boolean responderNoCheck;
   private static final int OCSP_STATUS_SUCCESSFUL = 0;
   private static final int OCSP_STATUS_MALFORMED_REQUEST = 1;
   private static final int OCSP_STATUS_INTERNAL_ERROR = 2;
   private static final int OCSP_STATUS_TRY_LATER = 3;
   private static final int OCSP_STATUS_SIG_REQUIRED = 5;
   private static final int OCSP_STATUS_UNAUTHORIZED = 6;
   private static final int NO_SPECIAL = 0;
   private static final int ZERO_OFFSET = 0;

   OCSPResponse(CertJ var1, OCSPResponderInternal var2, X509Certificate var3) {
      this.certJ = var1;
      this.checkCert = var3;
      this.designatedResponder = var2.getResponderCert();
      this.tolerance = var2.getTimeTolerance();
      this.database = var2.getDatabase();
      this.caCert = var2.getResponderCACert(var3);
      this.responderNoCheck = (var2.getFlags() & 8) != 0;
   }

   CertRevocationInfo getRevocationInfo(OCSPCertID var1) throws NotSupportedException {
      if (this.certIDs == null) {
         return null;
      } else {
         byte[] var2 = var1.encode();

         for(int var3 = 0; var3 < this.certIDs.length; ++var3) {
            if (CertJUtils.byteArraysEqual(this.certIDs[var3].encode(), var2)) {
               return this.revInfos[var3];
            }
         }

         return null;
      }
   }

   byte[] getNonce() {
      return this.nonce;
   }

   private void checkStatus(int var1) throws CertStatusException {
      switch (var1) {
         case 0:
            return;
         case 1:
            throw new CertStatusException("OCSP_STATUS_MALFORMED_REQUEST");
         case 2:
            throw new CertStatusException("OCSP_STATUS_INTERNAL_ERROR");
         case 3:
            throw new CertStatusException("OCSP_STATUS_TRY_LATER");
         case 4:
         default:
            throw new CertStatusException("OCSP_STATUS UNKNOWN!!!");
         case 5:
            throw new CertStatusException("OCSP_STATUS_SIG_REQUIRED");
         case 6:
            throw new CertStatusException("OCSP_STATUS_UNAUTHORIZED");
      }
   }

   private boolean verifySignature(Certificate var1, String var2, byte[] var3, int var4, int var5, byte[] var6, int var7, int var8) {
      boolean var9;
      try {
         String var10 = this.certJ.getDevice();
         JSAFE_Signature var11 = h.b(var2, var10, this.certJ);
         var11.verifyInit(var1.getSubjectPublicKey(var10), (JSAFE_Parameters)null, (SecureRandom)null, this.certJ.getPKCS11Sessions());
         var11.verifyUpdate(var3, var4, var5);
         byte[] var12 = new byte[var8];
         System.arraycopy(var6, var7, var12, 0, var8);
         var9 = var11.verifyFinal(var12, 0, var8);
         var11.clearSensitiveData();
      } catch (Exception var13) {
         var9 = false;
      }

      return var9;
   }

   void decode(CertPathCtx var1, byte[] var2, OCSPRequest var3) throws ASN_Exception, CertStatusException {
      EndContainer var5 = new EndContainer();
      SequenceContainer var6 = new SequenceContainer(0);
      EnumeratedContainer var7 = new EnumeratedContainer(0);
      EncodedContainer var8 = new EncodedContainer(10563584);
      SequenceContainer var9 = new SequenceContainer(10551296);
      OIDContainer var10 = new OIDContainer(16777216);
      OctetStringContainer var11 = new OctetStringContainer(0);
      ASN1Container[] var12 = new ASN1Container[]{var6, var7, var8, var5};
      ASN1Container[] var13 = new ASN1Container[]{var9, var10, var11, var5};
      Date var14 = var1.getValidationTime();
      this.validationTime = var14 == null ? System.currentTimeMillis() : var14.getTime();
      ASN1.berDecode(var2, 0, var12);
      int var4 = var7.getValueAsInt();
      this.checkStatus(var4);
      if (!var8.dataPresent) {
         throw new CertStatusException("OCSPResponse: missing status=OK responseBytes!");
      } else {
         ASN1.berDecode(var8.data, var8.dataOffset, var13);
         if (!CertJUtils.byteArraysEqual(var10.data, var10.dataOffset, var10.dataLen, OCSPAcceptableResponses.ID_PKIX_OCSP_BASIC)) {
            throw new CertStatusException("!ID_PKIX_OCSP_BASIC");
         } else {
            this.decodeBasicResponse(var1, var11.data, var11.dataOffset);
         }
      }
   }

   private void decodeBasicResponse(CertPathCtx var1, byte[] var2, int var3) throws ASN_Exception, CertStatusException {
      EndContainer var4 = new EndContainer();
      SequenceContainer var5 = new SequenceContainer(0);
      EncodedContainer var6 = new EncodedContainer(12288);
      EncodedContainer var7 = new EncodedContainer(65280);
      BitStringContainer var8 = new BitStringContainer(0);
      OfContainer var9 = new OfContainer(10551296, 12288, new EncodedContainer(12288));
      ASN1Container[] var10 = new ASN1Container[]{var5, var6, var7, var8, var9, var4};
      SequenceContainer var11 = new SequenceContainer(0);
      OIDContainer var12 = new OIDContainer(16777216);
      EncodedContainer var13 = new EncodedContainer(65536);
      ASN1Container[] var14 = new ASN1Container[]{var11, var12, var13, var4};
      ASN1.berDecode(var2, var3, var10);
      ASN1.berDecode(var7.data, var7.dataOffset, var14);

      try {
         JSAFE_Signature var15 = h.b(var7.data, var7.dataOffset, "Java", this.certJ);
         this.sigAlg = var15.getDigestAlgorithm() + "/" + var15.getSignatureAlgorithm() + "/" + var15.getPaddingScheme();
         if (this.database != null) {
            for(int var16 = 0; var16 < var9.getContainerCount(); ++var16) {
               ASN1Container var17 = var9.containerAt(var16);
               X509Certificate var18 = new X509Certificate(var17.data, var17.dataOffset, var17.dataLen);
               this.database.insertCertificate(var18);
            }
         }

         this.decodeResponseData(var1, var6.data, var6.dataOffset);
      } catch (CertStatusException var19) {
         throw var19;
      } catch (Exception var20) {
         throw new CertStatusException(var20);
      }

      if (!this.verifyResponse(var1, var6, var8)) {
         throw new CertStatusException("Unable to verify identity of responder");
      }
   }

   private void decodeResponseData(CertPathCtx var1, byte[] var2, int var3) throws ASN_Exception, NoServiceException, CertStatusException, NotSupportedException {
      EndContainer var4 = new EndContainer();
      SequenceContainer var5 = new SequenceContainer(0);
      IntegerContainer var6 = new IntegerContainer(10616832);
      EncodedContainer var7 = new EncodedContainer(65280);
      GenTimeContainer var8 = new GenTimeContainer(0);
      OfContainer var9 = new OfContainer(0, 12288, new EncodedContainer(12288));
      EncodedContainer var10 = new EncodedContainer(130816);
      ASN1Container[] var11 = new ASN1Container[]{var5, var6, var7, var8, var9, var10, var4};
      ASN1.berDecode(var2, var3, var11);
      if (var6.dataPresent && var6.getValueAsInt() != 0) {
         throw new CertStatusException("0 != " + var6.getValueAsInt());
      } else {
         this.decodeResponderID(var1.getDatabase(), var7.data, var7.dataOffset);
         Date var12 = new Date(var8.theTime.getTime());
         this.decodeSingleResponses(var9, var12);

         try {
            if (var10.dataPresent) {
               X509V3Extensions var13 = new X509V3Extensions(var10.data, var10.dataOffset, 10485761, 5);
               int var15 = var13.getExtensionCount();

               for(int var14 = 0; var14 < var15; ++var14) {
                  X509V3Extension var16 = var13.getExtensionByIndex(var14);
                  int var17 = var16.getExtensionType();
                  if (var16.getCriticality()) {
                     if (var17 != 120) {
                        throw new CertStatusException("unknown critical OCSP response extension");
                     }
                  } else if (var17 == 120) {
                     OCSPNonce var18 = (OCSPNonce)var16;
                     this.nonce = var18.getNonceValue();
                  }
               }
            }

         } catch (CertificateException var19) {
            throw new CertStatusException(var19);
         }
      }
   }

   private void decodeResponderID(DatabaseService var1, byte[] var2, int var3) throws ASN_Exception, NoServiceException, CertStatusException {
      EndContainer var4 = new EndContainer();
      ChoiceContainer var5 = new ChoiceContainer(0);
      EncodedContainer var6 = new EncodedContainer(10498049);
      EncodedContainer var7 = new EncodedContainer(10551042);
      ASN1Container[] var8 = new ASN1Container[]{var5, var6, var7, var4};
      ASN1.berDecode(var2, var3, var8);
      this.potentialResponderCerts = new Vector();

      try {
         if (var6.dataPresent) {
            X500Name var9 = new X500Name(var6.data, var6.dataOffset, 10485761);
            var1.selectCertificateBySubject(var9, this.potentialResponderCerts);
         } else {
            if (!var7.dataPresent) {
               throw new CertStatusException("unknown ResponderID CHOICE");
            }

            OctetStringContainer var15 = new OctetStringContainer(10485762);
            ASN1Container[] var10 = new ASN1Container[]{var15};
            ASN1.berDecode(var2, var3, var10);
            OCSPutil.selectCertificateByKeyHash(this.certJ, var1, var15.data, var15.dataOffset, var15.dataLen, this.potentialResponderCerts);
         }

      } catch (CertificateException var11) {
         throw new CertStatusException(var11);
      } catch (NameException var12) {
         throw new CertStatusException(var12);
      } catch (DatabaseException var13) {
         throw new CertStatusException(var13);
      } catch (InvalidParameterException var14) {
         throw new CertStatusException(var14);
      }
   }

   private OCSPRevocationInfo decodeRevocationInfo(byte[] var1, int var2, CertRevocationInfo var3) throws ASN_Exception, NotSupportedException {
      ChoiceContainer var4 = new ChoiceContainer(0);
      EncodedContainer var5 = new EncodedContainer(8389888);
      EncodedContainer var6 = new EncodedContainer(8400897);
      EncodedContainer var7 = new EncodedContainer(8389890);
      EndContainer var8 = new EndContainer();
      ASN1Container[] var9 = new ASN1Container[]{var4, var5, var6, var7, var8};
      OCSPRevocationInfo var10 = null;
      ASN1.berDecode(var1, var2, var9);
      if (var5.dataPresent) {
         var3.setStatus(0);
      } else if (var6.dataPresent) {
         SequenceContainer var11 = new SequenceContainer(8388609);
         GenTimeContainer var12 = new GenTimeContainer(0);
         EncodedContainer var13 = new EncodedContainer(10616576);
         ASN1Container[] var14 = new ASN1Container[]{var11, var12, var13, var8};
         ASN1.berDecode(var6.data, var6.dataOffset, var14);
         var10 = new OCSPRevocationInfo(var12.theTime.getTime());
         if (var13.dataPresent) {
            EnumeratedContainer var15 = new EnumeratedContainer(10551040);
            ASN1Container[] var16 = new ASN1Container[]{var15};
            ASN1.berDecode(var13.data, var13.dataOffset, var16);
            var10.setReasonCode(var15.getValueAsInt());
         }

         var3.setStatus(1);
      } else {
         if (!var7.dataPresent) {
            throw new NotSupportedException("CertStatus");
         }

         var3.setStatus(2);
      }

      return var10;
   }

   private boolean checkTime(long var1, int var3, OCSPEvidence var4) {
      long var5 = var4.getThisUpdate().getTime() - (long)var3 * 1000L;
      if (var1 < var5) {
         return false;
      } else {
         Date var7 = var4.getNextUpdate();
         return var7 == null || var1 <= var7.getTime() + (long)var3 * 1000L;
      }
   }

   private void decodeSingleResponses(OfContainer var1, Date var2) throws ASN_Exception, NotSupportedException {
      EndContainer var3 = new EndContainer();
      SequenceContainer var4 = new SequenceContainer(0);
      EncodedContainer var5 = new EncodedContainer(65280);
      EncodedContainer var6 = new EncodedContainer(65280);
      GenTimeContainer var7 = new GenTimeContainer(0);
      GenTimeContainer var8 = new GenTimeContainer(10551296);
      EncodedContainer var9 = new EncodedContainer(130816);
      ASN1Container[] var10 = new ASN1Container[]{var4, var5, var6, var7, var8, var9, var3};
      int var11 = var1.getContainerCount();
      this.certIDs = new OCSPCertID[var11];
      this.revInfos = new CertRevocationInfo[var11];

      for(int var12 = 0; var12 < var11; ++var12) {
         ASN1Container var13 = var1.containerAt(var12);
         ASN1.berDecode(var13.data, var13.dataOffset, var10);
         this.revInfos[var12] = new CertRevocationInfo();
         OCSPRevocationInfo var14 = this.decodeRevocationInfo(var6.data, var6.dataOffset, this.revInfos[var12]);

         try {
            Date var15 = new Date(var7.theTime.getTime());
            Date var16 = var8.dataPresent ? new Date(var8.theTime.getTime()) : null;
            X509V3Extensions var17;
            if (var9.dataPresent && var9.data != null) {
               var17 = new X509V3Extensions(var9.data, var9.dataOffset, 10485761, 4);
               int var18 = var17.getExtensionCount();

               for(int var19 = 0; var19 < var18; ++var19) {
                  X509V3Extension var20 = var17.getExtensionByIndex(var19);
                  if (var20.isExtensionType(-1) && var20.getCriticality()) {
                     throw new NotSupportedException("Unknown critical extension.");
                  }
               }
            } else {
               var17 = null;
            }

            OCSPEvidence var23 = new OCSPEvidence(0, var2, var15, var16, var17, var14);
            this.revInfos[var12].setEvidence(var23);
            this.revInfos[var12].setType(2);
            this.certIDs[var12] = new OCSPCertID(this.certJ, var5.data, var5.dataOffset, var5.dataLen);
            if (!this.checkTime(this.validationTime, this.tolerance, var23)) {
               this.revInfos[var12].setStatus(2);
            }
         } catch (CertificateException var21) {
            throw new NotSupportedException(var21);
         } catch (InvalidParameterException var22) {
            throw new NotSupportedException(var22);
         }
      }

   }

   private boolean verifyResponse(CertPathCtx var1, ASN1Container var2, ASN1Container var3) {
      boolean var4 = false;
      CertPathCtx var5 = var1;

      for(int var6 = 0; var6 < this.potentialResponderCerts.size(); ++var6) {
         this.actualResponder = (X509Certificate)this.potentialResponderCerts.elementAt(0);
         if (this.verifySignature(this.actualResponder, this.sigAlg, var2.data, var2.dataOffset, var2.dataLen, var3.data, var3.dataOffset, var3.dataLen)) {
            try {
               if (this.checkCert.equals(this.actualResponder)) {
                  if (!this.responderNoCheck) {
                     return false;
                  }

                  var5 = new CertPathCtx(var1.getPathOptions() | 4, var1.getTrustedCerts(), var1.getPolicies(), var1.getValidationTime(), var1.getDatabase());
               }

               var4 = this.certJ.buildCertPath(var5, this.actualResponder, (Vector)null, (Vector)null, (Vector)null, (Vector)null);
            } catch (CertJException var8) {
               var4 = false;
            }

            if (var4) {
               if (this.actualResponder.equals(this.caCert)) {
                  return true;
               }

               if (this.isOCSPDelegatedResponder(this.actualResponder, this.caCert)) {
                  return true;
               }

               if (this.actualResponder.equals(this.designatedResponder)) {
                  return true;
               }

               var4 = false;
            }
         }
      }

      return var4;
   }

   private boolean isOCSPDelegatedResponder(X509Certificate var1, X509Certificate var2) {
      X509V3Extensions var3 = var1.getExtensions();
      if (var3 != null && var3.getExtensionCount() != 0) {
         try {
            ExtendedKeyUsage var4 = (ExtendedKeyUsage)var3.getExtensionByType(37);
            if (var4 == null) {
               return false;
            }

            for(int var5 = 0; var5 < var4.getKeyUsageCount(); ++var5) {
               if (CertJUtils.byteArraysEqual(ExtendedKeyUsage.ID_KP_OCSP_SIGNING, var4.getExtendedKeyUsage(var5))) {
                  return true;
               }
            }
         } catch (CertificateException var6) {
            return false;
         }

         return this.actualResponder.getIssuerName().equals(var2.getSubjectName());
      } else {
         return false;
      }
   }
}
