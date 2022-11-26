package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPEntityIDRequestURLBuilder implements Function {
   private final Logger log = LoggerFactory.getLogger(HTTPEntityIDRequestURLBuilder.class);

   @Nullable
   public String apply(@Nonnull String entityID) {
      Constraint.isNotNull(entityID, "Entity ID was null");
      if (!entityID.toLowerCase().startsWith("http:") && !entityID.toLowerCase().startsWith("https:")) {
         this.log.debug("EntityID was not an HTTP or HTTPS URL, could not construct request URL on that basis");
         return null;
      } else {
         this.log.debug("Saw entityID with HTTP/HTTPS URL syntax, returning the entityID itself as request URL");
         return entityID;
      }
   }
}
