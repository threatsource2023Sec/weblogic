package net.shibboleth.utilities.java.support.primitive;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.IdentifiedComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;

public final class TimerSupport {
   private TimerSupport() {
   }

   @Nonnull
   @NotEmpty
   public static String getTimerName(@Nonnull Object obj) {
      return getTimerName((Object)obj, (String)null);
   }

   @Nonnull
   @NotEmpty
   public static String getTimerName(@Nonnull Object obj, @Nullable String additionalData) {
      Constraint.isNotNull(obj, "Target object for Timer was null");
      String baseName = null;
      if (obj instanceof IdentifiedComponent && StringSupport.trimOrNull(((IdentifiedComponent)obj).getId()) != null) {
         baseName = StringSupport.trimOrNull(((IdentifiedComponent)obj).getId());
      } else if (StringSupport.trimOrNull(obj.toString()) != null) {
         baseName = StringSupport.trimOrNull(obj.toString());
      } else {
         baseName = obj.getClass().getName();
      }

      return getTimerName(baseName, additionalData);
   }

   @Nonnull
   @NotEmpty
   public static String getTimerName(@Nonnull String baseName, @Nullable String additionalData) {
      Constraint.isNotNull(baseName, "Base name for Timer was null");
      return additionalData != null ? String.format("Timer for %s (%s)", baseName, additionalData) : String.format("Timer for %s", baseName);
   }
}
