package org.python.bouncycastle.cms.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.PrivateKey;
import javax.crypto.Mac;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.KeyTransRecipientId;
import org.python.bouncycastle.cms.RecipientOperator;
import org.python.bouncycastle.jcajce.io.MacOutputStream;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.operator.jcajce.JceGenericKey;

public class JceKTSKeyTransAuthenticatedRecipient extends JceKTSKeyTransRecipient {
   public JceKTSKeyTransAuthenticatedRecipient(PrivateKey var1, KeyTransRecipientId var2) throws IOException {
      super(var1, getPartyVInfoFromRID(var2));
   }

   public RecipientOperator getRecipientOperator(AlgorithmIdentifier var1, final AlgorithmIdentifier var2, byte[] var3) throws CMSException {
      final Key var4 = this.extractSecretKey(var1, var2, var3);
      final Mac var5 = this.contentHelper.createContentMac(var4, var2);
      return new RecipientOperator(new MacCalculator() {
         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return var2;
         }

         public GenericKey getKey() {
            return new JceGenericKey(var2, var4);
         }

         public OutputStream getOutputStream() {
            return new MacOutputStream(var5);
         }

         public byte[] getMac() {
            return var5.doFinal();
         }
      });
   }
}
