package org.opensaml.saml.common.profile.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ListMultimap;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NullableElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.profile.FormatSpecificNameIdentifierGenerator;
import org.opensaml.saml.common.profile.NameIdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingNameIdentifierGenerator implements NameIdentifierGenerator {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ChainingNameIdentifierGenerator.class);
   @Nonnull
   @NonnullElements
   private ListMultimap nameIdGeneratorMap = ArrayListMultimap.create();
   @Nullable
   private NameIdentifierGenerator defaultNameIdGenerator;

   public void setGenerators(@Nonnull @NullableElements List generators) {
      Constraint.isNotNull(generators, "NameIdentifierGenerator list cannot be null");
      this.nameIdGeneratorMap.clear();
      Iterator var2 = Collections2.filter(generators, Predicates.notNull()).iterator();

      while(var2.hasNext()) {
         NameIdentifierGenerator generator = (NameIdentifierGenerator)var2.next();
         if (generator instanceof FormatSpecificNameIdentifierGenerator) {
            this.nameIdGeneratorMap.put(((FormatSpecificNameIdentifierGenerator)generator).getFormat(), generator);
         } else {
            this.log.warn("Unable to install NameIdentifierGenerator of type {}, not format-specific", generator.getClass().getName());
         }
      }

   }

   public void setDefaultGenerator(@Nullable NameIdentifierGenerator generator) {
      this.defaultNameIdGenerator = generator;
   }

   @Nullable
   public SAMLObject generate(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull @NotEmpty String format) throws SAMLException {
      this.log.debug("Trying to generate identifier with Format {}", format);
      List generators = this.nameIdGeneratorMap.get(format);
      if (generators.isEmpty() && this.defaultNameIdGenerator != null) {
         this.log.debug("No generators installed for Format {}, trying default/fallback method", format);
         generators = Collections.singletonList(this.defaultNameIdGenerator);
      }

      Iterator var4 = generators.iterator();

      while(var4.hasNext()) {
         NameIdentifierGenerator generator = (NameIdentifierGenerator)var4.next();

         try {
            SAMLObject nameIdentifier = generator.generate(profileRequestContext, format);
            if (nameIdentifier != null) {
               this.log.debug("Successfully generated identifier with Format {}", format);
               return nameIdentifier;
            }
         } catch (SAMLException var7) {
            this.log.error("Error while generating identifier", var7);
         }
      }

      return null;
   }
}
