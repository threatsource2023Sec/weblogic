package org.python.bouncycastle.pqc.jcajce.provider.sphincs;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.digests.SHA3Digest;
import org.python.bouncycastle.crypto.digests.SHA512tDigest;
import org.python.bouncycastle.pqc.crypto.sphincs.SPHINCS256KeyGenerationParameters;
import org.python.bouncycastle.pqc.crypto.sphincs.SPHINCS256KeyPairGenerator;
import org.python.bouncycastle.pqc.crypto.sphincs.SPHINCSPrivateKeyParameters;
import org.python.bouncycastle.pqc.crypto.sphincs.SPHINCSPublicKeyParameters;
import org.python.bouncycastle.pqc.jcajce.spec.SPHINCS256KeyGenParameterSpec;

public class Sphincs256KeyPairGeneratorSpi extends KeyPairGenerator {
   ASN1ObjectIdentifier treeDigest;
   SPHINCS256KeyGenerationParameters param;
   SPHINCS256KeyPairGenerator engine;
   SecureRandom random;
   boolean initialised;

   public Sphincs256KeyPairGeneratorSpi() {
      super("SPHINCS256");
      this.treeDigest = NISTObjectIdentifiers.id_sha512_256;
      this.engine = new SPHINCS256KeyPairGenerator();
      this.random = new SecureRandom();
      this.initialised = false;
   }

   public void initialize(int var1, SecureRandom var2) {
      throw new IllegalArgumentException("use AlgorithmParameterSpec");
   }

   public void initialize(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
      if (!(var1 instanceof SPHINCS256KeyGenParameterSpec)) {
         throw new InvalidAlgorithmParameterException("parameter object not a SPHINCS256KeyGenParameterSpec");
      } else {
         SPHINCS256KeyGenParameterSpec var3 = (SPHINCS256KeyGenParameterSpec)var1;
         if (var3.getTreeDigest().equals("SHA512-256")) {
            this.treeDigest = NISTObjectIdentifiers.id_sha512_256;
            this.param = new SPHINCS256KeyGenerationParameters(var2, new SHA512tDigest(256));
         } else if (var3.getTreeDigest().equals("SHA3-256")) {
            this.treeDigest = NISTObjectIdentifiers.id_sha3_256;
            this.param = new SPHINCS256KeyGenerationParameters(var2, new SHA3Digest(256));
         }

         this.engine.init(this.param);
         this.initialised = true;
      }
   }

   public KeyPair generateKeyPair() {
      if (!this.initialised) {
         this.param = new SPHINCS256KeyGenerationParameters(this.random, new SHA512tDigest(256));
         this.engine.init(this.param);
         this.initialised = true;
      }

      AsymmetricCipherKeyPair var1 = this.engine.generateKeyPair();
      SPHINCSPublicKeyParameters var2 = (SPHINCSPublicKeyParameters)var1.getPublic();
      SPHINCSPrivateKeyParameters var3 = (SPHINCSPrivateKeyParameters)var1.getPrivate();
      return new KeyPair(new BCSphincs256PublicKey(this.treeDigest, var2), new BCSphincs256PrivateKey(this.treeDigest, var3));
   }
}
