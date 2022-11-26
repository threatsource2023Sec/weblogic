package org.opensaml.xmlsec.keyinfo.impl.provider;

import java.security.KeyException;
import java.security.PublicKey;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.CredentialContext;
import org.opensaml.security.criteria.KeyAlgorithmCriterion;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoResolutionContext;
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.RSAKeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAKeyValueProvider extends AbstractKeyInfoProvider {
   private final Logger log = LoggerFactory.getLogger(RSAKeyValueProvider.class);

   public boolean handles(@Nonnull XMLObject keyInfoChild) {
      return this.getRSAKeyValue(keyInfoChild) != null;
   }

   @Nullable
   public Collection process(@Nonnull KeyInfoCredentialResolver resolver, @Nonnull XMLObject keyInfoChild, @Nullable CriteriaSet criteriaSet, @Nonnull KeyInfoResolutionContext kiContext) throws SecurityException {
      RSAKeyValue keyValue = this.getRSAKeyValue(keyInfoChild);
      if (keyValue == null) {
         return null;
      } else {
         KeyAlgorithmCriterion algorithmCriteria;
         if (criteriaSet != null) {
            algorithmCriteria = (KeyAlgorithmCriterion)criteriaSet.get(KeyAlgorithmCriterion.class);
            if (algorithmCriteria != null && algorithmCriteria.getKeyAlgorithm() != null && !"RSA".equals(algorithmCriteria.getKeyAlgorithm())) {
               this.log.debug("Criterion specified non-RSA key algorithm, skipping");
               return null;
            }
         }

         this.log.debug("Attempting to extract credential from an RSAKeyValue");
         algorithmCriteria = null;

         PublicKey pubKey;
         try {
            pubKey = KeyInfoSupport.getRSAKey(keyValue);
         } catch (KeyException var10) {
            this.log.error("Error extracting RSA key value", var10);
            throw new SecurityException("Error extracting RSA key value", var10);
         }

         BasicCredential cred = new BasicCredential(pubKey);
         cred.getKeyNames().addAll(kiContext.getKeyNames());
         CredentialContext credContext = this.buildCredentialContext(kiContext);
         if (credContext != null) {
            cred.getCredentialContextSet().add(credContext);
         }

         this.log.debug("Credential successfully extracted from RSAKeyValue");
         LazySet credentialSet = new LazySet();
         credentialSet.add(cred);
         return credentialSet;
      }
   }

   @Nullable
   protected RSAKeyValue getRSAKeyValue(@Nonnull XMLObject xmlObject) {
      if (xmlObject instanceof RSAKeyValue) {
         return (RSAKeyValue)xmlObject;
      } else {
         return xmlObject instanceof KeyValue ? ((KeyValue)xmlObject).getRSAKeyValue() : null;
      }
   }
}
