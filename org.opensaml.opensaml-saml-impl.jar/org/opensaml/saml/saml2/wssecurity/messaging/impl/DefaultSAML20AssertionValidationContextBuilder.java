package org.opensaml.saml.saml2.wssecurity.messaging.impl;

import com.google.common.base.Function;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.messaging.context.SAMLSelfEntityContext;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.messaging.ServletRequestX509CredentialAdapter;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSAML20AssertionValidationContextBuilder implements Function {
   private Logger log = LoggerFactory.getLogger(DefaultSAML20AssertionValidationContextBuilder.class);
   private Function signatureCriteriaSetFunction;
   private boolean signatureRequired = true;

   public boolean isSignatureRequired() {
      return this.signatureRequired;
   }

   public void setSignatureRequired(boolean flag) {
      this.signatureRequired = flag;
   }

   @Nullable
   public Function getSignatureCriteriaSetFunction() {
      return this.signatureCriteriaSetFunction;
   }

   public void setSignatureCriteriaSetFunction(@Nullable Function function) {
      this.signatureCriteriaSetFunction = function;
   }

   @Nullable
   public ValidationContext apply(@Nullable SAML20AssertionTokenValidationInput input) {
      return input == null ? null : new ValidationContext(this.buildStaticParameters(input));
   }

   @Nonnull
   protected Map buildStaticParameters(@Nonnull SAML20AssertionTokenValidationInput input) {
      HashMap staticParams = new HashMap();
      staticParams.put("saml2.SignatureRequired", new Boolean(this.isSignatureRequired()));
      staticParams.put("saml2.SignatureValidationCriteriaSet", this.getSignatureCriteriaSet(input));
      X509Certificate attesterCertificate = this.getAttesterCertificate(input);
      if (attesterCertificate != null) {
         staticParams.put("saml2.SubjectConfirmation.HoK.PresenterCertificate", attesterCertificate);
      }

      PublicKey attesterPublicKey = this.getAttesterPublicKey(input);
      if (attesterPublicKey != null) {
         staticParams.put("saml2.SubjectConfirmation.HoK.PresenterKey", attesterPublicKey);
      }

      staticParams.put("saml2.SubjectConfirmation.ValidRecipients", this.getValidRecipients(input));
      staticParams.put("saml2.SubjectConfirmation.ValidAddresses", this.getValidAddresses(input));
      staticParams.put("saml2.Conditions.ValidAudiences", this.getValidAudiences(input));
      this.log.trace("Built static parameters map: {}", staticParams);
      return staticParams;
   }

   @Nonnull
   protected CriteriaSet getSignatureCriteriaSet(@Nonnull SAML20AssertionTokenValidationInput input) {
      CriteriaSet criteriaSet = new CriteriaSet();
      if (this.getSignatureCriteriaSetFunction() != null) {
         CriteriaSet dynamicCriteria = (CriteriaSet)this.getSignatureCriteriaSetFunction().apply(new Pair());
         if (dynamicCriteria != null) {
            criteriaSet.addAll(dynamicCriteria);
         }
      }

      if (!criteriaSet.contains(EntityIdCriterion.class)) {
         String issuer = null;
         if (input.getAssertion().getIssuer() != null) {
            issuer = StringSupport.trimOrNull(input.getAssertion().getIssuer().getValue());
         }

         if (issuer != null) {
            this.log.debug("Adding internally-generated EntityIdCriterion with value of: {}", issuer);
            criteriaSet.add(new EntityIdCriterion(issuer));
         }
      }

      if (!criteriaSet.contains(UsageCriterion.class)) {
         this.log.debug("Adding internally-generated UsageCriterion with value of: {}", UsageType.SIGNING);
         criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
      }

      this.log.debug("Resolved Signature validation CriteriaSet: {}", criteriaSet);
      return criteriaSet;
   }

   @Nullable
   protected X509Certificate getAttesterCertificate(@Nonnull SAML20AssertionTokenValidationInput input) {
      try {
         X509Credential credential = new ServletRequestX509CredentialAdapter(input.getHttpServletRequest());
         return credential.getEntityCertificate();
      } catch (SecurityException var3) {
         this.log.warn("Peer TLS X.509 certificate was not present. Holder-of-key proof-of-possession via client TLS cert will not be possible");
         return null;
      }
   }

   @Nullable
   protected PublicKey getAttesterPublicKey(@Nonnull SAML20AssertionTokenValidationInput input) {
      return null;
   }

   @Nonnull
   protected Set getValidRecipients(@Nonnull SAML20AssertionTokenValidationInput input) {
      LazySet validRecipients = new LazySet();
      String endpoint = input.getHttpServletRequest().getRequestURL().toString();
      validRecipients.add(endpoint);
      SAMLSelfEntityContext selfContext = (SAMLSelfEntityContext)input.getMessageContext().getSubcontext(SAMLSelfEntityContext.class);
      if (selfContext != null && selfContext.getEntityId() != null) {
         validRecipients.add(selfContext.getEntityId());
      }

      this.log.debug("Resolved valid subject confirmation recipients set: {}", validRecipients);
      return validRecipients;
   }

   @Nonnull
   protected Set getValidAddresses(@Nonnull SAML20AssertionTokenValidationInput input) {
      try {
         LazySet validAddresses = new LazySet();
         InetAddress[] addresses = null;
         String attesterIPAddress = this.getAttesterIPAddress(input);
         this.log.debug("Saw attester IP address: {}", attesterIPAddress);
         if (attesterIPAddress != null) {
            addresses = InetAddress.getAllByName(attesterIPAddress);
            validAddresses.addAll(Arrays.asList(addresses));
            this.log.debug("Resolved valid subject confirmation InetAddress set: {}", validAddresses);
            return validAddresses;
         } else {
            this.log.warn("Could not determine attester IP address. Validation of Assertion may or may not succeed");
            return Collections.emptySet();
         }
      } catch (UnknownHostException var5) {
         this.log.warn("Processing of attester IP address failed. Validation of Assertion may or may not succeed", var5);
         return Collections.emptySet();
      }
   }

   @Nonnull
   protected String getAttesterIPAddress(@Nonnull SAML20AssertionTokenValidationInput input) {
      return input.getHttpServletRequest().getRemoteAddr();
   }

   @Nonnull
   protected Set getValidAudiences(@Nonnull SAML20AssertionTokenValidationInput input) {
      LazySet validAudiences = new LazySet();
      SAMLSelfEntityContext selfContext = (SAMLSelfEntityContext)input.getMessageContext().getSubcontext(SAMLSelfEntityContext.class);
      if (selfContext != null && selfContext.getEntityId() != null) {
         validAudiences.add(selfContext.getEntityId());
      }

      this.log.debug("Resolved valid audiences set: {}", validAudiences);
      return validAudiences;
   }
}
