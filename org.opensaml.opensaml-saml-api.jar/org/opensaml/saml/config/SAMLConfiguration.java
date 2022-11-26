package org.opensaml.saml.config;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactBuilderFactory;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactBuilderFactory;

public class SAMLConfiguration {
   private static Function lowercaseFunction = new LowercaseFunction();
   private static String defaultDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
   private DateTimeFormatter dateFormatter;
   private SAML1ArtifactBuilderFactory saml1ArtifactBuilderFactory;
   private SAML2ArtifactBuilderFactory saml2ArtifactBuilderFactory;
   private List allowedBindingURLSchemes;

   public SAMLConfiguration() {
      this.setAllowedBindingURLSchemes(Lists.newArrayList(new String[]{"http", "https"}));
   }

   public DateTimeFormatter getSAMLDateFormatter() {
      if (this.dateFormatter == null) {
         DateTimeFormatter formatter = DateTimeFormat.forPattern(defaultDateFormat);
         this.dateFormatter = formatter.withChronology(ISOChronology.getInstanceUTC());
      }

      return this.dateFormatter;
   }

   public void setSAMLDateFormat(String format) {
      DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
      this.dateFormatter = formatter.withChronology(ISOChronology.getInstanceUTC());
   }

   public SAML1ArtifactBuilderFactory getSAML1ArtifactBuilderFactory() {
      return this.saml1ArtifactBuilderFactory;
   }

   public void setSAML1ArtifactBuilderFactory(SAML1ArtifactBuilderFactory factory) {
      this.saml1ArtifactBuilderFactory = factory;
   }

   public SAML2ArtifactBuilderFactory getSAML2ArtifactBuilderFactory() {
      return this.saml2ArtifactBuilderFactory;
   }

   public void setSAML2ArtifactBuilderFactory(SAML2ArtifactBuilderFactory factory) {
      this.saml2ArtifactBuilderFactory = factory;
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getAllowedBindingURLSchemes() {
      return Collections.unmodifiableList(this.allowedBindingURLSchemes);
   }

   public void setAllowedBindingURLSchemes(@Nullable List schemes) {
      if (schemes != null && !schemes.isEmpty()) {
         Collection normalized = Collections2.transform(StringSupport.normalizeStringCollection(schemes), lowercaseFunction);
         this.allowedBindingURLSchemes = new ArrayList(normalized);
      } else {
         this.allowedBindingURLSchemes = Collections.emptyList();
      }

   }

   private static class LowercaseFunction implements Function {
      private LowercaseFunction() {
      }

      public String apply(String input) {
         return input == null ? null : input.toLowerCase();
      }

      // $FF: synthetic method
      LowercaseFunction(Object x0) {
         this();
      }
   }
}
