package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.URISupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateRequestURLBuilder implements Function {
   public static final String CONTEXT_KEY_ENTITY_ID = "entityID";
   private final Logger log;
   private Template template;
   private String templateText;
   private Function transformer;
   private boolean encodeEntityID;

   public TemplateRequestURLBuilder(@Nonnull VelocityEngine engine, @Nonnull @NotEmpty String templateString, boolean encoded) {
      this(engine, templateString, encoded, (Function)null, StandardCharsets.US_ASCII);
   }

   public TemplateRequestURLBuilder(@Nonnull VelocityEngine engine, @Nonnull @NotEmpty String templateString, boolean encoded, @Nullable Function transform) {
      this(engine, templateString, encoded, transform, StandardCharsets.US_ASCII);
   }

   public TemplateRequestURLBuilder(@Nonnull VelocityEngine engine, @Nonnull @NotEmpty String templateString, boolean encoded, @Nullable Function transform, @Nullable Charset charSet) {
      this.log = LoggerFactory.getLogger(TemplateRequestURLBuilder.class);
      Constraint.isNotNull(engine, "VelocityEngine was null");
      String trimmedTemplate = StringSupport.trimOrNull(templateString);
      this.templateText = (String)Constraint.isNotNull(trimmedTemplate, "Template string was null or empty");
      this.transformer = transform;
      if (charSet != null) {
         this.template = Template.fromTemplate(engine, trimmedTemplate, charSet);
      } else {
         this.template = Template.fromTemplate(engine, trimmedTemplate);
      }

      this.encodeEntityID = encoded;
   }

   @Nullable
   public String apply(@Nonnull String input) {
      String entityID = (String)Constraint.isNotNull(input, "Entity ID was null");
      this.log.debug("Saw input entityID '{}'", entityID);
      if (this.transformer != null) {
         entityID = (String)this.transformer.apply(entityID);
         this.log.debug("Transformed entityID is '{}'", entityID);
         if (entityID == null) {
            this.log.debug("Transformed entityID was null");
            return null;
         }
      }

      VelocityContext context = new VelocityContext();
      if (this.encodeEntityID) {
         context.put("entityID", URISupport.doURLEncode(entityID));
      } else {
         context.put("entityID", entityID);
      }

      try {
         String result = this.template.merge(context);
         this.log.debug("From entityID '{}' and template text '{}', built request URL: {}", new Object[]{entityID, this.templateText, result});
         return result;
      } catch (Throwable var5) {
         this.log.error("Encountered fatal error attempting to build request URL", var5);
         return null;
      }
   }
}
