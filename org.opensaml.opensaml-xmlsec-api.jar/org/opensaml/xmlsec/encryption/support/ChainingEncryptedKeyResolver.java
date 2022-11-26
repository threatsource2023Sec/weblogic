package org.opensaml.xmlsec.encryption.support;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingEncryptedKeyResolver extends AbstractEncryptedKeyResolver {
   @Nonnull
   @NonnullElements
   private final List resolvers;
   @Nonnull
   private final Logger log;

   public ChainingEncryptedKeyResolver(@Nonnull List encKeyResolvers) {
      this.log = LoggerFactory.getLogger(ChainingEncryptedKeyResolver.class);
      Constraint.isNotNull(encKeyResolvers, "List of EncryptedKeyResolvers cannot be null");
      this.resolvers = new ArrayList(Collections2.filter(encKeyResolvers, Predicates.notNull()));
   }

   public ChainingEncryptedKeyResolver(@Nonnull List encKeyResolvers, @Nullable Set recipients) {
      super(recipients);
      this.log = LoggerFactory.getLogger(ChainingEncryptedKeyResolver.class);
      Constraint.isNotNull(encKeyResolvers, "List of EncryptedKeyResolvers cannot be null");
      this.resolvers = new ArrayList(Collections2.filter(encKeyResolvers, Predicates.notNull()));
   }

   public ChainingEncryptedKeyResolver(@Nonnull List encKeyResolvers, @Nullable String recipient) {
      this(encKeyResolvers, Collections.singleton(recipient));
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getResolverChain() {
      return ImmutableList.copyOf(this.resolvers);
   }

   @Nonnull
   public Iterable resolve(@Nonnull EncryptedData encryptedData) {
      if (this.resolvers.isEmpty()) {
         this.log.warn("Chaining encrypted key resolver resolution was attempted with an empty resolver chain");
         throw new IllegalStateException("The resolver chain is empty");
      } else {
         return new ChainingIterable(this, encryptedData);
      }
   }

   public class ChainingIterator implements Iterator {
      private final Logger log = LoggerFactory.getLogger(ChainingIterator.class);
      private final ChainingEncryptedKeyResolver parent;
      private final EncryptedData encryptedData;
      private final Iterator resolverIterator;
      private Iterator keyIterator;
      private EncryptedKeyResolver currentResolver;
      private EncryptedKey nextKey;

      public ChainingIterator(@Nonnull ChainingEncryptedKeyResolver resolver, @Nonnull EncryptedData encData) {
         this.parent = resolver;
         this.encryptedData = encData;
         this.resolverIterator = this.parent.getResolverChain().iterator();
         this.keyIterator = this.getNextKeyIterator();
         this.nextKey = null;
      }

      public boolean hasNext() {
         if (this.nextKey != null) {
            return true;
         } else {
            this.nextKey = this.getNextKey();
            return this.nextKey != null;
         }
      }

      public EncryptedKey next() {
         EncryptedKey tempKey;
         if (this.nextKey != null) {
            tempKey = this.nextKey;
            this.nextKey = null;
            return tempKey;
         } else {
            tempKey = this.getNextKey();
            if (tempKey != null) {
               return tempKey;
            } else {
               throw new NoSuchElementException("No more EncryptedKey elements are available");
            }
         }
      }

      public void remove() {
         throw new UnsupportedOperationException("Remove operation is not supported by this iterator");
      }

      @Nullable
      private Iterator getNextKeyIterator() {
         if (this.resolverIterator.hasNext()) {
            this.currentResolver = (EncryptedKeyResolver)this.resolverIterator.next();
            this.log.debug("Getting key iterator from next resolver: {}", this.currentResolver.getClass().toString());
            return this.currentResolver.resolve(this.encryptedData).iterator();
         } else {
            this.log.debug("No more resolvers available in the resolver chain");
            this.currentResolver = null;
            return null;
         }
      }

      @Nullable
      private EncryptedKey getNextKey() {
         EncryptedKey tempKey;
         if (this.keyIterator != null) {
            while(this.keyIterator.hasNext()) {
               tempKey = (EncryptedKey)this.keyIterator.next();
               if (this.parent.matchRecipient(tempKey.getRecipient())) {
                  this.log.debug("Found matching encrypted key: {}", tempKey.toString());
                  return tempKey;
               }
            }
         }

         for(this.keyIterator = this.getNextKeyIterator(); this.keyIterator != null; this.keyIterator = this.getNextKeyIterator()) {
            while(this.keyIterator.hasNext()) {
               tempKey = (EncryptedKey)this.keyIterator.next();
               if (this.parent.matchRecipient(tempKey.getRecipient())) {
                  this.log.debug("Found matching encrypted key: {}", tempKey.toString());
                  return tempKey;
               }
            }
         }

         return null;
      }
   }

   public class ChainingIterable implements Iterable {
      private final ChainingEncryptedKeyResolver parent;
      private final EncryptedData encryptedData;

      public ChainingIterable(@Nonnull ChainingEncryptedKeyResolver resolver, @Nonnull EncryptedData encData) {
         this.parent = resolver;
         this.encryptedData = encData;
      }

      @Nonnull
      public Iterator iterator() {
         return ChainingEncryptedKeyResolver.this.new ChainingIterator(this.parent, this.encryptedData);
      }
   }
}
