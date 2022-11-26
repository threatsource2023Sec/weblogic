package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataQueryProtocolRequestURLBuilder implements Function {
   private final Logger log;
   private String base;
   private Function transformer;
   private Escaper pathEscaper;

   public MetadataQueryProtocolRequestURLBuilder(@Nonnull @NotEmpty String baseURL) {
      this(baseURL, (Function)null);
   }

   public MetadataQueryProtocolRequestURLBuilder(@Nonnull @NotEmpty String baseURL, @Nullable Function transform) {
      this.log = LoggerFactory.getLogger(MetadataQueryProtocolRequestURLBuilder.class);
      this.pathEscaper = UrlEscapers.urlPathSegmentEscaper();
      this.base = (String)Constraint.isNotNull(StringSupport.trimOrNull(baseURL), "Base URL was null or empty");
      if (!this.base.endsWith("/")) {
         this.log.debug("Base URL did not end in a trailing '/', one will be added");
         this.base = this.base + "/";
      }

      this.log.debug("Effective base URL value was: {}", this.base);
      this.transformer = transform;
   }

   @Nullable
   public String apply(@Nonnull String input) {
      String entityID = (String)Constraint.isNotNull(input, "Entity ID was null");
      if (this.transformer != null) {
         entityID = (String)this.transformer.apply(entityID);
         this.log.debug("Transformed entityID is '{}'", entityID);
         if (entityID == null) {
            this.log.debug("Transformed entityID was null");
            return null;
         }
      }

      try {
         String result = this.base + "entities/" + this.pathEscaper.escape(entityID);
         this.log.debug("From entityID '{}' and base URL '{}', built request URL: {}", new Object[]{entityID, this.base, result});
         return result;
      } catch (Throwable var4) {
         this.log.error("Encountered fatal error attempting to build request URL", var4);
         return null;
      }
   }
}
