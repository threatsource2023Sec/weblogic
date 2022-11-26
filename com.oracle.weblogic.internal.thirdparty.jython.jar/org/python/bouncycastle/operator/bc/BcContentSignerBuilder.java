package org.python.bouncycastle.operator.bc;

import java.io.OutputStream;
import java.security.SecureRandom;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.operator.ContentSigner;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.RuntimeOperatorException;

public abstract class BcContentSignerBuilder {
   private SecureRandom random;
   private AlgorithmIdentifier sigAlgId;
   private AlgorithmIdentifier digAlgId;
   protected BcDigestProvider digestProvider;

   public BcContentSignerBuilder(AlgorithmIdentifier var1, AlgorithmIdentifier var2) {
      this.sigAlgId = var1;
      this.digAlgId = var2;
      this.digestProvider = BcDefaultDigestProvider.INSTANCE;
   }

   public BcContentSignerBuilder setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public ContentSigner build(AsymmetricKeyParameter var1) throws OperatorCreationException {
      final Signer var2 = this.createSigner(this.sigAlgId, this.digAlgId);
      if (this.random != null) {
         var2.init(true, new ParametersWithRandom(var1, this.random));
      } else {
         var2.init(true, var1);
      }

      return new ContentSigner() {
         private BcSignerOutputStream stream = new BcSignerOutputStream(var2);

         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return BcContentSignerBuilder.this.sigAlgId;
         }

         public OutputStream getOutputStream() {
            return this.stream;
         }

         public byte[] getSignature() {
            try {
               return this.stream.getSignature();
            } catch (CryptoException var2x) {
               throw new RuntimeOperatorException("exception obtaining signature: " + var2x.getMessage(), var2x);
            }
         }
      };
   }

   protected abstract Signer createSigner(AlgorithmIdentifier var1, AlgorithmIdentifier var2) throws OperatorCreationException;
}
