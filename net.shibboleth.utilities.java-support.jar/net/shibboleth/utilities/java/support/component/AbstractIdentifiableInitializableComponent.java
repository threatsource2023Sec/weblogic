package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public abstract class AbstractIdentifiableInitializableComponent extends AbstractIdentifiedInitializableComponent implements IdentifiableComponent {
   public void setId(@Nonnull @NotEmpty String componentId) {
      super.setId(componentId);
   }
}
