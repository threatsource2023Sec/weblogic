package org.python.bouncycastle.asn1.cms;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1SequenceParser;
import org.python.bouncycastle.asn1.ASN1SetParser;
import org.python.bouncycastle.asn1.ASN1TaggedObjectParser;

public class EnvelopedDataParser {
   private ASN1SequenceParser _seq;
   private ASN1Integer _version;
   private ASN1Encodable _nextObject;
   private boolean _originatorInfoCalled;

   public EnvelopedDataParser(ASN1SequenceParser var1) throws IOException {
      this._seq = var1;
      this._version = ASN1Integer.getInstance(var1.readObject());
   }

   public ASN1Integer getVersion() {
      return this._version;
   }

   public OriginatorInfo getOriginatorInfo() throws IOException {
      this._originatorInfoCalled = true;
      if (this._nextObject == null) {
         this._nextObject = this._seq.readObject();
      }

      if (this._nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 0) {
         ASN1SequenceParser var1 = (ASN1SequenceParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(16, false);
         this._nextObject = null;
         return OriginatorInfo.getInstance(var1.toASN1Primitive());
      } else {
         return null;
      }
   }

   public ASN1SetParser getRecipientInfos() throws IOException {
      if (!this._originatorInfoCalled) {
         this.getOriginatorInfo();
      }

      if (this._nextObject == null) {
         this._nextObject = this._seq.readObject();
      }

      ASN1SetParser var1 = (ASN1SetParser)this._nextObject;
      this._nextObject = null;
      return var1;
   }

   public EncryptedContentInfoParser getEncryptedContentInfo() throws IOException {
      if (this._nextObject == null) {
         this._nextObject = this._seq.readObject();
      }

      if (this._nextObject != null) {
         ASN1SequenceParser var1 = (ASN1SequenceParser)this._nextObject;
         this._nextObject = null;
         return new EncryptedContentInfoParser(var1);
      } else {
         return null;
      }
   }

   public ASN1SetParser getUnprotectedAttrs() throws IOException {
      if (this._nextObject == null) {
         this._nextObject = this._seq.readObject();
      }

      if (this._nextObject != null) {
         ASN1Encodable var1 = this._nextObject;
         this._nextObject = null;
         return (ASN1SetParser)((ASN1TaggedObjectParser)var1).getObjectParser(17, false);
      } else {
         return null;
      }
   }
}
