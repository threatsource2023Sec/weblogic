package org.opensaml.xmlsec.encryption.support;

import com.google.common.base.Strings;
import java.security.Key;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.xml.security.Init;
import org.apache.xml.security.encryption.EncryptionMethod;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Encrypter {
   private final Logger log = LoggerFactory.getLogger(Encrypter.class);
   private final Unmarshaller encryptedDataUnmarshaller;
   private final Unmarshaller encryptedKeyUnmarshaller;
   private final XMLSignatureBuilder keyInfoBuilder;
   private String jcaProviderName;

   public Encrypter() {
      UnmarshallerFactory unmarshallerFactory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();
      this.encryptedDataUnmarshaller = unmarshallerFactory.getUnmarshaller(EncryptedData.DEFAULT_ELEMENT_NAME);
      this.encryptedKeyUnmarshaller = unmarshallerFactory.getUnmarshaller(EncryptedKey.DEFAULT_ELEMENT_NAME);
      Constraint.isNotNull(this.encryptedDataUnmarshaller, "EncryptedData unmarshaller not configured");
      Constraint.isNotNull(this.encryptedKeyUnmarshaller, "EncryptedKey unmarshaller not configured");
      XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
      this.keyInfoBuilder = (XMLSignatureBuilder)builderFactory.getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME);
      Constraint.isNotNull(this.keyInfoBuilder, "KeyInfo builder not configured");
   }

   @Nullable
   public String getJCAProviderName() {
      return this.jcaProviderName;
   }

   public void setJCAProviderName(@Nullable String providerName) {
      this.jcaProviderName = providerName;
   }

   @Nonnull
   public EncryptedData encryptElement(@Nonnull XMLObject xmlObject, @Nonnull DataEncryptionParameters encParams) throws EncryptionException {
      List emptyKEKParamsList = new ArrayList();
      return this.encryptElement(xmlObject, (DataEncryptionParameters)encParams, (List)emptyKEKParamsList, false);
   }

   @Nonnull
   public EncryptedData encryptElement(@Nonnull XMLObject xmlObject, @Nonnull DataEncryptionParameters encParams, @Nonnull KeyEncryptionParameters kekParams) throws EncryptionException {
      List kekParamsList = new ArrayList();
      kekParamsList.add(kekParams);
      return this.encryptElement(xmlObject, (DataEncryptionParameters)encParams, (List)kekParamsList, false);
   }

   @Nonnull
   public EncryptedData encryptElement(@Nonnull XMLObject xmlObject, @Nonnull DataEncryptionParameters encParams, @Nonnull List kekParamsList) throws EncryptionException {
      return this.encryptElement(xmlObject, encParams, kekParamsList, false);
   }

   @Nonnull
   public EncryptedData encryptElementContent(@Nonnull XMLObject xmlObject, @Nonnull DataEncryptionParameters encParams) throws EncryptionException {
      List emptyKEKParamsList = new ArrayList();
      return this.encryptElement(xmlObject, (DataEncryptionParameters)encParams, (List)emptyKEKParamsList, true);
   }

   @Nonnull
   public EncryptedData encryptElementContent(@Nonnull XMLObject xmlObject, @Nonnull DataEncryptionParameters encParams, @Nonnull KeyEncryptionParameters kekParams) throws EncryptionException {
      List kekParamsList = new ArrayList();
      kekParamsList.add(kekParams);
      return this.encryptElement(xmlObject, (DataEncryptionParameters)encParams, (List)kekParamsList, true);
   }

   @Nonnull
   public EncryptedData encryptElementContent(@Nonnull XMLObject xmlObject, @Nonnull DataEncryptionParameters encParams, @Nonnull List kekParamsList) throws EncryptionException {
      return this.encryptElement(xmlObject, encParams, kekParamsList, true);
   }

   @Nonnull
   public List encryptKey(@Nonnull Key key, @Nonnull List kekParamsList, @Nonnull Document containingDocument) throws EncryptionException {
      this.checkParams(kekParamsList, false);
      List encKeys = new ArrayList();
      Iterator var5 = kekParamsList.iterator();

      while(var5.hasNext()) {
         KeyEncryptionParameters kekParam = (KeyEncryptionParameters)var5.next();
         encKeys.add(this.encryptKey(key, kekParam, containingDocument));
      }

      return encKeys;
   }

   @Nonnull
   public EncryptedKey encryptKey(@Nonnull Key key, @Nonnull KeyEncryptionParameters kekParams, @Nonnull Document containingDocument) throws EncryptionException {
      this.checkParams(kekParams, false);
      Key encryptionKey = CredentialSupport.extractEncryptionKey(kekParams.getEncryptionCredential());
      EncryptedKey encryptedKey = this.encryptKey(key, encryptionKey, kekParams.getAlgorithm(), kekParams.getRSAOAEPParameters(), containingDocument);
      if (kekParams.getKeyInfoGenerator() != null) {
         KeyInfoGenerator generator = kekParams.getKeyInfoGenerator();
         this.log.debug("Dynamically generating KeyInfo from Credential for EncryptedKey using generator: {}", generator.getClass().getName());

         try {
            encryptedKey.setKeyInfo(generator.generate(kekParams.getEncryptionCredential()));
         } catch (SecurityException var8) {
            this.log.error("Error during EncryptedKey KeyInfo generation", var8);
            throw new EncryptionException("Error during EncryptedKey KeyInfo generation", var8);
         }
      }

      if (kekParams.getRecipient() != null) {
         encryptedKey.setRecipient(kekParams.getRecipient());
      }

      return encryptedKey;
   }

   @Nonnull
   protected EncryptedKey encryptKey(@Nonnull Key targetKey, @Nonnull Key encryptionKey, @Nonnull String encryptionAlgorithmURI, @Nullable RSAOAEPParameters rsaOAEPParams, @Nonnull Document containingDocument) throws EncryptionException {
      Constraint.isNotNull(encryptionAlgorithmURI, "Encryption algorithm URI cannot be null");
      Constraint.isNotNull(containingDocument, "Containing document cannot be null");
      if (targetKey == null) {
         this.log.error("Target key for key encryption was null");
         throw new EncryptionException("Target key was null");
      } else if (encryptionKey == null) {
         this.log.error("Encryption key for key encryption was null");
         throw new EncryptionException("Encryption key was null");
      } else {
         this.log.debug("Encrypting encryption key with algorithm: {}", encryptionAlgorithmURI);

         XMLCipher xmlCipher;
         try {
            xmlCipher = this.buildXMLCipher(encryptionKey, encryptionAlgorithmURI, rsaOAEPParams);
         } catch (XMLEncryptionException var10) {
            this.log.error("Error initializing cipher instance on key encryption", var10);
            throw new EncryptionException("Error initializing cipher instance on key encryption", var10);
         }

         org.apache.xml.security.encryption.EncryptedKey apacheEncryptedKey;
         try {
            if (AlgorithmSupport.isRSAOAEP(encryptionAlgorithmURI) && rsaOAEPParams != null) {
               apacheEncryptedKey = xmlCipher.encryptKey(containingDocument, targetKey, this.getEffectiveMGF(encryptionAlgorithmURI, rsaOAEPParams), this.decodeOAEPParams(rsaOAEPParams.getOAEPParams()));
            } else {
               apacheEncryptedKey = xmlCipher.encryptKey(containingDocument, targetKey);
            }

            this.postProcessApacheEncryptedKey(apacheEncryptedKey, targetKey, encryptionKey, encryptionAlgorithmURI, containingDocument);
         } catch (XMLEncryptionException var11) {
            this.log.error("Error encrypting element on key encryption", var11);
            throw new EncryptionException("Error encrypting element on key encryption", var11);
         }

         try {
            Element encKeyElement = xmlCipher.martial(containingDocument, apacheEncryptedKey);
            return (EncryptedKey)this.encryptedKeyUnmarshaller.unmarshall(encKeyElement);
         } catch (UnmarshallingException var9) {
            this.log.error("Error unmarshalling EncryptedKey element", var9);
            throw new EncryptionException("Error unmarshalling EncryptedKey element");
         }
      }
   }

   @Nonnull
   protected XMLCipher buildXMLCipher(@Nonnull Key encryptionKey, @Nonnull String encryptionAlgorithmURI, @Nullable RSAOAEPParameters rsaOAEPParams) throws XMLEncryptionException {
      XMLCipher xmlCipher;
      if (this.getJCAProviderName() != null) {
         if (AlgorithmSupport.isRSAOAEP(encryptionAlgorithmURI) && rsaOAEPParams != null && rsaOAEPParams.getDigestMethod() != null) {
            xmlCipher = XMLCipher.getProviderInstance(encryptionAlgorithmURI, this.getJCAProviderName(), (String)null, rsaOAEPParams.getDigestMethod());
         } else {
            xmlCipher = XMLCipher.getProviderInstance(encryptionAlgorithmURI, this.getJCAProviderName());
         }
      } else if (AlgorithmSupport.isRSAOAEP(encryptionAlgorithmURI) && rsaOAEPParams != null && rsaOAEPParams.getDigestMethod() != null) {
         xmlCipher = XMLCipher.getInstance(encryptionAlgorithmURI, (String)null, rsaOAEPParams.getDigestMethod());
      } else {
         xmlCipher = XMLCipher.getInstance(encryptionAlgorithmURI);
      }

      xmlCipher.init(3, encryptionKey);
      return xmlCipher;
   }

   @Nullable
   protected String getEffectiveMGF(@Nonnull String encryptionAlgorithmURI, @Nullable RSAOAEPParameters rsaOAEPParams) {
      return "http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(encryptionAlgorithmURI) && rsaOAEPParams != null ? rsaOAEPParams.getMaskGenerationFunction() : null;
   }

   @Nullable
   protected byte[] decodeOAEPParams(@Nullable String base64Params) throws EncryptionException {
      try {
         if (base64Params != null) {
            byte[] oaepParams = Base64Support.decode(base64Params);
            return oaepParams.length == 0 ? null : oaepParams;
         } else {
            return null;
         }
      } catch (RuntimeException var3) {
         throw new EncryptionException(String.format("Error decoding OAEPParams data '%s'", base64Params), var3);
      }
   }

   protected void postProcessApacheEncryptedKey(@Nonnull org.apache.xml.security.encryption.EncryptedKey apacheEncryptedKey, @Nonnull Key targetKey, @Nonnull Key encryptionKey, @Nonnull String encryptionAlgorithmURI, @Nonnull Document containingDocument) throws EncryptionException {
      if (AlgorithmSupport.isRSAOAEP(encryptionAlgorithmURI)) {
         EncryptionMethod apacheEncryptionMethod = apacheEncryptedKey.getEncryptionMethod();
         if (apacheEncryptionMethod.getDigestAlgorithm() == null) {
            apacheEncryptionMethod.setDigestAlgorithm("http://www.w3.org/2000/09/xmldsig#sha1");
         }

         if (!"http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(encryptionAlgorithmURI) && apacheEncryptionMethod.getMGFAlgorithm() == null) {
            apacheEncryptionMethod.setMGFAlgorithm("http://www.w3.org/2009/xmlenc11#mgf1sha1");
         }
      }

   }

   @Nonnull
   protected EncryptedData encryptElement(@Nonnull XMLObject xmlObject, @Nonnull Key encryptionKey, @Nonnull String encryptionAlgorithmURI, boolean encryptContentMode) throws EncryptionException {
      if (xmlObject == null) {
         this.log.error("XMLObject for encryption was null");
         throw new EncryptionException("XMLObject cannot be null");
      } else if (encryptionKey == null) {
         this.log.error("Encryption key for key encryption was null");
         throw new EncryptionException("Encryption key cannot be null");
      } else {
         this.log.debug("Encrypting XMLObject using algorithm URI {} with content mode {}", encryptionAlgorithmURI, encryptContentMode);
         this.checkAndMarshall(xmlObject);
         Element targetElement = xmlObject.getDOM();
         Document ownerDocument = targetElement.getOwnerDocument();

         XMLCipher xmlCipher;
         try {
            if (this.getJCAProviderName() != null) {
               xmlCipher = XMLCipher.getProviderInstance(encryptionAlgorithmURI, this.getJCAProviderName());
            } else {
               xmlCipher = XMLCipher.getInstance(encryptionAlgorithmURI);
            }

            xmlCipher.init(1, encryptionKey);
         } catch (XMLEncryptionException var12) {
            this.log.error("Error initializing cipher instance on XMLObject encryption", var12);
            throw new EncryptionException("Error initializing cipher instance", var12);
         }

         org.apache.xml.security.encryption.EncryptedData apacheEncryptedData;
         try {
            apacheEncryptedData = xmlCipher.encryptData(ownerDocument, targetElement, encryptContentMode);
         } catch (Exception var11) {
            this.log.error("Error encrypting XMLObject", var11);
            throw new EncryptionException("Error encrypting XMLObject", var11);
         }

         try {
            Element encDataElement = xmlCipher.martial(ownerDocument, apacheEncryptedData);
            return (EncryptedData)this.encryptedDataUnmarshaller.unmarshall(encDataElement);
         } catch (UnmarshallingException var10) {
            this.log.error("Error unmarshalling EncryptedData element", var10);
            throw new EncryptionException("Error unmarshalling EncryptedData element", var10);
         }
      }
   }

   @Nonnull
   private EncryptedData encryptElement(@Nonnull XMLObject xmlObject, @Nonnull DataEncryptionParameters encParams, @Nonnull List kekParamsList, boolean encryptContentMode) throws EncryptionException {
      this.checkParams(encParams, kekParamsList);
      String encryptionAlgorithmURI = encParams.getAlgorithm();
      Key encryptionKey = CredentialSupport.extractEncryptionKey(encParams.getEncryptionCredential());
      if (encryptionKey == null) {
         encryptionKey = this.generateEncryptionKey(encryptionAlgorithmURI);
      }

      EncryptedData encryptedData = this.encryptElement(xmlObject, (Key)encryptionKey, (String)encryptionAlgorithmURI, encryptContentMode);
      Document ownerDocument = encryptedData.getDOM().getOwnerDocument();
      if (encParams.getKeyInfoGenerator() != null) {
         KeyInfoGenerator generator = encParams.getKeyInfoGenerator();
         this.log.debug("Dynamically generating KeyInfo from Credential for EncryptedData using generator: {}", generator.getClass().getName());

         try {
            encryptedData.setKeyInfo(generator.generate(encParams.getEncryptionCredential()));
         } catch (SecurityException var13) {
            this.log.error("Error during EncryptedData KeyInfo generation", var13);
            throw new EncryptionException("Error during EncryptedData KeyInfo generation", var13);
         }
      }

      EncryptedKey encryptedKey;
      for(Iterator var14 = kekParamsList.iterator(); var14.hasNext(); encryptedData.getKeyInfo().getEncryptedKeys().add(encryptedKey)) {
         KeyEncryptionParameters kekParams = (KeyEncryptionParameters)var14.next();
         encryptedKey = this.encryptKey((Key)encryptionKey, (KeyEncryptionParameters)kekParams, ownerDocument);
         if (encryptedData.getKeyInfo() == null) {
            KeyInfo keyInfo = (KeyInfo)this.keyInfoBuilder.buildObject();
            encryptedData.setKeyInfo(keyInfo);
         }
      }

      return encryptedData;
   }

   protected void checkAndMarshall(@Nonnull XMLObject xmlObject) throws EncryptionException {
      Element targetElement = xmlObject.getDOM();
      if (targetElement == null) {
         try {
            Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(xmlObject);
            if (marshaller == null) {
               throw new MarshallingException("No marshaller available for " + xmlObject.getElementQName());
            }

            marshaller.marshall(xmlObject);
         } catch (MarshallingException var4) {
            this.log.error("Error marshalling target XMLObject", var4);
            throw new EncryptionException("Error marshalling target XMLObject", var4);
         }
      }

   }

   protected void checkParams(@Nonnull DataEncryptionParameters encParams) throws EncryptionException {
      if (encParams == null) {
         this.log.error("Data encryption parameters are required");
         throw new EncryptionException("Data encryption parameters are required");
      } else if (Strings.isNullOrEmpty(encParams.getAlgorithm())) {
         this.log.error("Data encryption algorithm URI is required");
         throw new EncryptionException("Data encryption algorithm URI is required");
      }
   }

   protected void checkParams(@Nullable KeyEncryptionParameters kekParams, boolean allowEmpty) throws EncryptionException {
      if (kekParams == null) {
         if (!allowEmpty) {
            this.log.error("Key encryption parameters are required");
            throw new EncryptionException("Key encryption parameters are required");
         }
      } else {
         Key key = CredentialSupport.extractEncryptionKey(kekParams.getEncryptionCredential());
         if (key == null) {
            this.log.error("Key encryption credential and contained key are required");
            throw new EncryptionException("Key encryption credential and contained key are required");
         } else if (key instanceof DSAPublicKey) {
            this.log.error("Attempt made to use DSA key for encrypted key transport");
            throw new EncryptionException("DSA keys may not be used for encrypted key transport");
         } else if (key instanceof ECPublicKey) {
            this.log.error("Attempt made to use EC key for encrypted key transport");
            throw new EncryptionException("EC keys may not be used for encrypted key transport");
         } else if (Strings.isNullOrEmpty(kekParams.getAlgorithm())) {
            this.log.error("Key encryption algorithm URI is required");
            throw new EncryptionException("Key encryption algorithm URI is required");
         }
      }
   }

   protected void checkParams(@Nullable List kekParamsList, boolean allowEmpty) throws EncryptionException {
      if (kekParamsList != null && !kekParamsList.isEmpty()) {
         Iterator var3 = kekParamsList.iterator();

         while(var3.hasNext()) {
            KeyEncryptionParameters kekParams = (KeyEncryptionParameters)var3.next();
            this.checkParams(kekParams, false);
         }

      } else if (!allowEmpty) {
         this.log.error("Key encryption parameters list may not be empty");
         throw new EncryptionException("Key encryption parameters list may not be empty");
      }
   }

   protected void checkParams(@Nonnull DataEncryptionParameters encParams, @Nullable List kekParamsList) throws EncryptionException {
      this.checkParams(encParams);
      this.checkParams(kekParamsList, true);
      if (CredentialSupport.extractEncryptionKey(encParams.getEncryptionCredential()) == null && (kekParamsList == null || kekParamsList.isEmpty())) {
         this.log.error("Using a generated encryption key requires a KeyEncryptionParameters object and key encryption key");
         throw new EncryptionException("Using a generated encryption key requires a KeyEncryptionParameters object and key encryption key");
      }
   }

   @Nonnull
   protected SecretKey generateEncryptionKey(@Nonnull String encryptionAlgorithmURI) throws EncryptionException {
      try {
         this.log.debug("Generating random symmetric data encryption key from algorithm URI: {}", encryptionAlgorithmURI);
         return AlgorithmSupport.generateSymmetricKey(encryptionAlgorithmURI);
      } catch (NoSuchAlgorithmException var3) {
         this.log.error("Could not generate encryption key, algorithm URI was invalid: " + encryptionAlgorithmURI);
         throw new EncryptionException("Could not generate encryption key, algorithm URI was invalid: " + encryptionAlgorithmURI);
      } catch (KeyException var4) {
         this.log.error("Could not generate encryption key from algorithm URI: " + encryptionAlgorithmURI);
         throw new EncryptionException("Could not generate encryption key from algorithm URI: " + encryptionAlgorithmURI);
      }
   }

   static {
      if (!Init.isInitialized()) {
         Init.init();
      }

   }
}
