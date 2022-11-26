package org.python.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import org.python.bouncycastle.asn1.BEROctetString;
import org.python.bouncycastle.asn1.BERSet;
import org.python.bouncycastle.asn1.cms.AttributeTable;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.python.bouncycastle.asn1.cms.EncryptedData;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.OutputEncryptor;

public class CMSEncryptedDataGenerator extends CMSEncryptedGenerator {
   private CMSEncryptedData doGenerate(CMSTypedData var1, OutputEncryptor var2) throws CMSException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();

      try {
         OutputStream var4 = var2.getOutputStream(var3);
         var1.write(var4);
         var4.close();
      } catch (IOException var10) {
         throw new CMSException("");
      }

      byte[] var11 = var3.toByteArray();
      AlgorithmIdentifier var5 = var2.getAlgorithmIdentifier();
      BEROctetString var6 = new BEROctetString(var11);
      EncryptedContentInfo var7 = new EncryptedContentInfo(var1.getContentType(), var5, var6);
      BERSet var8 = null;
      if (this.unprotectedAttributeGenerator != null) {
         AttributeTable var9 = this.unprotectedAttributeGenerator.getAttributes(new HashMap());
         var8 = new BERSet(var9.toASN1EncodableVector());
      }

      ContentInfo var12 = new ContentInfo(CMSObjectIdentifiers.encryptedData, new EncryptedData(var7, var8));
      return new CMSEncryptedData(var12);
   }

   public CMSEncryptedData generate(CMSTypedData var1, OutputEncryptor var2) throws CMSException {
      return this.doGenerate(var1, var2);
   }
}
