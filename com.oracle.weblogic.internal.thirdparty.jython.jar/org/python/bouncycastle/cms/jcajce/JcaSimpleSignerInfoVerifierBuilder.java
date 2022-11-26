package org.python.bouncycastle.cms.jcajce;

import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cms.DefaultCMSSignatureAlgorithmNameGenerator;
import org.python.bouncycastle.cms.SignerInformationVerifier;
import org.python.bouncycastle.operator.ContentVerifierProvider;
import org.python.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.python.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

public class JcaSimpleSignerInfoVerifierBuilder {
   private Helper helper = new Helper();

   public JcaSimpleSignerInfoVerifierBuilder setProvider(Provider var1) {
      this.helper = new ProviderHelper(var1);
      return this;
   }

   public JcaSimpleSignerInfoVerifierBuilder setProvider(String var1) {
      this.helper = new NamedHelper(var1);
      return this;
   }

   public SignerInformationVerifier build(X509CertificateHolder var1) throws OperatorCreationException, CertificateException {
      return new SignerInformationVerifier(new DefaultCMSSignatureAlgorithmNameGenerator(), new DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(var1), this.helper.createDigestCalculatorProvider());
   }

   public SignerInformationVerifier build(X509Certificate var1) throws OperatorCreationException {
      return new SignerInformationVerifier(new DefaultCMSSignatureAlgorithmNameGenerator(), new DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(var1), this.helper.createDigestCalculatorProvider());
   }

   public SignerInformationVerifier build(PublicKey var1) throws OperatorCreationException {
      return new SignerInformationVerifier(new DefaultCMSSignatureAlgorithmNameGenerator(), new DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(var1), this.helper.createDigestCalculatorProvider());
   }

   private class Helper {
      private Helper() {
      }

      ContentVerifierProvider createContentVerifierProvider(PublicKey var1) throws OperatorCreationException {
         return (new JcaContentVerifierProviderBuilder()).build(var1);
      }

      ContentVerifierProvider createContentVerifierProvider(X509Certificate var1) throws OperatorCreationException {
         return (new JcaContentVerifierProviderBuilder()).build(var1);
      }

      ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder var1) throws OperatorCreationException, CertificateException {
         return (new JcaContentVerifierProviderBuilder()).build(var1);
      }

      DigestCalculatorProvider createDigestCalculatorProvider() throws OperatorCreationException {
         return (new JcaDigestCalculatorProviderBuilder()).build();
      }

      // $FF: synthetic method
      Helper(Object var2) {
         this();
      }
   }

   private class NamedHelper extends Helper {
      private final String providerName;

      public NamedHelper(String var2) {
         super(null);
         this.providerName = var2;
      }

      ContentVerifierProvider createContentVerifierProvider(PublicKey var1) throws OperatorCreationException {
         return (new JcaContentVerifierProviderBuilder()).setProvider(this.providerName).build(var1);
      }

      ContentVerifierProvider createContentVerifierProvider(X509Certificate var1) throws OperatorCreationException {
         return (new JcaContentVerifierProviderBuilder()).setProvider(this.providerName).build(var1);
      }

      DigestCalculatorProvider createDigestCalculatorProvider() throws OperatorCreationException {
         return (new JcaDigestCalculatorProviderBuilder()).setProvider(this.providerName).build();
      }

      ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder var1) throws OperatorCreationException, CertificateException {
         return (new JcaContentVerifierProviderBuilder()).setProvider(this.providerName).build(var1);
      }
   }

   private class ProviderHelper extends Helper {
      private final Provider provider;

      public ProviderHelper(Provider var2) {
         super(null);
         this.provider = var2;
      }

      ContentVerifierProvider createContentVerifierProvider(PublicKey var1) throws OperatorCreationException {
         return (new JcaContentVerifierProviderBuilder()).setProvider(this.provider).build(var1);
      }

      ContentVerifierProvider createContentVerifierProvider(X509Certificate var1) throws OperatorCreationException {
         return (new JcaContentVerifierProviderBuilder()).setProvider(this.provider).build(var1);
      }

      DigestCalculatorProvider createDigestCalculatorProvider() throws OperatorCreationException {
         return (new JcaDigestCalculatorProviderBuilder()).setProvider(this.provider).build();
      }

      ContentVerifierProvider createContentVerifierProvider(X509CertificateHolder var1) throws OperatorCreationException, CertificateException {
         return (new JcaContentVerifierProviderBuilder()).setProvider(this.provider).build(var1);
      }
   }
}
