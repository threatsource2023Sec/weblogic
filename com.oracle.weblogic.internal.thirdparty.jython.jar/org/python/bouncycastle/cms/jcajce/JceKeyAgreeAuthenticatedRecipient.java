package org.python.bouncycastle.cms.jcajce;

import java.io.OutputStream;
import java.security.Key;
import java.security.PrivateKey;
import javax.crypto.Mac;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.RecipientOperator;
import org.python.bouncycastle.jcajce.io.MacOutputStream;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.operator.jcajce.JceGenericKey;

public class JceKeyAgreeAuthenticatedRecipient extends JceKeyAgreeRecipient {
   public JceKeyAgreeAuthenticatedRecipient(PrivateKey var1) {
      super(var1);
   }

   public RecipientOperator getRecipientOperator(AlgorithmIdentifier var1, final AlgorithmIdentifier var2, SubjectPublicKeyInfo var3, ASN1OctetString var4, byte[] var5) throws CMSException {
      final Key var6 = this.extractSecretKey(var1, var2, var3, var4, var5);
      final Mac var7 = this.contentHelper.createContentMac(var6, var2);
      return new RecipientOperator(new MacCalculator() {
         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return var2;
         }

         public GenericKey getKey() {
            return new JceGenericKey(var2, var6);
         }

         public OutputStream getOutputStream() {
            return new MacOutputStream(var7);
         }

         public byte[] getMac() {
            return var7.doFinal();
         }
      });
   }
}
