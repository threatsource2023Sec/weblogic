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

public class BcPasswordEnvelopedRecipient extends BcPasswordRecipient {
   public BcPasswordEnvelopedRecipient(char[] var1) {
      super(var1);
   }

   public RecipientOperator getRecipientOperator(AlgorithmIdentifier var1, final AlgorithmIdentifier var2, byte[] var3, byte[] var4) throws CMSException {
      KeyParameter var5 = this.extractSecretKey(var1, var2, var3, var4);
      final Object var6 = EnvelopedDataHelper.createContentCipher(false, var5, var2);
      return new RecipientOperator(new InputDecryptor() {
         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return var2;
         }

         public InputStream getInputStream(InputStream var1) {
            return var6 instanceof BufferedBlockCipher ? new CipherInputStream(var1, (BufferedBlockCipher)var6) : new CipherInputStream(var1, (StreamCipher)var6);
         }
      });
   }
}
