package org.opensaml.saml.saml2.encryption;

import com.google.common.base.Strings;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;
import net.shibboleth.utilities.java.support.security.RandomIdentifierGenerationStrategy;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.EncryptedAttribute;
import org.opensaml.saml.saml2.core.EncryptedElementType;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NewEncryptedID;
import org.opensaml.saml.saml2.core.NewID;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.encryption.CarriedKeyName;
import org.opensaml.xmlsec.encryption.DataReference;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.ReferenceList;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.support.DataEncryptionParameters;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.opensaml.xmlsec.encryption.support.KeyEncryptionParameters;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyName;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Encrypter extends org.opensaml.xmlsec.encryption.support.Encrypter {
   private XMLObjectBuilderFactory builderFactory;
   private XMLSignatureBuilder keyInfoBuilder;
   private XMLEncryptionBuilder dataReferenceBuilder;
   private XMLEncryptionBuilder referenceListBuilder;
   private XMLSignatureBuilder retrievalMethodBuilder;
   private XMLSignatureBuilder keyNameBuilder;
   private XMLEncryptionBuilder carriedKeyNameBuilder;
   private IdentifierGenerationStrategy idGenerator;
   private DataEncryptionParameters encParams;
   private List kekParamsList;
   private KeyPlacement keyPlacement;
   private final Logger log = LoggerFactory.getLogger(Encrypter.class);

   public Encrypter(DataEncryptionParameters dataEncParams, List keyEncParams) {
      this.encParams = dataEncParams;
      this.kekParamsList = keyEncParams;
      this.init();
   }

   public Encrypter(DataEncryptionParameters dataEncParams, KeyEncryptionParameters keyEncParam) {
      List keks = new ArrayList();
      keks.add(keyEncParam);
      this.encParams = dataEncParams;
      this.kekParamsList = keks;
      this.init();
   }

   public Encrypter(DataEncryptionParameters dataEncParams) {
      List keks = new ArrayList();
      this.encParams = dataEncParams;
      this.kekParamsList = keks;
      this.init();
   }

   private void init() {
      this.builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
      this.keyInfoBuilder = (XMLSignatureBuilder)this.builderFactory.getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME);
      this.dataReferenceBuilder = (XMLEncryptionBuilder)this.builderFactory.getBuilder(DataReference.DEFAULT_ELEMENT_NAME);
      this.referenceListBuilder = (XMLEncryptionBuilder)this.builderFactory.getBuilder(ReferenceList.DEFAULT_ELEMENT_NAME);
      this.retrievalMethodBuilder = (XMLSignatureBuilder)this.builderFactory.getBuilder(RetrievalMethod.DEFAULT_ELEMENT_NAME);
      this.keyNameBuilder = (XMLSignatureBuilder)this.builderFactory.getBuilder(KeyName.DEFAULT_ELEMENT_NAME);
      this.carriedKeyNameBuilder = (XMLEncryptionBuilder)this.builderFactory.getBuilder(CarriedKeyName.DEFAULT_ELEMENT_NAME);
      this.idGenerator = new RandomIdentifierGenerationStrategy();
      this.keyPlacement = Encrypter.KeyPlacement.PEER;
   }

   public void setIDGenerator(IdentifierGenerationStrategy newIDGenerator) {
      this.idGenerator = newIDGenerator;
   }

   public KeyPlacement getKeyPlacement() {
      return this.keyPlacement;
   }

   public void setKeyPlacement(KeyPlacement newKeyPlacement) {
      this.keyPlacement = newKeyPlacement;
   }

   public EncryptedAssertion encrypt(Assertion assertion) throws EncryptionException {
      this.logPreEncryption(assertion, "Assertion");
      return (EncryptedAssertion)this.encrypt(assertion, EncryptedAssertion.DEFAULT_ELEMENT_NAME);
   }

   public EncryptedID encryptAsID(Assertion assertion) throws EncryptionException {
      this.logPreEncryption(assertion, "Assertion (as EncryptedID)");
      return (EncryptedID)this.encrypt(assertion, EncryptedID.DEFAULT_ELEMENT_NAME);
   }

   public EncryptedAttribute encrypt(Attribute attribute) throws EncryptionException {
      this.logPreEncryption(attribute, "Attribute");
      return (EncryptedAttribute)this.encrypt(attribute, EncryptedAttribute.DEFAULT_ELEMENT_NAME);
   }

   public EncryptedID encrypt(NameID nameID) throws EncryptionException {
      this.logPreEncryption(nameID, "NameID");
      return (EncryptedID)this.encrypt(nameID, EncryptedID.DEFAULT_ELEMENT_NAME);
   }

   public EncryptedID encrypt(BaseID baseID) throws EncryptionException {
      this.logPreEncryption(baseID, "BaseID");
      return (EncryptedID)this.encrypt(baseID, EncryptedID.DEFAULT_ELEMENT_NAME);
   }

   public NewEncryptedID encrypt(NewID newID) throws EncryptionException {
      this.logPreEncryption(newID, "NewID");
      return (NewEncryptedID)this.encrypt(newID, NewEncryptedID.DEFAULT_ELEMENT_NAME);
   }

   private void logPreEncryption(XMLObject xmlObject, String objectType) {
      if (this.log.isDebugEnabled()) {
         try {
            Element dom = XMLObjectSupport.marshall(xmlObject);
            this.log.debug("{} before encryption:\n{}", objectType, SerializeSupport.prettyPrintXML(dom));
         } catch (MarshallingException var4) {
            this.log.error("Unable to marshall {} for logging purposes", objectType, var4);
         }
      }

   }

   private EncryptedElementType encrypt(XMLObject xmlObject, QName encElementName) throws EncryptionException {
      this.checkParams(this.encParams, this.kekParamsList);
      EncryptedElementType encElement = (EncryptedElementType)this.builderFactory.getBuilder(encElementName).buildObject(encElementName);
      this.checkAndMarshall(encElement);
      Document ownerDocument = encElement.getDOM().getOwnerDocument();
      String encryptionAlgorithmURI = this.encParams.getAlgorithm();
      Key encryptionKey = CredentialSupport.extractEncryptionKey(this.encParams.getEncryptionCredential());
      if (encryptionKey == null) {
         encryptionKey = this.generateEncryptionKey(encryptionAlgorithmURI);
      }

      EncryptedData encryptedData = this.encryptElement(xmlObject, (Key)encryptionKey, encryptionAlgorithmURI, false);
      if (this.encParams.getKeyInfoGenerator() != null) {
         KeyInfoGenerator generator = this.encParams.getKeyInfoGenerator();
         this.log.debug("Dynamically generating KeyInfo from Credential for EncryptedData using generator: {}", generator.getClass().getName());

         try {
            encryptedData.setKeyInfo(generator.generate(this.encParams.getEncryptionCredential()));
         } catch (SecurityException var10) {
            throw new EncryptionException("Error generating EncryptedData KeyInfo", var10);
         }
      }

      List encryptedKeys = new ArrayList();
      if (this.kekParamsList != null && !this.kekParamsList.isEmpty()) {
         encryptedKeys.addAll(this.encryptKey((Key)encryptionKey, this.kekParamsList, ownerDocument));
      }

      return this.processElements(encElement, encryptedData, encryptedKeys);
   }

   protected EncryptedElementType processElements(EncryptedElementType encElement, EncryptedData encData, List encKeys) throws EncryptionException {
      if (encData.getID() == null) {
         encData.setID(this.idGenerator.generateIdentifier());
      }

      if (encKeys.isEmpty()) {
         encElement.setEncryptedData(encData);
         return encElement;
      } else {
         if (encData.getKeyInfo() == null) {
            encData.setKeyInfo((KeyInfo)this.keyInfoBuilder.buildObject());
         }

         Iterator var4 = encKeys.iterator();

         while(var4.hasNext()) {
            EncryptedKey encKey = (EncryptedKey)var4.next();
            if (encKey.getID() == null) {
               encKey.setID(this.idGenerator.generateIdentifier());
            }
         }

         switch (this.keyPlacement) {
            case INLINE:
               return this.placeKeysInline(encElement, encData, encKeys);
            case PEER:
               return this.placeKeysAsPeers(encElement, encData, encKeys);
            default:
               throw new EncryptionException("Unsupported key placement option was specified: " + this.keyPlacement);
         }
      }
   }

   protected EncryptedElementType placeKeysInline(EncryptedElementType encElement, EncryptedData encData, List encKeys) {
      this.log.debug("Placing EncryptedKey elements inline inside EncryptedData");
      encData.getKeyInfo().getEncryptedKeys().addAll(encKeys);
      encElement.setEncryptedData(encData);
      return encElement;
   }

   protected EncryptedElementType placeKeysAsPeers(EncryptedElementType encElement, EncryptedData encData, List encKeys) {
      this.log.debug("Placing EncryptedKey elements as peers of EncryptedData in EncryptedElementType");
      Iterator var4 = encKeys.iterator();

      while(var4.hasNext()) {
         EncryptedKey encKey = (EncryptedKey)var4.next();
         if (encKey.getReferenceList() == null) {
            encKey.setReferenceList((ReferenceList)this.referenceListBuilder.buildObject());
         }
      }

      if (encKeys.size() == 1) {
         this.linkSinglePeerKey(encData, (EncryptedKey)encKeys.get(0));
      } else if (encKeys.size() > 1) {
         this.linkMultiplePeerKeys(encData, encKeys);
      }

      encElement.setEncryptedData(encData);
      encElement.getEncryptedKeys().addAll(encKeys);
      return encElement;
   }

   protected void linkSinglePeerKey(EncryptedData encData, EncryptedKey encKey) {
      this.log.debug("Linking single peer EncryptedKey with RetrievalMethod and DataReference");
      RetrievalMethod rm = (RetrievalMethod)this.retrievalMethodBuilder.buildObject();
      rm.setURI("#" + encKey.getID());
      rm.setType("http://www.w3.org/2001/04/xmlenc#EncryptedKey");
      encData.getKeyInfo().getRetrievalMethods().add(rm);
      DataReference dr = (DataReference)this.dataReferenceBuilder.buildObject();
      dr.setURI("#" + encData.getID());
      encKey.getReferenceList().getDataReferences().add(dr);
   }

   protected void linkMultiplePeerKeys(EncryptedData encData, List encKeys) {
      this.log.debug("Linking multiple peer EncryptedKeys with CarriedKeyName and DataReference");
      List dataEncKeyNames = encData.getKeyInfo().getKeyNames();
      String carriedKeyNameValue;
      if (dataEncKeyNames.size() != 0 && !Strings.isNullOrEmpty(((KeyName)dataEncKeyNames.get(0)).getValue())) {
         carriedKeyNameValue = ((KeyName)dataEncKeyNames.get(0)).getValue();
      } else {
         String keyNameValue = this.idGenerator.generateIdentifier();
         this.log.debug("EncryptedData encryption key had no KeyName, generated one for use in CarriedKeyName: {}", keyNameValue);
         KeyName keyName = (KeyName)dataEncKeyNames.get(0);
         if (keyName == null) {
            keyName = (KeyName)this.keyNameBuilder.buildObject();
            dataEncKeyNames.add(keyName);
         }

         keyName.setValue(keyNameValue);
         carriedKeyNameValue = keyNameValue;
      }

      Iterator var8 = encKeys.iterator();

      while(var8.hasNext()) {
         EncryptedKey encKey = (EncryptedKey)var8.next();
         if (encKey.getCarriedKeyName() == null) {
            encKey.setCarriedKeyName((CarriedKeyName)this.carriedKeyNameBuilder.buildObject());
         }

         encKey.getCarriedKeyName().setValue(carriedKeyNameValue);
         DataReference dr = (DataReference)this.dataReferenceBuilder.buildObject();
         dr.setURI("#" + encData.getID());
         encKey.getReferenceList().getDataReferences().add(dr);
      }

   }

   public static enum KeyPlacement {
      PEER,
      INLINE;
   }
}
