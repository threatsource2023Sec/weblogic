package org.python.bouncycastle.cms.jcajce;

import java.io.OutputStream;
import java.security.Key;
import javax.crypto.Mac;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.RecipientOperator;
import org.python.bouncycastle.jcajce.io.MacOutputStream;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.operator.jcajce.JceGenericKey;

public class JcePasswordAuthenticatedRecipient extends JcePasswordRecipient {
   public JcePasswordAuthenticatedRecipient(char[] var1) {
      super(var1);
   }

   public RecipientOperator getRecipientOperator(AlgorithmIdentifier var1, final AlgorithmIdentifier var2, byte[] var3, byte[] var4) throws CMSException {
      final Key var5 = this.extractSecretKey(var1, var2, var3, var4);
      final Mac var6 = this.helper.createContentMac(var5, var2);
      return new RecipientOperator(new MacCalculator() {
         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return var2;
         }

         public GenericKey getKey() {
            return new JceGenericKey(var2, var5);
         }

         public OutputStream getOutputStream() {
            return new MacOutputStream(var6);
         }

         public byte[] getMac() {
            return var6.doFinal();
         }
      });
   }
}
