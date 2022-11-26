package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiedInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.DateTime;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.RefreshableMetadataResolver;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositeMetadataResolver extends AbstractIdentifiedInitializableComponent implements MetadataResolver, RefreshableMetadataResolver {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(CompositeMetadataResolver.class);
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
      throw new UnsupportedOperationException("Setting require valid metadata is not supported on chaining resolver");
   }

   @Nullable
   public MetadataFilter getMetadataFilter() {
      this.log.warn("Attempt to access unsupported MetadataFilter property on ChainingMetadataResolver");
      return null;
   }

   public void setMetadataFilter(@Nullable MetadataFilter newFilter) {
      throw new UnsupportedOperationException("Metadata filters are not supported on ChainingMetadataProviders");
   }

   public Iterable resolve(@Nullable CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      return new CompositeMetadataResolverIterable(this.resolvers, criteria);
   }

   public EntityDescriptor resolveSingle(@Nullable CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      EntityDescriptor metadata = null;
      Iterator var3 = this.resolvers.iterator();

      do {
         if (!var3.hasNext()) {
            return null;
         }

         MetadataResolver resolver = (MetadataResolver)var3.next();
         metadata = (EntityDescriptor)resolver.resolveSingle(criteria);
      } while(metadata == null);

      return metadata;
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.resolvers == null) {
         this.log.warn("CompositeMetadataResolver was not configured with any member MetadataResolvers");
         this.resolvers = Collections.emptyList();
      }

   }

   protected void doDestroy() {
      super.doDestroy();
      this.resolvers = Collections.emptyList();
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

   private static class CompositeMetadataResolverIterable implements Iterable {
      private final Logger log = LoggerFactory.getLogger(CompositeMetadataResolverIterable.class);
      private final List resolvers;
      private final CriteriaSet criteria;

      public CompositeMetadataResolverIterable(List composedResolvers, CriteriaSet metadataCritiera) {
         this.resolvers = ImmutableList.builder().addAll(Iterables.filter(composedResolvers, Predicates.notNull())).build();
         this.criteria = metadataCritiera;
      }

      public Iterator iterator() {
         return new CompositeMetadataResolverIterator();
      }

      private class CompositeMetadataResolverIterator implements Iterator {
         private Iterator resolverIterator;
         private MetadataResolver currentResolver;
         private Iterator currentResolverMetadataIterator;

         public CompositeMetadataResolverIterator() {
            this.resolverIterator = CompositeMetadataResolverIterable.this.resolvers.iterator();
         }

         public boolean hasNext() {
            if (!this.currentResolverMetadataIterator.hasNext()) {
               this.proceedToNextResolverIterator();
            }

            return this.currentResolverMetadataIterator.hasNext();
         }

         public EntityDescriptor next() {
            if (!this.currentResolverMetadataIterator.hasNext()) {
               this.proceedToNextResolverIterator();
            }

            return (EntityDescriptor)this.currentResolverMetadataIterator.next();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }

         private void proceedToNextResolverIterator() {
            while(true) {
               try {
                  if (this.resolverIterator.hasNext()) {
                     this.currentResolver = (MetadataResolver)this.resolverIterator.next();
                     this.currentResolverMetadataIterator = this.currentResolver.resolve(CompositeMetadataResolverIterable.this.criteria).iterator();
                     if (!this.currentResolverMetadataIterator.hasNext()) {
                        continue;
                     }

                     return;
                  }
               } catch (ResolverException var2) {
                  CompositeMetadataResolverIterable.this.log.debug("Error encountered attempting to fetch results from resolver", var2);
               }

               return;
            }
         }
      }
   }
}
