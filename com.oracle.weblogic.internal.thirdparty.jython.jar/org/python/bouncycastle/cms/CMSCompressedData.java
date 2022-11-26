package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.cms.CompressedData;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.operator.InputExpander;
import org.python.bouncycastle.operator.InputExpanderProvider;
import org.python.bouncycastle.util.Encodable;

public class CMSCompressedData implements Encodable {
   ContentInfo contentInfo;
   CompressedData comData;

   public CMSCompressedData(byte[] var1) throws CMSException {
      this(CMSUtils.readContentInfo(var1));
   }

   public CMSCompressedData(InputStream var1) throws CMSException {
      this(CMSUtils.readContentInfo(var1));
   }

   public CMSCompressedData(ContentInfo var1) throws CMSException {
      this.contentInfo = var1;

      try {
         this.comData = CompressedData.getInstance(var1.getContent());
      } catch (ClassCastException var3) {
         throw new CMSException("Malformed content.", var3);
      } catch (IllegalArgumentException var4) {
         throw new CMSException("Malformed content.", var4);
      }
   }

   public ASN1ObjectIdentifier getContentType() {
      return this.contentInfo.getContentType();
   }

   public byte[] getContent(InputExpanderProvider var1) throws CMSException {
      ContentInfo var2 = this.comData.getEncapContentInfo();
      ASN1OctetString var3 = (ASN1OctetString)var2.getContent();
      InputExpander var4 = var1.get(this.comData.getCompressionAlgorithmIdentifier());
      InputStream var5 = var4.getInputStream(var3.getOctetStream());

      try {
         return CMSUtils.streamToByteArray(var5);
      } catch (IOException var7) {
         throw new CMSException("exception reading compressed stream.", var7);
      }
   }

   public ContentInfo toASN1Structure() {
      return this.contentInfo;
   }

   public byte[] getEncoded() throws IOException {
      return this.contentInfo.getEncoded();
   }
}
