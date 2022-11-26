package org.opensaml.saml.common.binding;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.profile.context.ProfileRequestContext;

public class BindingDescriptor extends AbstractIdentifiableInitializableComponent implements Predicate {
   @Nonnull
   private Predicate activationCondition = Predicates.alwaysTrue();
   private boolean synchronous = false;
   private boolean artifact = false;
   private boolean signatureCapable = false;
   @Nullable
   @NotEmpty
   private String shortName;

   public void setActivationCondition(@Nonnull Predicate condition) {
      this.activationCondition = (Predicate)Constraint.isNotNull(condition, "Activation condition predicate cannot be null");
   }

   public boolean isSynchronous() {
      return this.synchronous;
   }

   public void setSynchronous(boolean flag) {
      this.synchronous = flag;
   }

   public boolean isArtifact() {
      return this.artifact;
   }

   public void setArtifact(boolean flag) {
      this.artifact = flag;
   }

   public boolean isSignatureCapable() {
      return this.signatureCapable;
   }

   public void setSignatureCapable(boolean flag) {
      this.signatureCapable = flag;
   }

   @Nullable
   @NotEmpty
   public String getShortName() {
      return this.shortName;
   }

   public void setShortName(@Nullable @NotEmpty String name) {
      this.shortName = StringSupport.trimOrNull(name);
   }

   public boolean apply(@Nullable ProfileRequestContext input) {
      return this.activationCondition.apply(input);
   }

   public int hashCode() {
      return this.getId().hashCode();
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (obj == this) {
         return true;
      } else {
         return obj instanceof BindingDescriptor ? this.getId().equals(((BindingDescriptor)obj).getId()) : false;
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("bindingId", this.getId()).add("shortName", this.shortName).add("synchronous", this.synchronous).add("artifact", this.artifact).add("signatureCapable", this.signatureCapable).toString();
   }
}
