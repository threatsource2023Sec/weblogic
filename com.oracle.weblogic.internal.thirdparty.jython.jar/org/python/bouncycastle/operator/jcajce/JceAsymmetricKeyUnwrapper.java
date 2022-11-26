package org.python.bouncycastle.operator.jcajce;

import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.AsymmetricKeyUnwrapper;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OperatorException;

public class JceAsymmetricKeyUnwrapper extends AsymmetricKeyUnwrapper {
   private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
   private Map extraMappings = new HashMap();
   private PrivateKey privKey;
   private boolean unwrappedKeyMustBeEncodable;

   public JceAsymmetricKeyUnwrapper(AlgorithmIdentifier var1, PrivateKey var2) {
      super(var1);
      this.privKey = var2;
   }

   public JceAsymmetricKeyUnwrapper setProvider(Provider var1) {
      this.helper = new OperatorHelper(new ProviderJcaJceHelper(var1));
      return this;
   }

   public JceAsymmetricKeyUnwrapper setProvider(String var1) {
      this.helper = new OperatorHelper(new NamedJcaJceHelper(var1));
      return this;
   }

   public JceAsymmetricKeyUnwrapper setMustProduceEncodableUnwrappedKey(boolean var1) {
      this.unwrappedKeyMustBeEncodable = var1;
      return this;
   }

   public JceAsymmetricKeyUnwrapper setAlgorithmMapping(ASN1ObjectIdentifier var1, String var2) {
      this.extraMappings.put(var1, var2);
      return this;
   }

   public GenericKey generateUnwrappedKey(AlgorithmIdentifier var1, byte[] var2) throws OperatorException {
      try {
         Object var3 = null;
         Cipher var4 = this.helper.createAsymmetricWrapper(this.getAlgorithmIdentifier().getAlgorithm(), this.extraMappings);
         AlgorithmParameters var5 = this.helper.createAlgorithmParameters(this.getAlgorithmIdentifier());

         try {
            if (var5 != null) {
               var4.init(4, this.privKey, var5);
            } else {
               var4.init(4, this.privKey);
            }

            var3 = var4.unwrap(var2, this.helper.getKeyAlgorithmName(var1.getAlgorithm()), 3);
            if (this.unwrappedKeyMustBeEncodable) {
               try {
                  byte[] var6 = ((Key)var3).getEncoded();
                  if (var6 == null || var6.length == 0) {
                     var3 = null;
                  }
               } catch (Exception var7) {
                  var3 = null;
               }
            }
         } catch (GeneralSecurityException var8) {
         } catch (IllegalStateException var9) {
         } catch (UnsupportedOperationException var10) {
         } catch (ProviderException var11) {
         }

         if (var3 == null) {
            var4.init(2, this.privKey);
            var3 = new SecretKeySpec(var4.doFinal(var2), var1.getAlgorithm().getId());
         }

         return new JceGenericKey(var1, (Key)var3);
      } catch (InvalidKeyException var12) {
         throw new OperatorException("key invalid: " + var12.getMessage(), var12);
      } catch (IllegalBlockSizeException var13) {
         throw new OperatorException("illegal blocksize: " + var13.getMessage(), var13);
      } catch (BadPaddingException var14) {
         throw new OperatorException("bad padding: " + var14.getMessage(), var14);
      }
   }
}
