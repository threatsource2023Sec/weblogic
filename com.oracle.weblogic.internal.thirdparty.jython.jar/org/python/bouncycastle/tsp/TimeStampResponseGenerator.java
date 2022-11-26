package org.python.bouncycastle.tsp;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERUTF8String;
import org.python.bouncycastle.asn1.cmp.PKIFreeText;
import org.python.bouncycastle.asn1.cmp.PKIStatusInfo;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.tsp.TimeStampResp;
import org.python.bouncycastle.asn1.x509.Extensions;

public class TimeStampResponseGenerator {
   int status;
   ASN1EncodableVector statusStrings;
   int failInfo;
   private TimeStampTokenGenerator tokenGenerator;
   private Set acceptedAlgorithms;
   private Set acceptedPolicies;
   private Set acceptedExtensions;

   public TimeStampResponseGenerator(TimeStampTokenGenerator var1, Set var2) {
      this(var1, var2, (Set)null, (Set)null);
   }

   public TimeStampResponseGenerator(TimeStampTokenGenerator var1, Set var2, Set var3) {
      this(var1, var2, var3, (Set)null);
   }

   public TimeStampResponseGenerator(TimeStampTokenGenerator var1, Set var2, Set var3, Set var4) {
      this.tokenGenerator = var1;
      this.acceptedAlgorithms = this.convert(var2);
      this.acceptedPolicies = this.convert(var3);
      this.acceptedExtensions = this.convert(var4);
      this.statusStrings = new ASN1EncodableVector();
   }

   private void addStatusString(String var1) {
      this.statusStrings.add(new DERUTF8String(var1));
   }

   private void setFailInfoField(int var1) {
      this.failInfo |= var1;
   }

   private PKIStatusInfo getPKIStatusInfo() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(new ASN1Integer((long)this.status));
      if (this.statusStrings.size() > 0) {
         var1.add(PKIFreeText.getInstance(new DERSequence(this.statusStrings)));
      }

      if (this.failInfo != 0) {
         FailInfo var2 = new FailInfo(this.failInfo);
         var1.add(var2);
      }

      return PKIStatusInfo.getInstance(new DERSequence(var1));
   }

   public TimeStampResponse generate(TimeStampRequest var1, BigInteger var2, Date var3) throws TSPException {
      try {
         return this.generateGrantedResponse(var1, var2, var3, "Operation Okay");
      } catch (Exception var5) {
         return this.generateRejectedResponse(var5);
      }
   }

   public TimeStampResponse generateGrantedResponse(TimeStampRequest var1, BigInteger var2, Date var3) throws TSPException {
      return this.generateGrantedResponse(var1, var2, var3, (String)null);
   }

   public TimeStampResponse generateGrantedResponse(TimeStampRequest var1, BigInteger var2, Date var3, String var4) throws TSPException {
      return this.generateGrantedResponse(var1, var2, var3, var4, (Extensions)null);
   }

   public TimeStampResponse generateGrantedResponse(TimeStampRequest var1, BigInteger var2, Date var3, String var4, Extensions var5) throws TSPException {
      if (var3 == null) {
         throw new TSPValidationException("The time source is not available.", 512);
      } else {
         var1.validate(this.acceptedAlgorithms, this.acceptedPolicies, this.acceptedExtensions);
         this.status = 0;
         this.statusStrings = new ASN1EncodableVector();
         if (var4 != null) {
            this.addStatusString(var4);
         }

         PKIStatusInfo var6 = this.getPKIStatusInfo();

         ContentInfo var7;
         try {
            var7 = this.tokenGenerator.generate(var1, var2, var3, var5).toCMSSignedData().toASN1Structure();
         } catch (TSPException var11) {
            throw var11;
         } catch (Exception var12) {
            throw new TSPException("Timestamp token received cannot be converted to ContentInfo", var12);
         }

         TimeStampResp var8 = new TimeStampResp(var6, var7);

         try {
            return new TimeStampResponse(var8);
         } catch (IOException var10) {
            throw new TSPException("created badly formatted response!");
         }
      }
   }

   public TimeStampResponse generateRejectedResponse(Exception var1) throws TSPException {
      return var1 instanceof TSPValidationException ? this.generateFailResponse(2, ((TSPValidationException)var1).getFailureCode(), var1.getMessage()) : this.generateFailResponse(2, 1073741824, var1.getMessage());
   }

   public TimeStampResponse generateFailResponse(int var1, int var2, String var3) throws TSPException {
      this.status = var1;
      this.statusStrings = new ASN1EncodableVector();
      this.setFailInfoField(var2);
      if (var3 != null) {
         this.addStatusString(var3);
      }

      PKIStatusInfo var4 = this.getPKIStatusInfo();
      TimeStampResp var5 = new TimeStampResp(var4, (ContentInfo)null);

      try {
         return new TimeStampResponse(var5);
      } catch (IOException var7) {
         throw new TSPException("created badly formatted response!");
      }
   }

   private Set convert(Set var1) {
      if (var1 == null) {
         return var1;
      } else {
         HashSet var2 = new HashSet(var1.size());
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            if (var4 instanceof String) {
               var2.add(new ASN1ObjectIdentifier((String)var4));
            } else {
               var2.add(var4);
            }
         }

         return var2;
      }
   }

   class FailInfo extends DERBitString {
      FailInfo(int var2) {
         super(getBytes(var2), getPadBits(var2));
      }
   }
}
