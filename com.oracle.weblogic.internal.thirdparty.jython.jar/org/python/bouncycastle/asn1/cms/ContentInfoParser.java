package org.python.bouncycastle.asn1.cms;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1SequenceParser;
import org.python.bouncycastle.asn1.ASN1TaggedObjectParser;

public class ContentInfoParser {
   private ASN1ObjectIdentifier contentType;
   private ASN1TaggedObjectParser content;

   public ContentInfoParser(ASN1SequenceParser var1) throws IOException {
      this.contentType = (ASN1ObjectIdentifier)var1.readObject();
      this.content = (ASN1TaggedObjectParser)var1.readObject();
   }

   public ASN1ObjectIdentifier getContentType() {
      return this.contentType;
   }

   public ASN1Encodable getContent(int var1) throws IOException {
      return this.content != null ? this.content.getObjectParser(var1, true) : null;
   }
}
