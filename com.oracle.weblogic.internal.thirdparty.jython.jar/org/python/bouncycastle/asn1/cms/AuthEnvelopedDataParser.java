package org.python.bouncycastle.asn1.cms;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1ParsingException;
import org.python.bouncycastle.asn1.ASN1SequenceParser;
import org.python.bouncycastle.asn1.ASN1SetParser;
import org.python.bouncycastle.asn1.ASN1TaggedObjectParser;

public class AuthEnvelopedDataParser {
   private ASN1SequenceParser seq;
   private ASN1Integer version;
   private ASN1Encodable nextObject;
   private boolean originatorInfoCalled;
   private EncryptedContentInfoParser authEncryptedContentInfoParser;

   public AuthEnvelopedDataParser(ASN1SequenceParser var1) throws IOException {
      this.seq = var1;
      this.version = ASN1Integer.getInstance(var1.readObject());
      if (this.version.getValue().intValue() != 0) {
         throw new ASN1ParsingException("AuthEnvelopedData version number must be 0");
      }
   }

   public ASN1Integer getVersion() {
      return this.version;
   }

   public OriginatorInfo getOriginatorInfo() throws IOException {
      this.originatorInfoCalled = true;
      if (this.nextObject == null) {
         this.nextObject = this.seq.readObject();
      }

      if (this.nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser)this.nextObject).getTagNo() == 0) {
         ASN1SequenceParser var1 = (ASN1SequenceParser)((ASN1TaggedObjectParser)this.nextObject).getObjectParser(16, false);
         this.nextObject = null;
         return OriginatorInfo.getInstance(var1.toASN1Primitive());
      } else {
         return null;
      }
   }

   public ASN1SetParser getRecipientInfos() throws IOException {
      if (!this.originatorInfoCalled) {
         this.getOriginatorInfo();
      }

      if (this.nextObject == null) {
         this.nextObject = this.seq.readObject();
      }

      ASN1SetParser var1 = (ASN1SetParser)this.nextObject;
      this.nextObject = null;
      return var1;
   }

   public EncryptedContentInfoParser getAuthEncryptedContentInfo() throws IOException {
      if (this.nextObject == null) {
         this.nextObject = this.seq.readObject();
      }

      if (this.nextObject != null) {
         ASN1SequenceParser var1 = (ASN1SequenceParser)this.nextObject;
         this.nextObject = null;
         this.authEncryptedContentInfoParser = new EncryptedContentInfoParser(var1);
         return this.authEncryptedContentInfoParser;
      } else {
         return null;
      }
   }

   public ASN1SetParser getAuthAttrs() throws IOException {
      if (this.nextObject == null) {
         this.nextObject = this.seq.readObject();
      }

      if (this.nextObject instanceof ASN1TaggedObjectParser) {
         ASN1Encodable var1 = this.nextObject;
         this.nextObject = null;
         return (ASN1SetParser)((ASN1TaggedObjectParser)var1).getObjectParser(17, false);
      } else if (!this.authEncryptedContentInfoParser.getContentType().equals(CMSObjectIdentifiers.data)) {
         throw new ASN1ParsingException("authAttrs must be present with non-data content");
      } else {
         return null;
      }
   }

   public ASN1OctetString getMac() throws IOException {
      if (this.nextObject == null) {
         this.nextObject = this.seq.readObject();
      }

      ASN1Encodable var1 = this.nextObject;
      this.nextObject = null;
      return ASN1OctetString.getInstance(var1.toASN1Primitive());
   }

   public ASN1SetParser getUnauthAttrs() throws IOException {
      if (this.nextObject == null) {
         this.nextObject = this.seq.readObject();
      }

      if (this.nextObject != null) {
         ASN1Encodable var1 = this.nextObject;
         this.nextObject = null;
         return (ASN1SetParser)((ASN1TaggedObjectParser)var1).getObjectParser(17, false);
      } else {
         return null;
      }
   }
}
