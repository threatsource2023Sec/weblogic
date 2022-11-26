package org.opensaml.saml.saml2.profile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;
import org.joda.time.DateTime;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SAML2ActionSupport {
   private SAML2ActionSupport() {
   }

   @Nonnull
   public static Assertion buildAssertion(@Nonnull AbstractProfileAction action, @Nonnull IdentifierGenerationStrategy idGenerator, @Nullable String issuer) {
      SAMLObjectBuilder assertionBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Assertion.DEFAULT_ELEMENT_NAME);
      Assertion assertion = (Assertion)assertionBuilder.buildObject();
      assertion.setID(idGenerator.generateIdentifier());
      assertion.setIssueInstant(new DateTime());
      assertion.setVersion(SAMLVersion.VERSION_20);
      if (issuer != null) {
         SAMLObjectBuilder issuerBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Issuer.DEFAULT_ELEMENT_NAME);
         Issuer issuerObject = (Issuer)issuerBuilder.buildObject();
         issuerObject.setValue(issuer);
         assertion.setIssuer(issuerObject);
      }

      getLogger().debug("Profile Action {}: Created Assertion {}", action.getClass().getSimpleName(), assertion.getID());
      return assertion;
   }

   @Nonnull
   public static Assertion addAssertionToResponse(@Nonnull AbstractProfileAction action, @Nonnull Response response, @Nonnull IdentifierGenerationStrategy idGenerator, @Nullable String issuer) {
      Assertion assertion = buildAssertion(action, idGenerator, issuer);
      assertion.setIssueInstant(response.getIssueInstant());
      getLogger().debug("Profile Action {}: Added Assertion {} to Response {}", new Object[]{action.getClass().getSimpleName(), assertion.getID(), response.getID()});
      response.getAssertions().add(assertion);
      return assertion;
   }

   @Nonnull
   public static Conditions addConditionsToAssertion(@Nonnull AbstractProfileAction action, @Nonnull Assertion assertion) {
      Conditions conditions = assertion.getConditions();
      if (conditions == null) {
         SAMLObjectBuilder conditionsBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Conditions.DEFAULT_ELEMENT_NAME);
         conditions = (Conditions)conditionsBuilder.buildObject();
         assertion.setConditions(conditions);
         getLogger().debug("Profile Action {}: Assertion {} did not already contain Conditions, one was added", action.getClass().getSimpleName(), assertion.getID());
      } else {
         getLogger().debug("Profile Action {}: Assertion {} already contained Conditions, nothing was done", action.getClass().getSimpleName(), assertion.getID());
      }

      return conditions;
   }

   @Nonnull
   public static Advice addAdviceToAssertion(@Nonnull AbstractProfileAction action, @Nonnull Assertion assertion) {
      Advice advice = assertion.getAdvice();
      if (advice == null) {
         SAMLObjectBuilder adviceBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Advice.DEFAULT_ELEMENT_NAME);
         advice = (Advice)adviceBuilder.buildObject();
         assertion.setAdvice(advice);
         getLogger().debug("Profile Action {}: Assertion {} did not already contain Advice, one was added", action.getClass().getSimpleName(), assertion.getID());
      } else {
         getLogger().debug("Profile Action {}: Assertion {} already contained Advice, nothing was done", action.getClass().getSimpleName(), assertion.getID());
      }

      return advice;
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(SAML2ActionSupport.class);
   }
}
