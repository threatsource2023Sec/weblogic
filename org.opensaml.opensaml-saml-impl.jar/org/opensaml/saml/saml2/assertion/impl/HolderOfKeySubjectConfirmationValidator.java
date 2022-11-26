package org.opensaml.saml.saml2.assertion.impl;

import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.collection.Pair;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.KeyInfoConfirmationDataType;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.X509Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public class HolderOfKeySubjectConfirmationValidator extends AbstractSubjectConfirmationValidator {
   private Logger log = LoggerFactory.getLogger(HolderOfKeySubjectConfirmationValidator.class);

   @Nonnull
   public String getServicedMethod() {
      return "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
   }

   @Nonnull
   protected ValidationResult doValidate(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
      if (!Objects.equals(confirmation.getMethod(), "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key")) {
         return ValidationResult.INDETERMINATE;
      } else {
         this.log.debug("Attempting holder-of-key subject confirmation");
         if (!this.isValidConfirmationDataType(confirmation)) {
            String msg = String.format("Subject confirmation data is not of type '%s'", KeyInfoConfirmationDataType.TYPE_NAME);
            this.log.debug(msg);
            context.setValidationFailureMessage(msg);
            return ValidationResult.INVALID;
         } else {
            List possibleKeys = this.getSubjectConfirmationKeyInformation(confirmation, assertion, context);
            if (possibleKeys.isEmpty()) {
               String msg = String.format("No key information for holder of key subject confirmation in assertion '%s'", assertion.getID());
               this.log.debug(msg);
               context.setValidationFailureMessage(msg);
               return ValidationResult.INVALID;
            } else {
               Pair keyCertPair = null;

               try {
                  keyCertPair = this.getKeyAndCertificate(context);
               } catch (IllegalArgumentException var8) {
                  this.log.warn("Problem with the validation context presenter key/cert params: {}", var8.getMessage());
                  context.setValidationFailureMessage("Unable to obtain presenter key/cert params from validation context");
                  return ValidationResult.INDETERMINATE;
               }

               if (keyCertPair.getFirst() == null && keyCertPair.getSecond() == null) {
                  this.log.debug("Neither the presenter's certificate nor its public key were provided");
                  context.setValidationFailureMessage("Neither the presenter's certificate nor its public key were provided");
                  return ValidationResult.INDETERMINATE;
               } else {
                  Iterator var6 = possibleKeys.iterator();

                  KeyInfo keyInfo;
                  do {
                     if (!var6.hasNext()) {
                        return ValidationResult.INVALID;
                     }

                     keyInfo = (KeyInfo)var6.next();
                     if (this.matchesKeyValue((PublicKey)keyCertPair.getFirst(), keyInfo)) {
                        this.log.debug("Successfully matched public key in subject confirmation data to supplied key param");
                        context.getDynamicParameters().put("saml2.SubjectConfirmation.HoK.ConfirmedKeyInfo", keyInfo);
                        return ValidationResult.VALID;
                     }
                  } while(!this.matchesX509Certificate((X509Certificate)keyCertPair.getSecond(), keyInfo));

                  this.log.debug("Successfully matched certificate in subject confirmation data to supplied cert param");
                  context.getDynamicParameters().put("saml2.SubjectConfirmation.HoK.ConfirmedKeyInfo", keyInfo);
                  return ValidationResult.VALID;
               }
            }
         }
      }
   }

   protected boolean isValidConfirmationDataType(@Nonnull SubjectConfirmation confirmation) throws AssertionValidationException {
      QName confirmationDataSchemaType = confirmation.getSubjectConfirmationData().getSchemaType();
      if (confirmationDataSchemaType != null && !confirmationDataSchemaType.equals(KeyInfoConfirmationDataType.TYPE_NAME)) {
         this.log.debug("SubjectConfirmationData xsi:type was non-null and did not match {}", KeyInfoConfirmationDataType.TYPE_NAME);
         return false;
      } else {
         this.log.debug("SubjectConfirmationData xsi:type was either null or matched {}", KeyInfoConfirmationDataType.TYPE_NAME);
         return true;
      }
   }

   @Nonnull
   protected Pair getKeyAndCertificate(@Nonnull ValidationContext context) throws AssertionValidationException {
      PublicKey presenterKey = null;

      try {
         presenterKey = (PublicKey)context.getStaticParameters().get("saml2.SubjectConfirmation.HoK.PresenterKey");
      } catch (ClassCastException var5) {
         throw new IllegalArgumentException(String.format("The value of the static validation parameter '%s' was not of the required type '%s'", "saml2.SubjectConfirmation.HoK.PresenterKey", PublicKey.class.getName()));
      }

      X509Certificate presenterCert = null;

      try {
         presenterCert = (X509Certificate)context.getStaticParameters().get("saml2.SubjectConfirmation.HoK.PresenterCertificate");
         if (presenterCert != null) {
            if (presenterKey != null) {
               if (!presenterKey.equals(presenterCert.getPublicKey())) {
                  throw new IllegalArgumentException("Presenter's certificate contains a different public key than the one explicitly given");
               }
            } else {
               presenterKey = presenterCert.getPublicKey();
            }
         }
      } catch (ClassCastException var6) {
         throw new IllegalArgumentException(String.format("The value of the static validation parameter '%s' was not of the required type '%s'", "saml2.SubjectConfirmation.HoK.PresenterCertificate", X509Certificate.class.getName()));
      }

      return new Pair(presenterKey, presenterCert);
   }

   @Nonnull
   protected List getSubjectConfirmationKeyInformation(@Nonnull SubjectConfirmation confirmation, @Nonnull Assertion assertion, @Nonnull ValidationContext context) throws AssertionValidationException {
      SubjectConfirmationData confirmationData = confirmation.getSubjectConfirmationData();
      List keyInfos = new LazyList();
      Iterator var6 = confirmationData.getUnknownXMLObjects(KeyInfo.DEFAULT_ELEMENT_NAME).iterator();

      while(var6.hasNext()) {
         XMLObject object = (XMLObject)var6.next();
         if (object != null) {
            keyInfos.add((KeyInfo)object);
         }
      }

      this.log.debug("Found '{}' KeyInfo children of SubjectConfirmationData", keyInfos.size());
      return keyInfos;
   }

   protected boolean matchesKeyValue(@Nullable PublicKey key, @Nonnull KeyInfo keyInfo) throws AssertionValidationException {
      if (key == null) {
         this.log.debug("Presenter PublicKey was null, skipping KeyValue match");
         return false;
      } else if (this.matchesKeyValue(key, keyInfo.getKeyValues())) {
         return true;
      } else if (this.matchesDEREncodedKeyValue(key, keyInfo.getDEREncodedKeyValues())) {
         return true;
      } else {
         this.log.debug("Failed to match either a KeyInfo KeyValue or DEREncodedKeyValue against supplied PublicKey param");
         return false;
      }
   }

   protected boolean matchesKeyValue(@Nonnull PublicKey key, @Nullable List keyValues) {
      if (keyValues != null && !keyValues.isEmpty()) {
         this.log.debug("Attempting to match KeyInfo KeyValue to supplied PublicKey param of type: {}", key.getAlgorithm());
         Iterator var3 = keyValues.iterator();

         while(var3.hasNext()) {
            KeyValue keyValue = (KeyValue)var3.next();

            try {
               PublicKey kiPublicKey = KeyInfoSupport.getKey(keyValue);
               if (Objects.equals(key, kiPublicKey)) {
                  this.log.debug("Matched KeyValue PublicKey");
                  return true;
               }
            } catch (KeyException var6) {
               this.log.warn("KeyInfo contained KeyValue that can not be parsed", var6);
            }
         }

         this.log.debug("Failed to match any KeyValue");
         return false;
      } else {
         this.log.debug("KeyInfo contained no KeyValue children");
         return false;
      }
   }

   protected boolean matchesDEREncodedKeyValue(@Nonnull PublicKey key, @Nullable List derEncodedKeyValues) {
      if (derEncodedKeyValues != null && !derEncodedKeyValues.isEmpty()) {
         this.log.debug("Attempting to match KeyInfo DEREncodedKeyValue to supplied PublicKey param of type: {}", key.getAlgorithm());
         Iterator var3 = derEncodedKeyValues.iterator();

         while(var3.hasNext()) {
            DEREncodedKeyValue derEncodedKeyValue = (DEREncodedKeyValue)var3.next();

            try {
               PublicKey kiPublicKey = KeyInfoSupport.getKey(derEncodedKeyValue);
               if (Objects.equals(key, kiPublicKey)) {
                  this.log.debug("Matched DEREncodedKeyValue PublicKey");
                  return true;
               }
            } catch (KeyException var6) {
               this.log.warn("KeyInfo contained DEREncodedKeyValue that can not be parsed", var6);
            }
         }

         this.log.debug("Failed to match any DEREncodedKeyValue");
         return false;
      } else {
         this.log.debug("KeyInfo contained no DEREncodedKeyValue children");
         return false;
      }
   }

   protected boolean matchesX509Certificate(@Nullable X509Certificate cert, @Nonnull KeyInfo keyInfo) throws AssertionValidationException {
      if (cert == null) {
         this.log.debug("Presenter X509Certificate was null, skipping certificate match");
         return false;
      } else {
         List x509Datas = keyInfo.getX509Datas();
         if (x509Datas != null && !x509Datas.isEmpty()) {
            this.log.debug("Attempting to match KeyInfo X509Data to supplied X509Certificate param");
            Iterator var5 = x509Datas.iterator();

            while(true) {
               while(var5.hasNext()) {
                  X509Data data = (X509Data)var5.next();
                  List xmlCertificates = data.getX509Certificates();
                  if (xmlCertificates != null && !xmlCertificates.isEmpty()) {
                     Iterator var7 = xmlCertificates.iterator();

                     while(var7.hasNext()) {
                        org.opensaml.xmlsec.signature.X509Certificate xmlCertificate = (org.opensaml.xmlsec.signature.X509Certificate)var7.next();

                        try {
                           X509Certificate kiCert = KeyInfoSupport.getCertificate(xmlCertificate);
                           if (Objects.equals(cert, kiCert)) {
                              this.log.debug("Matched X509Certificate");
                              return true;
                           }
                        } catch (CertificateException var10) {
                           this.log.warn("KeyInfo contained Certificate value that can not be parsed", var10);
                        }
                     }
                  } else {
                     this.log.debug("X509Data contained no X509Certificate children, skipping certificate match");
                  }
               }

               this.log.debug("Failed to match a KeyInfo X509Data against supplied X509Certificate param");
               return false;
            }
         } else {
            this.log.debug("KeyInfo contained no X509Data children, skipping certificate match");
            return false;
         }
      }
   }
}
