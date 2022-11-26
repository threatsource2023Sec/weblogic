package net.shibboleth.utilities.java.support.component;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public interface IdentifiableComponent extends IdentifiedComponent {
   void setId(@Nonnull @NotEmpty String var1);
}
