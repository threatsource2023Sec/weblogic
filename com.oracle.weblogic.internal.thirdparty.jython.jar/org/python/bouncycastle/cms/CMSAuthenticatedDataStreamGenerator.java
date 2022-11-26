package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.BERSequenceGenerator;
import org.python.bouncycastle.asn1.BERSet;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.cms.AuthenticatedData;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.DigestCalculator;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.util.io.TeeOutputStream;

public class CMSAuthenticatedDataStreamGenerator extends CMSAuthenticatedGenerator {
   private int bufferSize;
   private boolean berEncodeRecipientSet;
   private MacCalculator macCalculator;

   public void setBufferSize(int var1) {
      this.bufferSize = var1;
   }

   public void setBEREncodeRecipients(boolean var1) {
      this.berEncodeRecipientSet = var1;
   }

   public OutputStream open(OutputStream var1, MacCalculator var2) throws CMSException {
      return this.open(CMSObjectIdentifiers.data, var1, var2);
   }

   public OutputStream open(OutputStream var1, MacCalculator var2, DigestCalculator var3) throws CMSException {
      return this.open(CMSObjectIdentifiers.data, var1, var2, var3);
   }

   public OutputStream open(ASN1ObjectIdentifier var1, OutputStream var2, MacCalculator var3) throws CMSException {
      return this.open(var1, var2, var3, (DigestCalculator)null);
   }

   public OutputStream open(ASN1ObjectIdentifier var1, OutputStream var2, MacCalculator var3, DigestCalculator var4) throws CMSException {
      this.macCalculator = var3;

      try {
         ASN1EncodableVector var5 = new ASN1EncodableVector();
         Iterator var6 = this.recipientInfoGenerators.iterator();

         while(var6.hasNext()) {
            RecipientInfoGenerator var7 = (RecipientInfoGenerator)var6.next();
            var5.add(var7.generate(var3.getKey()));
         }

         BERSequenceGenerator var13 = new BERSequenceGenerator(var2);
         var13.addObject(CMSObjectIdentifiers.authenticatedData);
         BERSequenceGenerator var14 = new BERSequenceGenerator(var13.getRawOutputStream(), 0, true);
         var14.addObject(new ASN1Integer((long)AuthenticatedData.calculateVersion(this.originatorInfo)));
         if (this.originatorInfo != null) {
            var14.addObject(new DERTaggedObject(false, 0, this.originatorInfo));
         }

         if (this.berEncodeRecipientSet) {
            var14.getRawOutputStream().write((new BERSet(var5)).getEncoded());
         } else {
            var14.getRawOutputStream().write((new DERSet(var5)).getEncoded());
         }

         AlgorithmIdentifier var8 = var3.getAlgorithmIdentifier();
         var14.getRawOutputStream().write(var8.getEncoded());
         if (var4 != null) {
            var14.addObject(new DERTaggedObject(false, 1, var4.getAlgorithmIdentifier()));
         }

         BERSequenceGenerator var9 = new BERSequenceGenerator(var14.getRawOutputStream());
         var9.addObject(var1);
         OutputStream var10 = CMSUtils.createBEROctetOutputStream(var9.getRawOutputStream(), 0, false, this.bufferSize);
         TeeOutputStream var11;
         if (var4 != null) {
            var11 = new TeeOutputStream(var10, var4.getOutputStream());
         } else {
            var11 = new TeeOutputStream(var10, var3.getOutputStream());
         }

         return new CmsAuthenticatedDataOutputStream(var3, var4, var1, var11, var13, var14, var9);
      } catch (IOException var12) {
         throw new CMSException("exception decoding algorithm parameters.", var12);
      }
   }

   private class CmsAuthenticatedDataOutputStream extends OutputStream {
      private OutputStream dataStream;
      private BERSequenceGenerator cGen;
      private BERSequenceGenerator envGen;
      private BERSequenceGenerator eiGen;
      private MacCalculator macCalculator;
      private DigestCalculator digestCalculator;
      private ASN1ObjectIdentifier contentType;

      public CmsAuthenticatedDataOutputStream(MacCalculator var2, DigestCalculator var3, ASN1ObjectIdentifier var4, OutputStream var5, BERSequenceGenerator var6, BERSequenceGenerator var7, BERSequenceGenerator var8) {
         this.macCalculator = var2;
         this.digestCalculator = var3;
         this.contentType = var4;
         this.dataStream = var5;
         this.cGen = var6;
         this.envGen = var7;
         this.eiGen = var8;
      }

      public void write(int var1) throws IOException {
         this.dataStream.write(var1);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         this.dataStream.write(var1, var2, var3);
      }

      public void write(byte[] var1) throws IOException {
         this.dataStream.write(var1);
      }

      public void close() throws IOException {
         this.dataStream.close();
         this.eiGen.close();
         Map var1;
         if (this.digestCalculator != null) {
            var1 = Collections.unmodifiableMap(CMSAuthenticatedDataStreamGenerator.this.getBaseParameters(this.contentType, this.digestCalculator.getAlgorithmIdentifier(), this.macCalculator.getAlgorithmIdentifier(), this.digestCalculator.getDigest()));
            if (CMSAuthenticatedDataStreamGenerator.this.authGen == null) {
               CMSAuthenticatedDataStreamGenerator.this.authGen = new DefaultAuthenticatedAttributeTableGenerator();
            }

            DERSet var2 = new DERSet(CMSAuthenticatedDataStreamGenerator.this.authGen.getAttributes(var1).toASN1EncodableVector());
            OutputStream var3 = this.macCalculator.getOutputStream();
            var3.write(var2.getEncoded("DER"));
            var3.close();
            this.envGen.addObject(new DERTaggedObject(false, 2, var2));
         } else {
            var1 = Collections.unmodifiableMap(new HashMap());
         }

         this.envGen.addObject(new DEROctetString(this.macCalculator.getMac()));
         if (CMSAuthenticatedDataStreamGenerator.this.unauthGen != null) {
            this.envGen.addObject(new DERTaggedObject(false, 3, new BERSet(CMSAuthenticatedDataStreamGenerator.this.unauthGen.getAttributes(var1).toASN1EncodableVector())));
         }

         this.envGen.close();
         this.cGen.close();
      }
   }
}
