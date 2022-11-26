package org.python.bouncycastle.openssl.jcajce;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.openssl.PEMException;
import org.python.bouncycastle.openssl.PEMKeyPair;

public class JcaPEMKeyConverter {
   private JcaJceHelper helper = new DefaultJcaJceHelper();
   private static final Map algorithms = new HashMap();

   public JcaPEMKeyConverter setProvider(Provider var1) {
      this.helper = new ProviderJcaJceHelper(var1);
      return this;
   }

   public JcaPEMKeyConverter setProvider(String var1) {
      this.helper = new NamedJcaJceHelper(var1);
      return this;
   }

   public KeyPair getKeyPair(PEMKeyPair var1) throws PEMException {
      try {
         KeyFactory var2 = this.getKeyFactory(var1.getPrivateKeyInfo().getPrivateKeyAlgorithm());
         return new KeyPair(var2.generatePublic(new X509EncodedKeySpec(var1.getPublicKeyInfo().getEncoded())), var2.generatePrivate(new PKCS8EncodedKeySpec(var1.getPrivateKeyInfo().getEncoded())));
      } catch (Exception var3) {
         throw new PEMException("unable to convert key pair: " + var3.getMessage(), var3);
      }
   }

   public PublicKey getPublicKey(SubjectPublicKeyInfo var1) throws PEMException {
      try {
         KeyFactory var2 = this.getKeyFactory(var1.getAlgorithm());
         return var2.generatePublic(new X509EncodedKeySpec(var1.getEncoded()));
      } catch (Exception var3) {
         throw new PEMException("unable to convert key pair: " + var3.getMessage(), var3);
      }
   }

   public PrivateKey getPrivateKey(PrivateKeyInfo var1) throws PEMException {
      try {
         KeyFactory var2 = this.getKeyFactory(var1.getPrivateKeyAlgorithm());
         return var2.generatePrivate(new PKCS8EncodedKeySpec(var1.getEncoded()));
      } catch (Exception var3) {
         throw new PEMException("unable to convert key pair: " + var3.getMessage(), var3);
      }
   }

   private KeyFactory getKeyFactory(AlgorithmIdentifier var1) throws NoSuchAlgorithmException, NoSuchProviderException {
      ASN1ObjectIdentifier var2 = var1.getAlgorithm();
      String var3 = (String)algorithms.get(var2);
      if (var3 == null) {
         var3 = var2.getId();
      }

      try {
         return this.helper.createKeyFactory(var3);
      } catch (NoSuchAlgorithmException var5) {
         if (var3.equals("ECDSA")) {
            return this.helper.createKeyFactory("EC");
         } else {
            throw var5;
         }
      }
   }

   static {
      algorithms.put(X9ObjectIdentifiers.id_ecPublicKey, "ECDSA");
      algorithms.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
      algorithms.put(X9ObjectIdentifiers.id_dsa, "DSA");
   }
}
