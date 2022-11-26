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
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DEREncodedKeyValueProvider extends AbstractKeyInfoProvider {
   private final Logger log = LoggerFactory.getLogger(DEREncodedKeyValueProvider.class);

   public boolean handles(@Nonnull XMLObject keyInfoChild) {
      return this.getDEREncodedKeyValue(keyInfoChild) != null;
   }

   @Nullable
   public Collection process(@Nonnull KeyInfoCredentialResolver resolver, @Nonnull XMLObject keyInfoChild, @Nullable CriteriaSet criteriaSet, @Nonnull KeyInfoResolutionContext kiContext) throws SecurityException {
      DEREncodedKeyValue keyValue = this.getDEREncodedKeyValue(keyInfoChild);
      if (keyValue == null) {
         return null;
      } else {
         this.log.debug("Attempting to extract credential from a DEREncodedKeyValue");
         PublicKey pubKey = null;

         try {
            pubKey = KeyInfoSupport.getKey(keyValue);
         } catch (KeyException var11) {
            this.log.error("Error extracting DER-encoded key value", var11);
            throw new SecurityException("Error extracting DER-encoded key value", var11);
         }

         KeyAlgorithmCriterion algorithmCriteria = (KeyAlgorithmCriterion)criteriaSet.get(KeyAlgorithmCriterion.class);
         if (algorithmCriteria != null && algorithmCriteria.getKeyAlgorithm() != null && !algorithmCriteria.getKeyAlgorithm().equals(pubKey.getAlgorithm())) {
            this.log.debug("Criteria specified key algorithm {}, actually {}, skipping", algorithmCriteria.getKeyAlgorithm(), pubKey.getAlgorithm());
            return null;
         } else {
            BasicCredential cred = new BasicCredential(pubKey);
            if (kiContext != null) {
               cred.getKeyNames().addAll(kiContext.getKeyNames());
            }

            CredentialContext credContext = this.buildCredentialContext(kiContext);
            if (credContext != null) {
               cred.getCredentialContextSet().add(credContext);
            }

            this.log.debug("Credential successfully extracted from DEREncodedKeyValue");
            LazySet credentialSet = new LazySet();
            credentialSet.add(cred);
            return credentialSet;
         }
      }
   }

   @Nullable
   protected DEREncodedKeyValue getDEREncodedKeyValue(@Nonnull XMLObject xmlObject) {
      return xmlObject instanceof DEREncodedKeyValue ? (DEREncodedKeyValue)xmlObject : null;
   }
}
