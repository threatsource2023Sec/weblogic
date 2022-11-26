package org.python.bouncycastle.cms.jcajce;

import java.io.InputStream;
import java.security.Key;
import javax.crypto.Cipher;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.RecipientOperator;
import org.python.bouncycastle.jcajce.io.CipherInputStream;
import org.python.bouncycastle.operator.InputDecryptor;

public class JcePasswordEnvelopedRecipient extends JcePasswordRecipient {
   public JcePasswordEnvelopedRecipient(char[] var1) {
      super(var1);
   }

   public RecipientOperator getRecipientOperator(AlgorithmIdentifier var1, final AlgorithmIdentifier var2, byte[] var3, byte[] var4) throws CMSException {
      Key var5 = this.extractSecretKey(var1, var2, var3, var4);
      final Cipher var6 = this.helper.createContentCipher(var5, var2);
      return new RecipientOperator(new InputDecryptor() {
         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return var2;
         }

         public InputStream getInputStream(InputStream var1) {
            return new CipherInputStream(var1, var6);
         }
      });
   }
}
