package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.component.IdentifiedComponent;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.component.UnmodifiableComponent;

@ThreadSafe
public interface AccessControlService extends InitializableComponent, IdentifiedComponent, UnmodifiableComponent {
   @Nonnull
   AccessControl getInstance(@Nonnull String var1);
}
