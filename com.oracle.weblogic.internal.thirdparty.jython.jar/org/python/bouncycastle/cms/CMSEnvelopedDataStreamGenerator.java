package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.BERSequenceGenerator;
import org.python.bouncycastle.asn1.BERSet;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.cms.AttributeTable;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.asn1.cms.EnvelopedData;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OutputEncryptor;

public class CMSEnvelopedDataStreamGenerator extends CMSEnvelopedGenerator {
   private ASN1Set _unprotectedAttributes = null;
   private int _bufferSize;
   private boolean _berEncodeRecipientSet;

   public void setBufferSize(int var1) {
      this._bufferSize = var1;
   }

   public void setBEREncodeRecipients(boolean var1) {
      this._berEncodeRecipientSet = var1;
   }

   private ASN1Integer getVersion() {
      return this.originatorInfo == null && this._unprotectedAttributes == null ? new ASN1Integer(0L) : new ASN1Integer(2L);
   }

   private OutputStream doOpen(ASN1ObjectIdentifier var1, OutputStream var2, OutputEncryptor var3) throws IOException, CMSException {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      GenericKey var5 = var3.getKey();
      Iterator var6 = this.recipientInfoGenerators.iterator();

      while(var6.hasNext()) {
         RecipientInfoGenerator var7 = (RecipientInfoGenerator)var6.next();
         var4.add(var7.generate(var5));
      }

      return this.open(var1, var2, var4, var3);
   }

   protected OutputStream open(ASN1ObjectIdentifier var1, OutputStream var2, ASN1EncodableVector var3, OutputEncryptor var4) throws IOException {
      BERSequenceGenerator var5 = new BERSequenceGenerator(var2);
      var5.addObject(CMSObjectIdentifiers.envelopedData);
      BERSequenceGenerator var6 = new BERSequenceGenerator(var5.getRawOutputStream(), 0, true);
      var6.addObject(this.getVersion());
      if (this.originatorInfo != null) {
         var6.addObject(new DERTaggedObject(false, 0, this.originatorInfo));
      }

      if (this._berEncodeRecipientSet) {
         var6.getRawOutputStream().write((new BERSet(var3)).getEncoded());
      } else {
         var6.getRawOutputStream().write((new DERSet(var3)).getEncoded());
      }

      BERSequenceGenerator var7 = new BERSequenceGenerator(var6.getRawOutputStream());
      var7.addObject(var1);
      AlgorithmIdentifier var8 = var4.getAlgorithmIdentifier();
      var7.getRawOutputStream().write(var8.getEncoded());
      OutputStream var9 = CMSUtils.createBEROctetOutputStream(var7.getRawOutputStream(), 0, false, this._bufferSize);
      OutputStream var10 = var4.getOutputStream(var9);
      return new CmsEnvelopedDataOutputStream(var10, var5, var6, var7);
   }

   protected OutputStream open(OutputStream var1, ASN1EncodableVector var2, OutputEncryptor var3) throws CMSException {
      try {
         BERSequenceGenerator var4 = new BERSequenceGenerator(var1);
         var4.addObject(CMSObjectIdentifiers.envelopedData);
         BERSequenceGenerator var5 = new BERSequenceGenerator(var4.getRawOutputStream(), 0, true);
         Object var6;
         if (this._berEncodeRecipientSet) {
            var6 = new BERSet(var2);
         } else {
            var6 = new DERSet(var2);
         }

         var5.addObject(new ASN1Integer((long)EnvelopedData.calculateVersion(this.originatorInfo, (ASN1Set)var6, this._unprotectedAttributes)));
         if (this.originatorInfo != null) {
            var5.addObject(new DERTaggedObject(false, 0, this.originatorInfo));
         }

         var5.getRawOutputStream().write(((ASN1Set)var6).getEncoded());
         BERSequenceGenerator var7 = new BERSequenceGenerator(var5.getRawOutputStream());
         var7.addObject(CMSObjectIdentifiers.data);
         AlgorithmIdentifier var8 = var3.getAlgorithmIdentifier();
         var7.getRawOutputStream().write(var8.getEncoded());
         OutputStream var9 = CMSUtils.createBEROctetOutputStream(var7.getRawOutputStream(), 0, false, this._bufferSize);
         return new CmsEnvelopedDataOutputStream(var3.getOutputStream(var9), var4, var5, var7);
      } catch (IOException var10) {
         throw new CMSException("exception decoding algorithm parameters.", var10);
      }
   }

   public OutputStream open(OutputStream var1, OutputEncryptor var2) throws CMSException, IOException {
      return this.doOpen(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), var1, var2);
   }

   public OutputStream open(ASN1ObjectIdentifier var1, OutputStream var2, OutputEncryptor var3) throws CMSException, IOException {
      return this.doOpen(var1, var2, var3);
   }

   private class CmsEnvelopedDataOutputStream extends OutputStream {
      private OutputStream _out;
      private BERSequenceGenerator _cGen;
      private BERSequenceGenerator _envGen;
      private BERSequenceGenerator _eiGen;

      public CmsEnvelopedDataOutputStream(OutputStream var2, BERSequenceGenerator var3, BERSequenceGenerator var4, BERSequenceGenerator var5) {
         this._out = var2;
         this._cGen = var3;
         this._envGen = var4;
         this._eiGen = var5;
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
         if (CMSEnvelopedDataStreamGenerator.this.unprotectedAttributeGenerator != null) {
            AttributeTable var1 = CMSEnvelopedDataStreamGenerator.this.unprotectedAttributeGenerator.getAttributes(new HashMap());
            BERSet var2 = new BERSet(var1.toASN1EncodableVector());
            this._envGen.addObject(new DERTaggedObject(false, 1, var2));
         }

         this._envGen.close();
         this._cGen.close();
      }
   }
}
