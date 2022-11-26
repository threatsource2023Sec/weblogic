package org.opensaml.saml.metadata.resolver.filter;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataFilterChain implements MetadataFilter {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(MetadataFilterChain.class);
   @Nonnull
   @NonnullElements
   private List filters = Collections.emptyList();

   @Nullable
   public final XMLObject filter(@Nullable XMLObject xmlObject) throws FilterException {
      if (xmlObject == null) {
         return null;
      } else {
         synchronized(this.filters) {
            if (this.filters != null && !this.filters.isEmpty()) {
               XMLObject current = xmlObject;

               MetadataFilter filter;
               for(Iterator var4 = this.filters.iterator(); var4.hasNext(); current = filter.filter(current)) {
                  filter = (MetadataFilter)var4.next();
                  if (current == null) {
                     return null;
                  }

                  this.log.debug("Applying filter {}", filter.getClass().getName());
               }

               return current;
            } else {
               this.log.debug("No filters configured, nothing to do");
               return xmlObject;
            }
         }
      }
   }

   @Nonnull
   @NonnullElements
   @Live
   public List getFilters() {
      return this.filters;
   }

   public void setFilters(@Nonnull @NonnullElements List newFilters) {
      Constraint.isNotNull(newFilters, "Filter collection cannot be null");
      this.filters = new ArrayList(Collections2.filter(newFilters, Predicates.notNull()));
   }
}
