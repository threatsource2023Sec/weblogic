package org.python.bouncycastle.cms.bc;

import java.io.InputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.RecipientOperator;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.crypto.StreamCipher;
import org.python.bouncycastle.crypto.io.CipherInputStream;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.operator.InputDecryptor;
import org.python.bouncycastle.operator.bc.BcSymmetricKeyUnwrapper;

public class BcKEKEnvelopedRecipient extends BcKEKRecipient {
   public BcKEKEnvelopedRecipient(BcSymmetricKeyUnwrapper var1) {
      super(var1);
   }

   public RecipientOperator getRecipientOperator(AlgorithmIdentifier var1, final AlgorithmIdentifier var2, byte[] var3) throws CMSException {
      KeyParameter var4 = (KeyParameter)this.extractSecretKey(var1, var2, var3);
      final Object var5 = EnvelopedDataHelper.createContentCipher(false, var4, var2);
      return new RecipientOperator(new InputDecryptor() {
         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return var2;
         }

         public InputStream getInputStream(InputStream var1) {
            return var5 instanceof BufferedBlockCipher ? new CipherInputStream(var1, (BufferedBlockCipher)var5) : new CipherInputStream(var1, (StreamCipher)var5);
         }
      });
   }
}
