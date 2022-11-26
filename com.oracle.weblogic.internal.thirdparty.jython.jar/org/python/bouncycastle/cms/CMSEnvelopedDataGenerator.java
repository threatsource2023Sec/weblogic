package org.python.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.BEROctetString;
import org.python.bouncycastle.asn1.BERSet;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.cms.AttributeTable;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.python.bouncycastle.asn1.cms.EnvelopedData;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OutputEncryptor;

public class CMSEnvelopedDataGenerator extends CMSEnvelopedGenerator {
   private CMSEnvelopedData doGenerate(CMSTypedData var1, OutputEncryptor var2) throws CMSException {
      if (!this.oldRecipientInfoGenerators.isEmpty()) {
         throw new IllegalStateException("can only use addRecipientGenerator() with this method");
      } else {
         ASN1EncodableVector var3 = new ASN1EncodableVector();
         ByteArrayOutputStream var4 = new ByteArrayOutputStream();

         try {
            OutputStream var5 = var2.getOutputStream(var4);
            var1.write(var5);
            var5.close();
         } catch (IOException var12) {
            throw new CMSException("");
         }

         byte[] var15 = var4.toByteArray();
         AlgorithmIdentifier var6 = var2.getAlgorithmIdentifier();
         BEROctetString var7 = new BEROctetString(var15);
         GenericKey var8 = var2.getKey();
         Iterator var9 = this.recipientInfoGenerators.iterator();

         while(var9.hasNext()) {
            RecipientInfoGenerator var10 = (RecipientInfoGenerator)var9.next();
            var3.add(var10.generate(var8));
         }

         EncryptedContentInfo var16 = new EncryptedContentInfo(var1.getContentType(), var6, var7);
         BERSet var13 = null;
         if (this.unprotectedAttributeGenerator != null) {
            AttributeTable var11 = this.unprotectedAttributeGenerator.getAttributes(new HashMap());
            var13 = new BERSet(var11.toASN1EncodableVector());
         }

         ContentInfo var14 = new ContentInfo(CMSObjectIdentifiers.envelopedData, new EnvelopedData(this.originatorInfo, new DERSet(var3), var16, var13));
         return new CMSEnvelopedData(var14);
      }
   }

   public CMSEnvelopedData generate(CMSTypedData var1, OutputEncryptor var2) throws CMSException {
      return this.doGenerate(var1, var2);
   }
}
