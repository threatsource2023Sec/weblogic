package org.opensaml.saml.criterion;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class BindingCriterion implements Criterion {
   @Nonnull
   @NonnullElements
   private final List bindings;

   public BindingCriterion(@Nonnull @NonnullElements List bindingURIs) {
      Constraint.isNotNull(bindingURIs, "Binding list cannot be null");
      this.bindings = new ArrayList(bindingURIs.size());
      Iterator var2 = bindingURIs.iterator();

      while(var2.hasNext()) {
         String binding = (String)var2.next();
         String trimmed = StringSupport.trimOrNull(binding);
         if (trimmed != null) {
            this.bindings.add(trimmed);
         }
      }

   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getBindings() {
      return ImmutableList.copyOf(this.bindings);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("BindingCriterion [bindings=");
      builder.append(this.bindings);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.bindings.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof BindingCriterion ? this.bindings.equals(((BindingCriterion)obj).bindings) : false;
      }
   }
}
