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
import org.opensaml.saml.metadata.EntityGroupName;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class EntityGroupNamePredicate implements Predicate {
   @Nonnull
   @NonnullElements
   private final Set groupNames;

   public EntityGroupNamePredicate(@Nonnull @NonnullElements Collection names) {
      Constraint.isNotNull(names, "Group name collection cannot be null");
      this.groupNames = new HashSet(names.size());
      Iterator var2 = names.iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         String trimmed = StringSupport.trimOrNull(name);
         if (trimmed != null) {
            this.groupNames.add(trimmed);
         }
      }

   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set getGroupNames() {
      return ImmutableSet.copyOf(this.groupNames);
   }

   public boolean apply(@Nullable EntityDescriptor input) {
      if (input != null) {
         Iterator var2 = input.getObjectMetadata().get(EntityGroupName.class).iterator();

         while(var2.hasNext()) {
            EntityGroupName group = (EntityGroupName)var2.next();
            if (this.groupNames.contains(group.getName())) {
               return true;
            }
         }
      }

      return false;
   }
}
