package org.opensaml.xmlsec.keyinfo.impl.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCriterion;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoResolutionContext;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyInfoReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyInfoReferenceProvider extends AbstractKeyInfoProvider {
   private final Logger log = LoggerFactory.getLogger(KeyInfoReferenceProvider.class);

   public boolean handles(@Nonnull XMLObject keyInfoChild) {
      return this.getKeyInfoReference(keyInfoChild) != null;
   }

   @Nullable
   public Collection process(@Nonnull KeyInfoCredentialResolver resolver, @Nonnull XMLObject keyInfoChild, @Nullable CriteriaSet criteriaSet, @Nonnull KeyInfoResolutionContext kiContext) throws SecurityException {
      KeyInfoReference ref = this.getKeyInfoReference(keyInfoChild);
      if (ref == null) {
         return null;
      } else {
         this.log.debug("Attempting to follow same-document KeyInfoReference");
         XMLObject target = ref.resolveIDFromRoot(ref.getURI().substring(1));
         if (target == null) {
            this.log.warn("KeyInfoReference URI could not be dereferenced");
            return null;
         } else if (!(target instanceof KeyInfo)) {
            this.log.warn("The product of dereferencing the KeyInfoReference was not a KeyInfo");
            return null;
         } else if (!((KeyInfo)target).getKeyInfoReferences().isEmpty()) {
            this.log.warn("The dereferenced KeyInfo contained a KeyInfoReference, cannot process");
            return null;
         } else {
            this.log.debug("Recursively processing KeyInfoReference referent");
            CriteriaSet newCriteria = new CriteriaSet();
            newCriteria.add(new KeyInfoCriterion((KeyInfo)target));
            Iterator var8 = criteriaSet.iterator();

            while(var8.hasNext()) {
               Criterion crit = (Criterion)var8.next();
               if (!(crit instanceof KeyInfoCriterion)) {
                  newCriteria.add(crit);
               }
            }

            try {
               Iterable creds = resolver.resolve(newCriteria);
               if (creds != null) {
                  Collection result = new ArrayList();
                  Iterator var10 = creds.iterator();

                  while(var10.hasNext()) {
                     Credential c = (Credential)var10.next();
                     result.add(c);
                  }

                  return result;
               }
            } catch (ResolverException var12) {
               this.log.error("Exception while resolving credentials from KeyInfoReference referent", var12);
            }

            return null;
         }
      }
   }

   @Nullable
   protected KeyInfoReference getKeyInfoReference(@Nonnull XMLObject xmlObject) {
      if (xmlObject instanceof KeyInfoReference) {
         KeyInfoReference ref = (KeyInfoReference)xmlObject;
         String uri = ref.getURI();
         if (uri != null && uri.startsWith("#")) {
            return ref;
         }

         this.log.debug("KeyInfoReference did not contain a same-document URI reference, cannot handle");
      }

      return null;
   }
}
