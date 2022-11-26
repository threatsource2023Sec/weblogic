package org.opensaml.xmlsec.signature.support.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingSignatureTrustEngine implements SignatureTrustEngine {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ChainingSignatureTrustEngine.class);
   @Nonnull
   @NonnullElements
   private List engines;

   public ChainingSignatureTrustEngine(@Nonnull @ParameterName(name = "chain") @NonnullElements List chain) {
      Constraint.isNotNull(chain, "SignatureTrustEngine list cannot be null");
      this.engines = new ArrayList(Collections2.filter(chain, Predicates.notNull()));
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getChain() {
      return ImmutableList.copyOf(this.engines);
   }

   @Nullable
   public KeyInfoCredentialResolver getKeyInfoResolver() {
      return null;
   }

   public boolean validate(@Nonnull Signature token, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
      Iterator var3 = this.engines.iterator();

      SignatureTrustEngine engine;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         engine = (SignatureTrustEngine)var3.next();
      } while(!engine.validate(token, trustBasisCriteria));

      this.log.debug("Signature was trusted by chain member: {}", engine.getClass().getName());
      return true;
   }

   public boolean validate(@Nonnull byte[] signature, @Nonnull byte[] content, @Nonnull String algorithmURI, @Nullable CriteriaSet trustBasisCriteria, @Nonnull Credential candidateCredential) throws SecurityException {
      Iterator var6 = this.engines.iterator();

      SignatureTrustEngine engine;
      do {
         if (!var6.hasNext()) {
            return false;
         }

         engine = (SignatureTrustEngine)var6.next();
      } while(!engine.validate(signature, content, algorithmURI, trustBasisCriteria, candidateCredential));

      this.log.debug("Signature was trusted by chain member: {}", engine.getClass().getName());
      return true;
   }
}
