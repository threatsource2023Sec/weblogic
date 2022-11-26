package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexRequestURLBuilder implements Function {
   private final Logger log = LoggerFactory.getLogger(RegexRequestURLBuilder.class);
   private Pattern pattern;
   private String template;

   public RegexRequestURLBuilder(@Nonnull @NotEmpty String regex, @Nonnull @NotEmpty String replacement) {
      String regexTemp = (String)Constraint.isNotNull(StringSupport.trimOrNull(regex), "Regex was null or empty");
      this.pattern = Pattern.compile(regexTemp);
      this.template = (String)Constraint.isNotNull(StringSupport.trimOrNull(replacement), "Replacement template was null or empty");
   }

   @Nullable
   public String apply(@Nonnull String entityID) {
      Constraint.isNotNull(entityID, "Entity ID was null");

      try {
         Matcher matcher = this.pattern.matcher(entityID);
         if (matcher.matches()) {
            String result = matcher.replaceAll(this.template);
            this.log.debug("Regular expression '{}' matched successfully against entity ID '{}', returning '{}'", new Object[]{this.pattern.pattern(), entityID, result});
            return result;
         } else {
            this.log.debug("Regular expression '{}' did not match against entity ID '{}', returning null", this.pattern.pattern(), entityID);
            return null;
         }
      } catch (Throwable var4) {
         this.log.warn("Error evaluating regular expression '{}' against entity ID '{}'", new Object[]{this.pattern.pattern(), entityID, var4});
         return null;
      }
   }
}
