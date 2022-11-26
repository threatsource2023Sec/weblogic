package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.BERSequenceGenerator;
import org.python.bouncycastle.asn1.BERTaggedObject;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.asn1.cms.SignerInfo;

public class CMSSignedDataStreamGenerator extends CMSSignedGenerator {
   private int _bufferSize;

   public void setBufferSize(int var1) {
      this._bufferSize = var1;
   }

   public OutputStream open(OutputStream var1) throws IOException {
      return this.open(var1, false);
   }

   public OutputStream open(OutputStream var1, boolean var2) throws IOException {
      return this.open(CMSObjectIdentifiers.data, var1, var2);
   }

   public OutputStream open(OutputStream var1, boolean var2, OutputStream var3) throws IOException {
      return this.open(CMSObjectIdentifiers.data, var1, var2, var3);
   }

   public OutputStream open(ASN1ObjectIdentifier var1, OutputStream var2, boolean var3) throws IOException {
      return this.open(var1, var2, var3, (OutputStream)null);
   }

   public OutputStream open(ASN1ObjectIdentifier var1, OutputStream var2, boolean var3, OutputStream var4) throws IOException {
      BERSequenceGenerator var5 = new BERSequenceGenerator(var2);
      var5.addObject(CMSObjectIdentifiers.signedData);
      BERSequenceGenerator var6 = new BERSequenceGenerator(var5.getRawOutputStream(), 0, true);
      var6.addObject(this.calculateVersion(var1));
      ASN1EncodableVector var7 = new ASN1EncodableVector();
      Iterator var8 = this._signers.iterator();

      while(var8.hasNext()) {
         SignerInformation var9 = (SignerInformation)var8.next();
         var7.add(CMSSignedHelper.INSTANCE.fixAlgID(var9.getDigestAlgorithmID()));
      }

      var8 = this.signerGens.iterator();

      while(var8.hasNext()) {
         SignerInfoGenerator var13 = (SignerInfoGenerator)var8.next();
         var7.add(var13.getDigestAlgorithm());
      }

      var6.getRawOutputStream().write((new DERSet(var7)).getEncoded());
      BERSequenceGenerator var12 = new BERSequenceGenerator(var6.getRawOutputStream());
      var12.addObject(var1);
      OutputStream var14 = var3 ? CMSUtils.createBEROctetOutputStream(var12.getRawOutputStream(), 0, true, this._bufferSize) : null;
      OutputStream var10 = CMSUtils.getSafeTeeOutputStream(var4, var14);
      OutputStream var11 = CMSUtils.attachSignersToOutputStream(this.signerGens, var10);
      return new CmsSignedDataOutputStream(var11, var1, var5, var6, var12);
   }

   private ASN1Integer calculateVersion(ASN1ObjectIdentifier var1) {
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      Iterator var6;
      Object var7;
      if (this.certs != null) {
         var6 = this.certs.iterator();

         while(var6.hasNext()) {
            var7 = var6.next();
            if (var7 instanceof ASN1TaggedObject) {
               ASN1TaggedObject var8 = (ASN1TaggedObject)var7;
               if (var8.getTagNo() == 1) {
                  var4 = true;
               } else if (var8.getTagNo() == 2) {
                  var5 = true;
               } else if (var8.getTagNo() == 3) {
                  var2 = true;
               }
            }
         }
      }

      if (var2) {
         return new ASN1Integer(5L);
      } else {
         if (this.crls != null) {
            var6 = this.crls.iterator();

            while(var6.hasNext()) {
               var7 = var6.next();
               if (var7 instanceof ASN1TaggedObject) {
                  var3 = true;
               }
            }
         }

         if (var3) {
            return new ASN1Integer(5L);
         } else if (var5) {
            return new ASN1Integer(4L);
         } else if (var4) {
            return new ASN1Integer(3L);
         } else if (this.checkForVersion3(this._signers, this.signerGens)) {
            return new ASN1Integer(3L);
         } else {
            return !CMSObjectIdentifiers.data.equals(var1) ? new ASN1Integer(3L) : new ASN1Integer(1L);
         }
      }
   }

   private boolean checkForVersion3(List var1, List var2) {
      Iterator var3 = var1.iterator();

      SignerInfo var4;
      do {
         if (!var3.hasNext()) {
            var3 = var2.iterator();

            SignerInfoGenerator var5;
            do {
               if (!var3.hasNext()) {
                  return false;
               }

               var5 = (SignerInfoGenerator)var3.next();
            } while(var5.getGeneratedVersion() != 3);

            return true;
         }

         var4 = SignerInfo.getInstance(((SignerInformation)var3.next()).toASN1Structure());
      } while(var4.getVersion().getValue().intValue() != 3);

      return true;
   }

   private class CmsSignedDataOutputStream extends OutputStream {
      private OutputStream _out;
      private ASN1ObjectIdentifier _contentOID;
      private BERSequenceGenerator _sGen;
      private BERSequenceGenerator _sigGen;
      private BERSequenceGenerator _eiGen;

      public CmsSignedDataOutputStream(OutputStream var2, ASN1ObjectIdentifier var3, BERSequenceGenerator var4, BERSequenceGenerator var5, BERSequenceGenerator var6) {
         this._out = var2;
         this._contentOID = var3;
         this._sGen = var4;
         this._sigGen = var5;
         this._eiGen = var6;
      }

      public void write(int var1) throws IOException {
         this._out.write(var1);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         this._out.write(var1, var2, var3);
      }

      public void write(byte[] var1) throws IOException {
         this._out.write(var1);
      }

      public void close() throws IOException {
         this._out.close();
         this._eiGen.close();
         CMSSignedDataStreamGenerator.this.digests.clear();
         ASN1Set var1;
         if (CMSSignedDataStreamGenerator.this.certs.size() != 0) {
            var1 = CMSUtils.createBerSetFromList(CMSSignedDataStreamGenerator.this.certs);
            this._sigGen.getRawOutputStream().write((new BERTaggedObject(false, 0, var1)).getEncoded());
         }

         if (CMSSignedDataStreamGenerator.this.crls.size() != 0) {
            var1 = CMSUtils.createBerSetFromList(CMSSignedDataStreamGenerator.this.crls);
            this._sigGen.getRawOutputStream().write((new BERTaggedObject(false, 1, var1)).getEncoded());
         }

         ASN1EncodableVector var6 = new ASN1EncodableVector();
         Iterator var2 = CMSSignedDataStreamGenerator.this.signerGens.iterator();

         while(var2.hasNext()) {
            SignerInfoGenerator var3 = (SignerInfoGenerator)var2.next();

            try {
               var6.add(var3.generate(this._contentOID));
               byte[] var4 = var3.getCalculatedDigest();
               CMSSignedDataStreamGenerator.this.digests.put(var3.getDigestAlgorithm().getAlgorithm().getId(), var4);
            } catch (CMSException var5) {
               throw new CMSStreamException("exception generating signers: " + var5.getMessage(), var5);
            }
         }

         var2 = CMSSignedDataStreamGenerator.this._signers.iterator();

         while(var2.hasNext()) {
            SignerInformation var7 = (SignerInformation)var2.next();
            var6.add(var7.toASN1Structure());
         }

         this._sigGen.getRawOutputStream().write((new DERSet(var6)).getEncoded());
         this._sigGen.close();
         this._sGen.close();
      }
   }
}
