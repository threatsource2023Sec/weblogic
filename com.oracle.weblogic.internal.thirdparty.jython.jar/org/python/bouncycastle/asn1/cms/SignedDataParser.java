package org.python.bouncycastle.asn1.cms;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1SequenceParser;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.ASN1SetParser;
import org.python.bouncycastle.asn1.ASN1TaggedObjectParser;

public class SignedDataParser {
   private ASN1SequenceParser _seq;
   private ASN1Integer _version;
   private Object _nextObject;
   private boolean _certsCalled;
   private boolean _crlsCalled;

   public static SignedDataParser getInstance(Object var0) throws IOException {
      if (var0 instanceof ASN1Sequence) {
         return new SignedDataParser(((ASN1Sequence)var0).parser());
      } else if (var0 instanceof ASN1SequenceParser) {
         return new SignedDataParser((ASN1SequenceParser)var0);
      } else {
         throw new IOException("unknown object encountered: " + var0.getClass().getName());
      }
   }

   private SignedDataParser(ASN1SequenceParser var1) throws IOException {
      this._seq = var1;
      this._version = (ASN1Integer)var1.readObject();
   }

   public ASN1Integer getVersion() {
      return this._version;
   }

   public ASN1SetParser getDigestAlgorithms() throws IOException {
      ASN1Encodable var1 = this._seq.readObject();
      return var1 instanceof ASN1Set ? ((ASN1Set)var1).parser() : (ASN1SetParser)var1;
   }

   public ContentInfoParser getEncapContentInfo() throws IOException {
      return new ContentInfoParser((ASN1SequenceParser)this._seq.readObject());
   }

   public ASN1SetParser getCertificates() throws IOException {
      this._certsCalled = true;
      this._nextObject = this._seq.readObject();
      if (this._nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 0) {
         ASN1SetParser var1 = (ASN1SetParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(17, false);
         this._nextObject = null;
         return var1;
      } else {
         return null;
      }
   }

   public ASN1SetParser getCrls() throws IOException {
      if (!this._certsCalled) {
         throw new IOException("getCerts() has not been called.");
      } else {
         this._crlsCalled = true;
         if (this._nextObject == null) {
            this._nextObject = this._seq.readObject();
         }

         if (this._nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 1) {
            ASN1SetParser var1 = (ASN1SetParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(17, false);
            this._nextObject = null;
            return var1;
         } else {
            return null;
         }
      }
   }

   public ASN1SetParser getSignerInfos() throws IOException {
      if (this._certsCalled && this._crlsCalled) {
         if (this._nextObject == null) {
            this._nextObject = this._seq.readObject();
         }

         return (ASN1SetParser)this._nextObject;
      } else {
         throw new IOException("getCerts() and/or getCrls() has not been called.");
      }
   }
}
