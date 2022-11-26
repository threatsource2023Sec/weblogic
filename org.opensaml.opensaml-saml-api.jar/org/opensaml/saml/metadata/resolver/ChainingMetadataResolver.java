package org.opensaml.saml.metadata.resolver;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.DateTime;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingMetadataResolver extends AbstractIdentifiableInitializableComponent implements MetadataResolver, RefreshableMetadataResolver {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ChainingMetadataResolver.class);
   @Nonnull
   @NonnullElements
   private List resolvers = Collections.emptyList();

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getResolvers() {
      return ImmutableList.copyOf(this.resolvers);
   }

   public void setResolvers(@Nonnull @NonnullElements List newResolvers) throws ResolverException {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      if (newResolvers != null && !newResolvers.isEmpty()) {
         this.resolvers = new ArrayList(Collections2.filter(newResolvers, Predicates.notNull()));
      } else {
         this.resolvers = Collections.emptyList();
      }
   }

   public boolean isRequireValidMetadata() {
      this.log.warn("Attempt to access unsupported requireValidMetadata property on ChainingMetadataResolver");
      return false;
   }

   public void setRequireValidMetadata(boolean requireValidMetadata) {
      throw new UnsupportedOperationException("Setting requireValidMetadata is not supported on chaining resolver");
   }

   public MetadataFilter getMetadataFilter() {
      this.log.warn("Attempt to access unsupported MetadataFilter property on ChainingMetadataResolver");
      return null;
   }

   public void setMetadataFilter(MetadataFilter newFilter) {
      throw new UnsupportedOperationException("Metadata filters are not supported on ChainingMetadataResolver");
   }

   @Nullable
   public EntityDescriptor resolveSingle(@Nullable CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      Iterable iterable = this.resolve(criteria);
      if (iterable != null) {
         Iterator iterator = iterable.iterator();
         if (iterator != null && iterator.hasNext()) {
            return (EntityDescriptor)iterator.next();
         }
      }

      return null;
   }

   @Nonnull
   public Iterable resolve(@Nullable CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      Iterator var2 = this.resolvers.iterator();

      while(var2.hasNext()) {
         MetadataResolver resolver = (MetadataResolver)var2.next();

         try {
            Iterable descriptors = resolver.resolve(criteria);
            if (descriptors != null && descriptors.iterator().hasNext()) {
               return descriptors;
            }
         } catch (ResolverException var5) {
            this.log.warn("Error retrieving metadata from resolver of type {}, proceeding to next resolver", resolver.getClass().getName(), var5);
         }
      }

      return Collections.emptyList();
   }

   public void refresh() throws ResolverException {
      Iterator var1 = this.resolvers.iterator();

      while(var1.hasNext()) {
         MetadataResolver resolver = (MetadataResolver)var1.next();
         if (resolver instanceof RefreshableMetadataResolver) {
            ((RefreshableMetadataResolver)resolver).refresh();
         }
      }

   }

   @Nullable
   public DateTime getLastUpdate() {
      DateTime ret = null;
      Iterator var2 = this.resolvers.iterator();

      while(true) {
         DateTime lastUpdate;
         do {
            MetadataResolver resolver;
            do {
               if (!var2.hasNext()) {
                  return ret;
               }

               resolver = (MetadataResolver)var2.next();
            } while(!(resolver instanceof RefreshableMetadataResolver));

            lastUpdate = ((RefreshableMetadataResolver)resolver).getLastUpdate();
         } while(ret != null && !ret.isBefore(lastUpdate));

         ret = lastUpdate;
      }
   }

   @Nullable
   public DateTime getLastRefresh() {
      DateTime ret = null;
      Iterator var2 = this.resolvers.iterator();

      while(true) {
         DateTime lastRefresh;
         do {
            MetadataResolver resolver;
            do {
               if (!var2.hasNext()) {
                  return ret;
               }

               resolver = (MetadataResolver)var2.next();
            } while(!(resolver instanceof RefreshableMetadataResolver));

            lastRefresh = ((RefreshableMetadataResolver)resolver).getLastRefresh();
         } while(ret != null && !ret.isBefore(lastRefresh));

         ret = lastRefresh;
      }
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.resolvers == null) {
         this.log.warn("ChainingMetadataResolver was not configured with any member MetadataResolvers");
         this.resolvers = Collections.emptyList();
      }

   }

   protected void doDestroy() {
      super.doDestroy();
      this.resolvers = Collections.emptyList();
   }
}
