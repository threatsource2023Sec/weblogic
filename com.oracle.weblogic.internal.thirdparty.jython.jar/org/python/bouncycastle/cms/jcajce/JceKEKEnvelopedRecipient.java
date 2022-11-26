package org.python.bouncycastle.cms.jcajce;

import java.io.InputStream;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.RecipientOperator;
import org.python.bouncycastle.jcajce.io.CipherInputStream;
import org.python.bouncycastle.operator.InputDecryptor;

public class JceKEKEnvelopedRecipient extends JceKEKRecipient {
   public JceKEKEnvelopedRecipient(SecretKey var1) {
      super(var1);
   }

   public RecipientOperator getRecipientOperator(AlgorithmIdentifier var1, final AlgorithmIdentifier var2, byte[] var3) throws CMSException {
      Key var4 = this.extractSecretKey(var1, var2, var3);
      final Cipher var5 = this.contentHelper.createContentCipher(var4, var2);
      return new RecipientOperator(new InputDecryptor() {
         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return var2;
         }

         public InputStream getInputStream(InputStream var1) {
            return new CipherInputStream(var1, var5);
         }
      });
   }
}
