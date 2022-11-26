package org.opensaml.saml.saml1.profile;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;
import org.joda.time.DateTime;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Conditions;
import org.opensaml.saml.saml1.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SAML1ActionSupport {
   private SAML1ActionSupport() {
   }

   @Nonnull
   public static Assertion buildAssertion(@Nonnull AbstractProfileAction action, @Nonnull IdentifierGenerationStrategy idGenerator, @Nonnull @NotEmpty String issuer) {
      SAMLObjectBuilder assertionBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Assertion.DEFAULT_ELEMENT_NAME);
      Assertion assertion = (Assertion)assertionBuilder.buildObject();
      assertion.setID(idGenerator.generateIdentifier());
      assertion.setIssueInstant(new DateTime());
      assertion.setIssuer(issuer);
      assertion.setVersion(SAMLVersion.VERSION_11);
      getLogger().debug("Profile Action {}: Created Assertion {}", action.getClass().getSimpleName(), assertion.getID());
      return assertion;
   }

   @Nonnull
   public static Assertion addAssertionToResponse(@Nonnull AbstractProfileAction action, @Nonnull Response response, @Nonnull IdentifierGenerationStrategy idGenerator, @Nonnull @NotEmpty String issuer) {
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
         getLogger().debug("Profile Action {}: Assertion {} did not already contain Conditions, added", action.getClass().getSimpleName(), assertion.getID());
      } else {
         getLogger().debug("Profile Action {}: Assertion {} already contains Conditions, nothing was done", action.getClass().getSimpleName(), assertion.getID());
      }

      return conditions;
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(SAML1ActionSupport.class);
   }
}
