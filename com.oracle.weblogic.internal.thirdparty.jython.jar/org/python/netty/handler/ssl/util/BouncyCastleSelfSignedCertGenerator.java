package org.python.netty.handler.ssl.util;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cert.X509v3CertificateBuilder;
import org.python.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.python.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.operator.ContentSigner;
import org.python.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

final class BouncyCastleSelfSignedCertGenerator {
   private static final Provider PROVIDER = new BouncyCastleProvider();

   static String[] generate(String fqdn, KeyPair keypair, SecureRandom random, Date notBefore, Date notAfter) throws Exception {
      PrivateKey key = keypair.getPrivate();
      X500Name owner = new X500Name("CN=" + fqdn);
      X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(owner, new BigInteger(64, random), notBefore, notAfter, owner, keypair.getPublic());
      ContentSigner signer = (new JcaContentSignerBuilder("SHA256WithRSAEncryption")).build(key);
      X509CertificateHolder certHolder = builder.build(signer);
      X509Certificate cert = (new JcaX509CertificateConverter()).setProvider(PROVIDER).getCertificate(certHolder);
      cert.verify(keypair.getPublic());
      return SelfSignedCertificate.newSelfSignedCertificate(fqdn, key, cert);
   }

   private BouncyCastleSelfSignedCertGenerator() {
   }
}
