package org.python.bouncycastle.cms.jcajce;

import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.python.bouncycastle.asn1.cms.AttributeTable;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.python.bouncycastle.cms.CMSAttributeTableGenerator;
import org.python.bouncycastle.cms.DefaultSignedAttributeTableGenerator;
import org.python.bouncycastle.cms.SignerInfoGenerator;
import org.python.bouncycastle.cms.SignerInfoGeneratorBuilder;
import org.python.bouncycastle.operator.ContentSigner;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.python.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

public class JcaSimpleSignerInfoGeneratorBuilder {
   private Helper helper = new Helper();
   private boolean hasNoSignedAttributes;
   private CMSAttributeTableGenerator signedGen;
   private CMSAttributeTableGenerator unsignedGen;

   public JcaSimpleSignerInfoGeneratorBuilder() throws OperatorCreationException {
   }

   public JcaSimpleSignerInfoGeneratorBuilder setProvider(String var1) throws OperatorCreationException {
      this.helper = new NamedHelper(var1);
      return this;
   }

   public JcaSimpleSignerInfoGeneratorBuilder setProvider(Provider var1) throws OperatorCreationException {
      this.helper = new ProviderHelper(var1);
      return this;
   }

   public JcaSimpleSignerInfoGeneratorBuilder setDirectSignature(boolean var1) {
      this.hasNoSignedAttributes = var1;
      return this;
   }

   public JcaSimpleSignerInfoGeneratorBuilder setSignedAttributeGenerator(CMSAttributeTableGenerator var1) {
      this.signedGen = var1;
      return this;
   }

   public JcaSimpleSignerInfoGeneratorBuilder setSignedAttributeGenerator(AttributeTable var1) {
      this.signedGen = new DefaultSignedAttributeTableGenerator(var1);
      return this;
   }

   public JcaSimpleSignerInfoGeneratorBuilder setUnsignedAttributeGenerator(CMSAttributeTableGenerator var1) {
      this.unsignedGen = var1;
      return this;
   }

   public SignerInfoGenerator build(String var1, PrivateKey var2, X509Certificate var3) throws OperatorCreationException, CertificateEncodingException {
      ContentSigner var4 = this.helper.createContentSigner(var1, var2);
      return this.configureAndBuild().build(var4, (X509CertificateHolder)(new JcaX509CertificateHolder(var3)));
   }

   public SignerInfoGenerator build(String var1, PrivateKey var2, byte[] var3) throws OperatorCreationException, CertificateEncodingException {
      ContentSigner var4 = this.helper.createContentSigner(var1, var2);
      return this.configureAndBuild().build(var4, var3);
   }

   private SignerInfoGeneratorBuilder configureAndBuild() throws OperatorCreationException {
      SignerInfoGeneratorBuilder var1 = new SignerInfoGeneratorBuilder(this.helper.createDigestCalculatorProvider());
      var1.setDirectSignature(this.hasNoSignedAttributes);
      var1.setSignedAttributeGenerator(this.signedGen);
      var1.setUnsignedAttributeGenerator(this.unsignedGen);
      return var1;
   }

   private class Helper {
      private Helper() {
      }

      ContentSigner createContentSigner(String var1, PrivateKey var2) throws OperatorCreationException {
         return (new JcaContentSignerBuilder(var1)).build(var2);
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

      ContentSigner createContentSigner(String var1, PrivateKey var2) throws OperatorCreationException {
         return (new JcaContentSignerBuilder(var1)).setProvider(this.providerName).build(var2);
      }

      DigestCalculatorProvider createDigestCalculatorProvider() throws OperatorCreationException {
         return (new JcaDigestCalculatorProviderBuilder()).setProvider(this.providerName).build();
      }
   }

   private class ProviderHelper extends Helper {
      private final Provider provider;

      public ProviderHelper(Provider var2) {
         super(null);
         this.provider = var2;
      }

      ContentSigner createContentSigner(String var1, PrivateKey var2) throws OperatorCreationException {
         return (new JcaContentSignerBuilder(var1)).setProvider(this.provider).build(var2);
      }

      DigestCalculatorProvider createDigestCalculatorProvider() throws OperatorCreationException {
         return (new JcaDigestCalculatorProviderBuilder()).setProvider(this.provider).build();
      }
   }
}
