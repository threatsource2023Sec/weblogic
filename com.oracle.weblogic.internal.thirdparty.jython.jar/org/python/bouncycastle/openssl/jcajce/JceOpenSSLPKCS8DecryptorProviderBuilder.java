package org.python.bouncycastle.openssl.jcajce;

import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.pkcs.EncryptionScheme;
import org.python.bouncycastle.asn1.pkcs.KeyDerivationFunc;
import org.python.bouncycastle.asn1.pkcs.PBEParameter;
import org.python.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.python.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.python.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.CharToByteConverter;
import org.python.bouncycastle.jcajce.PBKDF1KeyWithParameters;
import org.python.bouncycastle.jcajce.PKCS12KeyWithParameters;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.openssl.PEMException;
import org.python.bouncycastle.operator.InputDecryptor;
import org.python.bouncycastle.operator.InputDecryptorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.util.Strings;

public class JceOpenSSLPKCS8DecryptorProviderBuilder {
   private JcaJceHelper helper = new DefaultJcaJceHelper();

   public JceOpenSSLPKCS8DecryptorProviderBuilder() {
      this.helper = new DefaultJcaJceHelper();
   }

   public JceOpenSSLPKCS8DecryptorProviderBuilder setProvider(String var1) {
      this.helper = new NamedJcaJceHelper(var1);
      return this;
   }

   public JceOpenSSLPKCS8DecryptorProviderBuilder setProvider(Provider var1) {
      this.helper = new ProviderJcaJceHelper(var1);
      return this;
   }

   public InputDecryptorProvider build(final char[] var1) throws OperatorCreationException {
      return new InputDecryptorProvider() {
         public InputDecryptor get(final AlgorithmIdentifier var1x) throws OperatorCreationException {
            try {
               final Cipher var10;
               if (PEMUtilities.isPKCS5Scheme2(var1x.getAlgorithm())) {
                  PBES2Parameters var2 = PBES2Parameters.getInstance(var1x.getParameters());
                  KeyDerivationFunc var3 = var2.getKeyDerivationFunc();
                  EncryptionScheme var4 = var2.getEncryptionScheme();
                  PBKDF2Params var5 = (PBKDF2Params)var3.getParameters();
                  int var6 = var5.getIterationCount().intValue();
                  byte[] var7 = var5.getSalt();
                  String var8 = var4.getAlgorithm().getId();
                  SecretKey var9 = PEMUtilities.generateSecretKeyForPKCS5Scheme2(JceOpenSSLPKCS8DecryptorProviderBuilder.this.helper, var8, var1, var7, var6);
                  var10 = JceOpenSSLPKCS8DecryptorProviderBuilder.this.helper.createCipher(var8);
                  AlgorithmParameters var11 = JceOpenSSLPKCS8DecryptorProviderBuilder.this.helper.createAlgorithmParameters(var8);
                  var11.init(var4.getParameters().toASN1Primitive().getEncoded());
                  var10.init(2, var9, var11);
               } else if (PEMUtilities.isPKCS12(var1x.getAlgorithm())) {
                  PKCS12PBEParams var14 = PKCS12PBEParams.getInstance(var1x.getParameters());
                  var10 = JceOpenSSLPKCS8DecryptorProviderBuilder.this.helper.createCipher(var1x.getAlgorithm().getId());
                  var10.init(2, new PKCS12KeyWithParameters(var1, var14.getIV(), var14.getIterations().intValue()));
               } else {
                  if (!PEMUtilities.isPKCS5Scheme1(var1x.getAlgorithm())) {
                     throw new PEMException("Unknown algorithm: " + var1x.getAlgorithm());
                  }

                  PBEParameter var15 = PBEParameter.getInstance(var1x.getParameters());
                  var10 = JceOpenSSLPKCS8DecryptorProviderBuilder.this.helper.createCipher(var1x.getAlgorithm().getId());
                  var10.init(2, new PBKDF1KeyWithParameters(var1, new CharToByteConverter() {
                     public String getType() {
                        return "ASCII";
                     }

                     public byte[] convert(char[] var1x) {
                        return Strings.toByteArray(var1x);
                     }
                  }, var15.getSalt(), var15.getIterationCount().intValue()));
               }

               return new InputDecryptor() {
                  public AlgorithmIdentifier getAlgorithmIdentifier() {
                     return var1x;
                  }

                  public InputStream getInputStream(InputStream var1xx) {
                     return new CipherInputStream(var1xx, var10);
                  }
               };
            } catch (IOException var12) {
               throw new OperatorCreationException(var1x.getAlgorithm() + " not available: " + var12.getMessage(), var12);
            } catch (GeneralSecurityException var13) {
               throw new OperatorCreationException(var1x.getAlgorithm() + " not available: " + var13.getMessage(), var13);
            }
         }
      };
   }
}
