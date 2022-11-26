package net.shibboleth.utilities.java.support.service;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import org.joda.time.DateTime;

public interface ReloadableService extends InitializableComponent {
   @Nullable
   DateTime getLastSuccessfulReloadInstant();

   @Nullable
   DateTime getLastReloadAttemptInstant();

   @Nullable
   Throwable getReloadFailureCause();

   void reload();

   @Nullable
   ServiceableComponent getServiceableComponent();
}
