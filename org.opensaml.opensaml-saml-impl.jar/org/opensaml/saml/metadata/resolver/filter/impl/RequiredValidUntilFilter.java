package org.opensaml.saml.metadata.resolver.filter.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequiredValidUntilFilter implements MetadataFilter {
   @Nonnull
   private final Logger log;
   @Duration
   private long maxValidityInterval;

   public RequiredValidUntilFilter() {
      this(0L);
   }

   public RequiredValidUntilFilter(long maxValidity) {
      this.log = LoggerFactory.getLogger(RequiredValidUntilFilter.class);
      this.maxValidityInterval = maxValidity * 1000L;
   }

   @Duration
   public long getMaxValidityInterval() {
      return this.maxValidityInterval;
   }

   @Duration
   public void setMaxValidityInterval(@Duration long validity) {
      this.maxValidityInterval = validity;
   }

   @Nullable
   public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
      if (metadata == null) {
         return null;
      } else {
         DateTime validUntil = this.getValidUntil(metadata);
         if (validUntil == null) {
            throw new FilterException("Metadata did not include a validUntil attribute");
         } else {
            DateTime now = new DateTime(ISOChronology.getInstanceUTC());
            if (this.maxValidityInterval > 0L && validUntil.isAfter(now)) {
               long validityInterval = validUntil.getMillis() - now.getMillis();
               if (validityInterval > this.maxValidityInterval) {
                  throw new FilterException(String.format("Metadata's validity interval %s is larger than is allowed %s", DOMTypeSupport.longToDuration(validityInterval), DOMTypeSupport.longToDuration(this.maxValidityInterval)));
               }
            }

            return metadata;
         }
      }
   }

   @Nullable
   protected DateTime getValidUntil(@Nonnull XMLObject metadata) throws FilterException {
      if (metadata instanceof EntitiesDescriptor) {
         return ((EntitiesDescriptor)metadata).getValidUntil();
      } else if (metadata instanceof EntityDescriptor) {
         return ((EntityDescriptor)metadata).getValidUntil();
      } else {
         this.log.error("Metadata root element was not an EntitiesDescriptor or EntityDescriptor it was a {}", metadata.getElementQName());
         throw new FilterException("Metadata root element was not an EntitiesDescriptor or EntityDescriptor");
      }
   }
}
