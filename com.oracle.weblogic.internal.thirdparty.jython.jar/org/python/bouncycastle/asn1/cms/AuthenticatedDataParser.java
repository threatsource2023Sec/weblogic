package org.python.bouncycastle.asn1.cms;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1SequenceParser;
import org.python.bouncycastle.asn1.ASN1SetParser;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.ASN1TaggedObjectParser;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class AuthenticatedDataParser {
   private ASN1SequenceParser seq;
   private ASN1Integer version;
   private ASN1Encodable nextObject;
   private boolean originatorInfoCalled;

   public AuthenticatedDataParser(ASN1SequenceParser var1) throws IOException {
      this.seq = var1;
      this.version = ASN1Integer.getInstance(var1.readObject());
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

   public AlgorithmIdentifier getMacAlgorithm() throws IOException {
      if (this.nextObject == null) {
         this.nextObject = this.seq.readObject();
      }

      if (this.nextObject != null) {
         ASN1SequenceParser var1 = (ASN1SequenceParser)this.nextObject;
         this.nextObject = null;
         return AlgorithmIdentifier.getInstance(var1.toASN1Primitive());
      } else {
         return null;
      }
   }

   public AlgorithmIdentifier getDigestAlgorithm() throws IOException {
      if (this.nextObject == null) {
         this.nextObject = this.seq.readObject();
      }

      if (this.nextObject instanceof ASN1TaggedObjectParser) {
         AlgorithmIdentifier var1 = AlgorithmIdentifier.getInstance((ASN1TaggedObject)this.nextObject.toASN1Primitive(), false);
         this.nextObject = null;
         return var1;
      } else {
         return null;
      }
   }

   /** @deprecated */
   public ContentInfoParser getEnapsulatedContentInfo() throws IOException {
      return this.getEncapsulatedContentInfo();
   }

   public ContentInfoParser getEncapsulatedContentInfo() throws IOException {
      if (this.nextObject == null) {
         this.nextObject = this.seq.readObject();
      }

      if (this.nextObject != null) {
         ASN1SequenceParser var1 = (ASN1SequenceParser)this.nextObject;
         this.nextObject = null;
         return new ContentInfoParser(var1);
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
