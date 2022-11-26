package org.opensaml.xmlsec.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import org.opensaml.xmlsec.encryption.support.ChainingEncryptedKeyResolver;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.encryption.support.InlineEncryptedKeyResolver;
import org.opensaml.xmlsec.encryption.support.RSAOAEPParameters;
import org.opensaml.xmlsec.encryption.support.SimpleKeyInfoReferenceEncryptedKeyResolver;
import org.opensaml.xmlsec.encryption.support.SimpleRetrievalMethodEncryptedKeyResolver;
import org.opensaml.xmlsec.impl.BasicDecryptionConfiguration;
import org.opensaml.xmlsec.impl.BasicEncryptionConfiguration;
import org.opensaml.xmlsec.impl.BasicSignatureSigningConfiguration;
import org.opensaml.xmlsec.impl.BasicSignatureValidationConfiguration;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoGeneratorManager;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;
import org.opensaml.xmlsec.keyinfo.impl.BasicKeyInfoGeneratorFactory;
import org.opensaml.xmlsec.keyinfo.impl.BasicProviderKeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.impl.X509KeyInfoGeneratorFactory;
import org.opensaml.xmlsec.keyinfo.impl.provider.DEREncodedKeyValueProvider;
import org.opensaml.xmlsec.keyinfo.impl.provider.DSAKeyValueProvider;
import org.opensaml.xmlsec.keyinfo.impl.provider.InlineX509DataProvider;
import org.opensaml.xmlsec.keyinfo.impl.provider.RSAKeyValueProvider;

public class DefaultSecurityConfigurationBootstrap {
   protected DefaultSecurityConfigurationBootstrap() {
   }

   @Nonnull
   public static BasicEncryptionConfiguration buildDefaultEncryptionConfiguration() {
      BasicEncryptionConfiguration config = new BasicEncryptionConfiguration();
      config.setBlacklistedAlgorithms(Collections.singletonList("http://www.w3.org/2001/04/xmlenc#rsa-1_5"));
      config.setDataEncryptionAlgorithms(Arrays.asList("http://www.w3.org/2001/04/xmlenc#aes128-cbc", "http://www.w3.org/2001/04/xmlenc#aes192-cbc", "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc"));
      config.setKeyTransportEncryptionAlgorithms(Arrays.asList("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://www.w3.org/2001/04/xmlenc#kw-aes128", "http://www.w3.org/2001/04/xmlenc#kw-aes192", "http://www.w3.org/2001/04/xmlenc#kw-aes256", "http://www.w3.org/2001/04/xmlenc#kw-tripledes"));
      config.setRSAOAEPParameters(new RSAOAEPParameters("http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2009/xmlenc11#mgf1sha1", (String)null));
      config.setDataKeyInfoGeneratorManager(buildDataEncryptionKeyInfoGeneratorManager());
      config.setKeyTransportKeyInfoGeneratorManager(buildKeyTransportEncryptionKeyInfoGeneratorManager());
      return config;
   }

   @Nonnull
   public static BasicDecryptionConfiguration buildDefaultDecryptionConfiguration() {
      BasicDecryptionConfiguration config = new BasicDecryptionConfiguration();
      config.setBlacklistedAlgorithms(Collections.singletonList("http://www.w3.org/2001/04/xmlenc#rsa-1_5"));
      config.setEncryptedKeyResolver(buildBasicEncryptedKeyResolver());
      return config;
   }

   @Nonnull
   public static BasicSignatureSigningConfiguration buildDefaultSignatureSigningConfiguration() {
      BasicSignatureSigningConfiguration config = new BasicSignatureSigningConfiguration();
      config.setBlacklistedAlgorithms(Arrays.asList("http://www.w3.org/2001/04/xmldsig-more#md5", "http://www.w3.org/2001/04/xmldsig-more#rsa-md5", "http://www.w3.org/2001/04/xmldsig-more#hmac-md5"));
      config.setSignatureAlgorithms(Arrays.asList("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384", "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512", "http://www.w3.org/2000/09/xmldsig#rsa-sha1", "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256", "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384", "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512", "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1", "http://www.w3.org/2000/09/xmldsig#dsa-sha1", "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256", "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384", "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512", "http://www.w3.org/2000/09/xmldsig#hmac-sha1"));
      config.setSignatureReferenceDigestMethods(Arrays.asList("http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmldsig-more#sha384", "http://www.w3.org/2001/04/xmlenc#sha512", "http://www.w3.org/2000/09/xmldsig#sha1"));
      config.setSignatureCanonicalizationAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#");
      config.setKeyInfoGeneratorManager(buildSignatureKeyInfoGeneratorManager());
      return config;
   }

   @Nonnull
   public static BasicSignatureValidationConfiguration buildDefaultSignatureValidationConfiguration() {
      BasicSignatureValidationConfiguration config = new BasicSignatureValidationConfiguration();
      config.setBlacklistedAlgorithms(Arrays.asList("http://www.w3.org/2001/04/xmldsig-more#md5", "http://www.w3.org/2001/04/xmldsig-more#rsa-md5", "http://www.w3.org/2001/04/xmldsig-more#hmac-md5"));
      return config;
   }

   protected static EncryptedKeyResolver buildBasicEncryptedKeyResolver() {
      List resolverChain = new ArrayList();
      resolverChain.add(new InlineEncryptedKeyResolver());
      resolverChain.add(new SimpleRetrievalMethodEncryptedKeyResolver());
      resolverChain.add(new SimpleKeyInfoReferenceEncryptedKeyResolver());
      return new ChainingEncryptedKeyResolver(resolverChain);
   }

   public static KeyInfoCredentialResolver buildBasicInlineKeyInfoCredentialResolver() {
      ArrayList providers = new ArrayList();
      providers.add(new RSAKeyValueProvider());
      providers.add(new DSAKeyValueProvider());
      providers.add(new DEREncodedKeyValueProvider());
      providers.add(new InlineX509DataProvider());
      KeyInfoCredentialResolver resolver = new BasicProviderKeyInfoCredentialResolver(providers);
      return resolver;
   }

   protected static NamedKeyInfoGeneratorManager buildDataEncryptionKeyInfoGeneratorManager() {
      return buildBasicKeyInfoGeneratorManager();
   }

   protected static NamedKeyInfoGeneratorManager buildKeyTransportEncryptionKeyInfoGeneratorManager() {
      return buildBasicKeyInfoGeneratorManager();
   }

   protected static NamedKeyInfoGeneratorManager buildSignatureKeyInfoGeneratorManager() {
      NamedKeyInfoGeneratorManager namedManager = new NamedKeyInfoGeneratorManager();
      namedManager.setUseDefaultManager(true);
      KeyInfoGeneratorManager defaultManager = namedManager.getDefaultManager();
      BasicKeyInfoGeneratorFactory basicFactory = new BasicKeyInfoGeneratorFactory();
      basicFactory.setEmitPublicKeyValue(true);
      basicFactory.setEmitPublicDEREncodedKeyValue(true);
      basicFactory.setEmitKeyNames(true);
      X509KeyInfoGeneratorFactory x509Factory = new X509KeyInfoGeneratorFactory();
      x509Factory.setEmitEntityCertificate(true);
      x509Factory.setEmitEntityCertificateChain(true);
      defaultManager.registerFactory(basicFactory);
      defaultManager.registerFactory(x509Factory);
      return namedManager;
   }

   public static NamedKeyInfoGeneratorManager buildBasicKeyInfoGeneratorManager() {
      NamedKeyInfoGeneratorManager namedManager = new NamedKeyInfoGeneratorManager();
      namedManager.setUseDefaultManager(true);
      KeyInfoGeneratorManager defaultManager = namedManager.getDefaultManager();
      BasicKeyInfoGeneratorFactory basicFactory = new BasicKeyInfoGeneratorFactory();
      basicFactory.setEmitPublicKeyValue(true);
      basicFactory.setEmitPublicDEREncodedKeyValue(true);
      basicFactory.setEmitKeyNames(true);
      X509KeyInfoGeneratorFactory x509Factory = new X509KeyInfoGeneratorFactory();
      x509Factory.setEmitEntityCertificate(true);
      defaultManager.registerFactory(basicFactory);
      defaultManager.registerFactory(x509Factory);
      return namedManager;
   }
}
