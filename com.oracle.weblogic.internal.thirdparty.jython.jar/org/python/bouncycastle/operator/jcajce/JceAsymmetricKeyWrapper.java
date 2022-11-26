package org.python.bouncycastle.operator.jcajce;

import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Provider;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.RSAESOAEPparams;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.AsymmetricKeyWrapper;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OperatorException;

public class JceAsymmetricKeyWrapper extends AsymmetricKeyWrapper {
   private OperatorHelper helper;
   private Map extraMappings;
   private PublicKey publicKey;
   private SecureRandom random;
   private static final Map digests = new HashMap();

   public JceAsymmetricKeyWrapper(PublicKey var1) {
      super(SubjectPublicKeyInfo.getInstance(var1.getEncoded()).getAlgorithm());
      this.helper = new OperatorHelper(new DefaultJcaJceHelper());
      this.extraMappings = new HashMap();
      this.publicKey = var1;
   }

   public JceAsymmetricKeyWrapper(X509Certificate var1) {
      this(var1.getPublicKey());
   }

   public JceAsymmetricKeyWrapper(AlgorithmIdentifier var1, PublicKey var2) {
      super(var1);
      this.helper = new OperatorHelper(new DefaultJcaJceHelper());
      this.extraMappings = new HashMap();
      this.publicKey = var2;
   }

   public JceAsymmetricKeyWrapper(AlgorithmParameterSpec var1, PublicKey var2) {
      super(extractFromSpec(var1));
      this.helper = new OperatorHelper(new DefaultJcaJceHelper());
      this.extraMappings = new HashMap();
      this.publicKey = var2;
   }

   public JceAsymmetricKeyWrapper setProvider(Provider var1) {
      this.helper = new OperatorHelper(new ProviderJcaJceHelper(var1));
      return this;
   }

   public JceAsymmetricKeyWrapper setProvider(String var1) {
      this.helper = new OperatorHelper(new NamedJcaJceHelper(var1));
      return this;
   }

   public JceAsymmetricKeyWrapper setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public JceAsymmetricKeyWrapper setAlgorithmMapping(ASN1ObjectIdentifier var1, String var2) {
      this.extraMappings.put(var1, var2);
      return this;
   }

   public byte[] generateWrappedKey(GenericKey var1) throws OperatorException {
      Cipher var2 = this.helper.createAsymmetricWrapper(this.getAlgorithmIdentifier().getAlgorithm(), this.extraMappings);
      AlgorithmParameters var3 = this.helper.createAlgorithmParameters(this.getAlgorithmIdentifier());
      byte[] var4 = null;

      try {
         if (var3 != null) {
            var2.init(3, this.publicKey, var3, this.random);
         } else {
            var2.init(3, this.publicKey, this.random);
         }

         var4 = var2.wrap(OperatorUtils.getJceKey(var1));
      } catch (InvalidKeyException var8) {
      } catch (GeneralSecurityException var9) {
      } catch (IllegalStateException var10) {
      } catch (UnsupportedOperationException var11) {
      } catch (ProviderException var12) {
      }

      if (var4 == null) {
         try {
            var2.init(1, this.publicKey, this.random);
            var4 = var2.doFinal(OperatorUtils.getJceKey(var1).getEncoded());
         } catch (InvalidKeyException var6) {
            throw new OperatorException("unable to encrypt contents key", var6);
         } catch (GeneralSecurityException var7) {
            throw new OperatorException("unable to encrypt contents key", var7);
         }
      }

      return var4;
   }

   private static AlgorithmIdentifier extractFromSpec(AlgorithmParameterSpec var0) {
      if (var0 instanceof OAEPParameterSpec) {
         OAEPParameterSpec var1 = (OAEPParameterSpec)var0;
         if (var1.getMGFAlgorithm().equals(OAEPParameterSpec.DEFAULT.getMGFAlgorithm())) {
            if (var1.getPSource() instanceof PSource.PSpecified) {
               return new AlgorithmIdentifier(PKCSObjectIdentifiers.id_RSAES_OAEP, new RSAESOAEPparams(getDigest(var1.getDigestAlgorithm()), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, getDigest(((MGF1ParameterSpec)var1.getMGFParameters()).getDigestAlgorithm())), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(((PSource.PSpecified)var1.getPSource()).getValue()))));
            } else {
               throw new IllegalArgumentException("unknown PSource: " + var1.getPSource().getAlgorithm());
            }
         } else {
            throw new IllegalArgumentException("unknown MGF: " + var1.getMGFAlgorithm());
         }
      } else {
         throw new IllegalArgumentException("unknown spec: " + var0.getClass().getName());
      }
   }

   private static AlgorithmIdentifier getDigest(String var0) {
      AlgorithmIdentifier var1 = (AlgorithmIdentifier)digests.get(var0);
      if (var1 != null) {
         return var1;
      } else {
         throw new IllegalArgumentException("unknown digest name: " + var0);
      }
   }

   static {
      digests.put("SHA-1", new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE));
      digests.put("SHA-1", new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE));
      digests.put("SHA224", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, DERNull.INSTANCE));
      digests.put("SHA-224", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, DERNull.INSTANCE));
      digests.put("SHA256", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE));
      digests.put("SHA-256", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE));
      digests.put("SHA384", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, DERNull.INSTANCE));
      digests.put("SHA-384", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, DERNull.INSTANCE));
      digests.put("SHA512", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, DERNull.INSTANCE));
      digests.put("SHA-512", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, DERNull.INSTANCE));
      digests.put("SHA512/224", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512_224, DERNull.INSTANCE));
      digests.put("SHA-512/224", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512_224, DERNull.INSTANCE));
      digests.put("SHA-512(224)", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512_224, DERNull.INSTANCE));
      digests.put("SHA512/256", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512_256, DERNull.INSTANCE));
      digests.put("SHA-512/256", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512_256, DERNull.INSTANCE));
      digests.put("SHA-512(256)", new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512_256, DERNull.INSTANCE));
   }
}
