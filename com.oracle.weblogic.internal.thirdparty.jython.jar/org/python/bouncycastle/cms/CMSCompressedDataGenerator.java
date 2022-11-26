package org.python.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.BEROctetString;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.asn1.cms.CompressedData;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.OutputCompressor;

public class CMSCompressedDataGenerator {
   public static final String ZLIB = "1.2.840.113549.1.9.16.3.8";

   public CMSCompressedData generate(CMSTypedData var1, OutputCompressor var2) throws CMSException {
      AlgorithmIdentifier var5;
      BEROctetString var6;
      try {
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         OutputStream var4 = var2.getOutputStream(var3);
         var1.write(var4);
         var4.close();
         var5 = var2.getAlgorithmIdentifier();
         var6 = new BEROctetString(var3.toByteArray());
      } catch (IOException var7) {
         throw new CMSException("exception encoding data.", var7);
      }

      ContentInfo var8 = new ContentInfo(var1.getContentType(), var6);
      ContentInfo var9 = new ContentInfo(CMSObjectIdentifiers.compressedData, new CompressedData(var5, var8));
      return new CMSCompressedData(var9);
   }
}
