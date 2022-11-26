package org.python.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.ASN1OctetStringParser;
import org.python.bouncycastle.asn1.ASN1SequenceParser;
import org.python.bouncycastle.asn1.cms.CompressedDataParser;
import org.python.bouncycastle.asn1.cms.ContentInfoParser;
import org.python.bouncycastle.operator.InputExpander;
import org.python.bouncycastle.operator.InputExpanderProvider;

public class CMSCompressedDataParser extends CMSContentInfoParser {
   public CMSCompressedDataParser(byte[] var1) throws CMSException {
      this((InputStream)(new ByteArrayInputStream(var1)));
   }

   public CMSCompressedDataParser(InputStream var1) throws CMSException {
      super(var1);
   }

   public CMSTypedStream getContent(InputExpanderProvider var1) throws CMSException {
      try {
         CompressedDataParser var2 = new CompressedDataParser((ASN1SequenceParser)this._contentInfo.getContent(16));
         ContentInfoParser var3 = var2.getEncapContentInfo();
         InputExpander var4 = var1.get(var2.getCompressionAlgorithmIdentifier());
         ASN1OctetStringParser var5 = (ASN1OctetStringParser)var3.getContent(4);
         return new CMSTypedStream(var3.getContentType().getId(), var4.getInputStream(var5.getOctetStream()));
      } catch (IOException var6) {
         throw new CMSException("IOException reading compressed content.", var6);
      }
   }
}
