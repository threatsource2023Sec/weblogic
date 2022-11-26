package org.opensaml.security.credential.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractChainingCredentialResolver extends AbstractCredentialResolver {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ChainingCredentialResolver.class);
   @Nonnull
   @NonnullElements
   private List resolvers;

   public AbstractChainingCredentialResolver(@Nonnull List credResolvers) {
      Constraint.isNotNull(credResolvers, "CredentialResolver list cannot be null");
      this.resolvers = new ArrayList(Collections2.filter(credResolvers, Predicates.notNull()));
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getResolverChain() {
      return ImmutableList.copyOf(this.resolvers);
   }

   @Nonnull
   public Iterable resolve(@Nullable CriteriaSet criteriaSet) throws ResolverException {
      if (this.resolvers.isEmpty()) {
         this.log.warn("Chaining credential resolver resolution was attempted with an empty resolver chain");
         throw new IllegalStateException("The resolver chain is empty");
      } else {
         return new CredentialIterable(this, criteriaSet);
      }
   }

   public class CredentialIterator implements Iterator {
      @Nonnull
      private final Logger log = LoggerFactory.getLogger(CredentialIterator.class);
      private AbstractChainingCredentialResolver parent;
      private CriteriaSet critSet;
      private Iterator resolverIterator;
      private Iterator credentialIterator;
      private CredentialResolver currentResolver;
      private Credential nextCredential;

      public CredentialIterator(@Nonnull AbstractChainingCredentialResolver resolver, @Nullable CriteriaSet criteriaSet) {
         Constraint.isNotNull(resolver, "Parent resolver cannot be null");
         this.parent = resolver;
         this.critSet = criteriaSet;
         this.resolverIterator = this.parent.getResolverChain().iterator();
         this.credentialIterator = this.getNextCredentialIterator();
         this.nextCredential = null;
      }

      public boolean hasNext() {
         if (this.nextCredential != null) {
            return true;
         } else {
            this.nextCredential = this.getNextCredential();
            return this.nextCredential != null;
         }
      }

      public Credential next() {
         Credential tempCred;
         if (this.nextCredential != null) {
            tempCred = this.nextCredential;
            this.nextCredential = null;
            return tempCred;
         } else {
            tempCred = this.getNextCredential();
            if (tempCred != null) {
               return tempCred;
            } else {
               throw new NoSuchElementException("No more Credential elements are available");
            }
         }
      }

      public void remove() {
         throw new UnsupportedOperationException("Remove operation is not supported by this iterator");
      }

      @Nullable
      private Iterator getNextCredentialIterator() {
         while(this.resolverIterator.hasNext()) {
            this.currentResolver = (CredentialResolver)this.resolverIterator.next();
            this.log.debug("Getting credential iterator from next resolver in chain: {}", this.currentResolver.getClass().toString());

            try {
               return this.currentResolver.resolve(this.critSet).iterator();
            } catch (ResolverException var2) {
               this.log.error(String.format("Error resolving credentials from chaining resolver member '%s'", this.currentResolver.getClass().getName()), var2);
               if (this.resolverIterator.hasNext()) {
                  this.log.error("Will attempt to resolve credentials from next member of resolver chain");
               }
            }
         }

         this.log.debug("No more credential resolvers available in the resolver chain");
         this.currentResolver = null;
         return null;
      }

      @Nullable
      private Credential getNextCredential() {
         if (this.credentialIterator != null && this.credentialIterator.hasNext()) {
            return (Credential)this.credentialIterator.next();
         } else {
            for(this.credentialIterator = this.getNextCredentialIterator(); this.credentialIterator != null; this.credentialIterator = this.getNextCredentialIterator()) {
               if (this.credentialIterator.hasNext()) {
                  return (Credential)this.credentialIterator.next();
               }
            }

            return null;
         }
      }
   }

   public class CredentialIterable implements Iterable {
      private AbstractChainingCredentialResolver parent;
      private CriteriaSet critSet;

      public CredentialIterable(@Nonnull AbstractChainingCredentialResolver resolver, @Nullable CriteriaSet criteriaSet) {
         this.parent = resolver;
         this.critSet = criteriaSet;
      }

      @Nonnull
      public Iterator iterator() {
         return AbstractChainingCredentialResolver.this.new CredentialIterator(this.parent, this.critSet);
      }
   }
}
