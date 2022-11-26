package org.python.bouncycastle.asn1.cms;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1SequenceParser;
import org.python.bouncycastle.asn1.ASN1TaggedObjectParser;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedContentInfoParser {
   private ASN1ObjectIdentifier _contentType;
   private AlgorithmIdentifier _contentEncryptionAlgorithm;
   private ASN1TaggedObjectParser _encryptedContent;

   public EncryptedContentInfoParser(ASN1SequenceParser var1) throws IOException {
      this._contentType = (ASN1ObjectIdentifier)var1.readObject();
      this._contentEncryptionAlgorithm = AlgorithmIdentifier.getInstance(var1.readObject().toASN1Primitive());
      this._encryptedContent = (ASN1TaggedObjectParser)var1.readObject();
   }

   public ASN1ObjectIdentifier getContentType() {
      return this._contentType;
   }

   public AlgorithmIdentifier getContentEncryptionAlgorithm() {
      return this._contentEncryptionAlgorithm;
   }

   public ASN1Encodable getEncryptedContent(int var1) throws IOException {
      return this._encryptedContent.getObjectParser(var1, false);
   }
}
