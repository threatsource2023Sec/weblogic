package org.python.bouncycastle.pkcs.jcajce;

import java.io.InputStream;
import java.security.Provider;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.cryptopro.GOST28147Parameters;
import org.python.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.python.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.python.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.jcajce.PKCS12KeyWithParameters;
import org.python.bouncycastle.jcajce.spec.GOST28147ParameterSpec;
import org.python.bouncycastle.jcajce.spec.PBKDF2KeySpec;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.DefaultSecretKeySizeProvider;
import org.python.bouncycastle.operator.InputDecryptor;
import org.python.bouncycastle.operator.InputDecryptorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.SecretKeySizeProvider;

public class JcePKCSPBEInputDecryptorProviderBuilder {
   private JcaJceHelper helper = new DefaultJcaJceHelper();
   private boolean wrongPKCS12Zero = false;
   private SecretKeySizeProvider keySizeProvider;

   public JcePKCSPBEInputDecryptorProviderBuilder() {
      this.keySizeProvider = DefaultSecretKeySizeProvider.INSTANCE;
   }

   public JcePKCSPBEInputDecryptorProviderBuilder setProvider(Provider var1) {
      this.helper = new ProviderJcaJceHelper(var1);
      return this;
   }

   public JcePKCSPBEInputDecryptorProviderBuilder setProvider(String var1) {
      this.helper = new NamedJcaJceHelper(var1);
      return this;
   }

   public JcePKCSPBEInputDecryptorProviderBuilder setTryWrongPKCS12Zero(boolean var1) {
      this.wrongPKCS12Zero = var1;
      return this;
   }

   public JcePKCSPBEInputDecryptorProviderBuilder setKeySizeProvider(SecretKeySizeProvider var1) {
      this.keySizeProvider = var1;
      return this;
   }

   public InputDecryptorProvider build(final char[] var1) {
      return new InputDecryptorProvider() {
         private Cipher cipher;
         private AlgorithmIdentifier encryptionAlg;

         public InputDecryptor get(AlgorithmIdentifier var1x) throws OperatorCreationException {
            ASN1ObjectIdentifier var2 = var1x.getAlgorithm();

            try {
               if (var2.on(PKCSObjectIdentifiers.pkcs_12PbeIds)) {
                  PKCS12PBEParams var3 = PKCS12PBEParams.getInstance(var1x.getParameters());
                  this.cipher = JcePKCSPBEInputDecryptorProviderBuilder.this.helper.createCipher(var2.getId());
                  this.cipher.init(2, new PKCS12KeyWithParameters(var1, JcePKCSPBEInputDecryptorProviderBuilder.this.wrongPKCS12Zero, var3.getIV(), var3.getIterations().intValue()));
                  this.encryptionAlg = var1x;
               } else if (var2.equals(PKCSObjectIdentifiers.id_PBES2)) {
                  PBES2Parameters var11 = PBES2Parameters.getInstance(var1x.getParameters());
                  PBKDF2Params var4 = PBKDF2Params.getInstance(var11.getKeyDerivationFunc().getParameters());
                  AlgorithmIdentifier var5 = AlgorithmIdentifier.getInstance(var11.getEncryptionScheme());
                  SecretKeyFactory var6 = JcePKCSPBEInputDecryptorProviderBuilder.this.helper.createSecretKeyFactory(var11.getKeyDerivationFunc().getAlgorithm().getId());
                  SecretKey var7;
                  if (var4.isDefaultPrf()) {
                     var7 = var6.generateSecret(new PBEKeySpec(var1, var4.getSalt(), var4.getIterationCount().intValue(), JcePKCSPBEInputDecryptorProviderBuilder.this.keySizeProvider.getKeySize(var5)));
                  } else {
                     var7 = var6.generateSecret(new PBKDF2KeySpec(var1, var4.getSalt(), var4.getIterationCount().intValue(), JcePKCSPBEInputDecryptorProviderBuilder.this.keySizeProvider.getKeySize(var5), var4.getPrf()));
                  }

                  this.cipher = JcePKCSPBEInputDecryptorProviderBuilder.this.helper.createCipher(var11.getEncryptionScheme().getAlgorithm().getId());
                  this.encryptionAlg = AlgorithmIdentifier.getInstance(var11.getEncryptionScheme());
                  ASN1Encodable var8 = var11.getEncryptionScheme().getParameters();
                  if (var8 instanceof ASN1OctetString) {
                     this.cipher.init(2, var7, new IvParameterSpec(ASN1OctetString.getInstance(var8).getOctets()));
                  } else {
                     GOST28147Parameters var9 = GOST28147Parameters.getInstance(var8);
                     this.cipher.init(2, var7, new GOST28147ParameterSpec(var9.getEncryptionParamSet(), var9.getIV()));
                  }
               }
            } catch (Exception var10) {
               throw new OperatorCreationException("unable to create InputDecryptor: " + var10.getMessage(), var10);
            }

            return new InputDecryptor() {
               public AlgorithmIdentifier getAlgorithmIdentifier() {
                  return encryptionAlg;
               }

               public InputStream getInputStream(InputStream var1x) {
                  return new CipherInputStream(var1x, cipher);
               }
            };
         }
      };
   }
}
