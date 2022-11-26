package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityIdPredicate implements Predicate {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(EntityIdPredicate.class);
   @Nonnull
   @NonnullElements
   private final Set entityIds;

   public EntityIdPredicate(@Nonnull @NonnullElements Collection ids) {
      Constraint.isNotNull(ids, "EntityID collection cannot be null");
      this.entityIds = new HashSet(ids.size());
      Iterator var2 = ids.iterator();

      while(var2.hasNext()) {
         String id = (String)var2.next();
         String trimmed = StringSupport.trimOrNull(id);
         if (trimmed != null) {
            this.entityIds.add(trimmed);
         }
      }

   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set getEntityIds() {
      return ImmutableSet.copyOf(this.entityIds);
   }

   public boolean apply(@Nullable EntityDescriptor input) {
      return input != null && input.getEntityID() != null ? this.entityIds.contains(input.getEntityID()) : false;
   }
}
