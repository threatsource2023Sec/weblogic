package org.python.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.util.io.Streams;

public abstract class RecipientInformation {
   protected RecipientId rid;
   protected AlgorithmIdentifier keyEncAlg;
   protected AlgorithmIdentifier messageAlgorithm;
   protected CMSSecureReadable secureReadable;
   private AuthAttributesProvider additionalData;
   private byte[] resultMac;
   private RecipientOperator operator;

   RecipientInformation(AlgorithmIdentifier var1, AlgorithmIdentifier var2, CMSSecureReadable var3, AuthAttributesProvider var4) {
      this.keyEncAlg = var1;
      this.messageAlgorithm = var2;
      this.secureReadable = var3;
      this.additionalData = var4;
   }

   public RecipientId getRID() {
      return this.rid;
   }

   private byte[] encodeObj(ASN1Encodable var1) throws IOException {
      return var1 != null ? var1.toASN1Primitive().getEncoded() : null;
   }

   public AlgorithmIdentifier getKeyEncryptionAlgorithm() {
      return this.keyEncAlg;
   }

   public String getKeyEncryptionAlgOID() {
      return this.keyEncAlg.getAlgorithm().getId();
   }

   public byte[] getKeyEncryptionAlgParams() {
      try {
         return this.encodeObj(this.keyEncAlg.getParameters());
      } catch (Exception var2) {
         throw new RuntimeException("exception getting encryption parameters " + var2);
      }
   }

   public byte[] getContentDigest() {
      return this.secureReadable instanceof CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable ? ((CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable)this.secureReadable).getDigest() : null;
   }

   public byte[] getMac() {
      if (this.resultMac == null && this.operator.isMacBased()) {
         if (this.additionalData != null) {
            try {
               Streams.drain(this.operator.getInputStream(new ByteArrayInputStream(this.additionalData.getAuthAttributes().getEncoded("DER"))));
            } catch (IOException var2) {
               throw new IllegalStateException("unable to drain input: " + var2.getMessage());
            }
         }

         this.resultMac = this.operator.getMac();
      }

      return this.resultMac;
   }

   public byte[] getContent(Recipient var1) throws CMSException {
      try {
         return CMSUtils.streamToByteArray(this.getContentStream(var1).getContentStream());
      } catch (IOException var3) {
         throw new CMSException("unable to parse internal stream: " + var3.getMessage(), var3);
      }
   }

   public CMSTypedStream getContentStream(Recipient var1) throws CMSException, IOException {
      this.operator = this.getRecipientOperator(var1);
      return this.additionalData != null ? new CMSTypedStream(this.secureReadable.getInputStream()) : new CMSTypedStream(this.operator.getInputStream(this.secureReadable.getInputStream()));
   }

   protected abstract RecipientOperator getRecipientOperator(Recipient var1) throws CMSException, IOException;
}
