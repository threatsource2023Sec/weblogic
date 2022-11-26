package org.python.bouncycastle.cert.ocsp;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERGeneralizedTime;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.ocsp.BasicOCSPResponse;
import org.python.bouncycastle.asn1.ocsp.CertStatus;
import org.python.bouncycastle.asn1.ocsp.ResponseData;
import org.python.bouncycastle.asn1.ocsp.RevokedInfo;
import org.python.bouncycastle.asn1.ocsp.SingleResponse;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.CRLReason;
import org.python.bouncycastle.asn1.x509.Extensions;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.operator.ContentSigner;
import org.python.bouncycastle.operator.DigestCalculator;

public class BasicOCSPRespBuilder {
   private List list = new ArrayList();
   private Extensions responseExtensions = null;
   private RespID responderID;

   public BasicOCSPRespBuilder(RespID var1) {
      this.responderID = var1;
   }

   public BasicOCSPRespBuilder(SubjectPublicKeyInfo var1, DigestCalculator var2) throws OCSPException {
      this.responderID = new RespID(var1, var2);
   }

   public BasicOCSPRespBuilder addResponse(CertificateID var1, CertificateStatus var2) {
      this.addResponse(var1, var2, new Date(), (Date)null, (Extensions)null);
      return this;
   }

   public BasicOCSPRespBuilder addResponse(CertificateID var1, CertificateStatus var2, Extensions var3) {
      this.addResponse(var1, var2, new Date(), (Date)null, var3);
      return this;
   }

   public BasicOCSPRespBuilder addResponse(CertificateID var1, CertificateStatus var2, Date var3, Extensions var4) {
      this.addResponse(var1, var2, new Date(), var3, var4);
      return this;
   }

   public BasicOCSPRespBuilder addResponse(CertificateID var1, CertificateStatus var2, Date var3, Date var4) {
      this.addResponse(var1, var2, var3, var4, (Extensions)null);
      return this;
   }

   public BasicOCSPRespBuilder addResponse(CertificateID var1, CertificateStatus var2, Date var3, Date var4, Extensions var5) {
      this.list.add(new ResponseObject(var1, var2, var3, var4, var5));
      return this;
   }

   public BasicOCSPRespBuilder setResponseExtensions(Extensions var1) {
      this.responseExtensions = var1;
      return this;
   }

   public BasicOCSPResp build(ContentSigner var1, X509CertificateHolder[] var2, Date var3) throws OCSPException {
      Iterator var4 = this.list.iterator();
      ASN1EncodableVector var5 = new ASN1EncodableVector();

      while(var4.hasNext()) {
         try {
            var5.add(((ResponseObject)var4.next()).toResponse());
         } catch (Exception var13) {
            throw new OCSPException("exception creating Request", var13);
         }
      }

      ResponseData var6 = new ResponseData(this.responderID.toASN1Primitive(), new ASN1GeneralizedTime(var3), new DERSequence(var5), this.responseExtensions);

      DERBitString var8;
      try {
         OutputStream var7 = var1.getOutputStream();
         var7.write(var6.getEncoded("DER"));
         var7.close();
         var8 = new DERBitString(var1.getSignature());
      } catch (Exception var12) {
         throw new OCSPException("exception processing TBSRequest: " + var12.getMessage(), var12);
      }

      AlgorithmIdentifier var14 = var1.getAlgorithmIdentifier();
      DERSequence var9 = null;
      if (var2 != null && var2.length > 0) {
         ASN1EncodableVector var10 = new ASN1EncodableVector();

         for(int var11 = 0; var11 != var2.length; ++var11) {
            var10.add(var2[var11].toASN1Structure());
         }

         var9 = new DERSequence(var10);
      }

      return new BasicOCSPResp(new BasicOCSPResponse(var6, var14, var8, var9));
   }

   private class ResponseObject {
      CertificateID certId;
      CertStatus certStatus;
      ASN1GeneralizedTime thisUpdate;
      ASN1GeneralizedTime nextUpdate;
      Extensions extensions;

      public ResponseObject(CertificateID var2, CertificateStatus var3, Date var4, Date var5, Extensions var6) {
         this.certId = var2;
         if (var3 == null) {
            this.certStatus = new CertStatus();
         } else if (var3 instanceof UnknownStatus) {
            this.certStatus = new CertStatus(2, DERNull.INSTANCE);
         } else {
            RevokedStatus var7 = (RevokedStatus)var3;
            if (var7.hasRevocationReason()) {
               this.certStatus = new CertStatus(new RevokedInfo(new ASN1GeneralizedTime(var7.getRevocationTime()), CRLReason.lookup(var7.getRevocationReason())));
            } else {
               this.certStatus = new CertStatus(new RevokedInfo(new ASN1GeneralizedTime(var7.getRevocationTime()), (CRLReason)null));
            }
         }

         this.thisUpdate = new DERGeneralizedTime(var4);
         if (var5 != null) {
            this.nextUpdate = new DERGeneralizedTime(var5);
         } else {
            this.nextUpdate = null;
         }

         this.extensions = var6;
      }

      public SingleResponse toResponse() throws Exception {
         return new SingleResponse(this.certId.toASN1Primitive(), this.certStatus, this.thisUpdate, this.nextUpdate, this.extensions);
      }
   }
}
