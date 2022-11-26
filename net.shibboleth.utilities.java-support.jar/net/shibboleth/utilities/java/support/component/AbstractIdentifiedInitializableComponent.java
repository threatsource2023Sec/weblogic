package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public abstract class AbstractIdentifiedInitializableComponent extends AbstractInitializableComponent implements IdentifiedComponent {
   @Nullable
   @NonnullAfterInit
   private String id;

   @Nullable
   @NonnullAfterInit
   public String getId() {
      return this.id;
   }

   protected void setId(@Nonnull @NotEmpty String componentId) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.id = (String)Constraint.isNotNull(StringSupport.trimOrNull(componentId), "Component ID can not be null or empty");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.getId() == null) {
         throw new ComponentInitializationException("Component identifier can not be null");
      }
   }
}
