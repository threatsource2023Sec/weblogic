package com.bea.core.repackaged.springframework.context.i18n;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.TimeZone;

public interface TimeZoneAwareLocaleContext extends LocaleContext {
   @Nullable
   TimeZone getTimeZone();
}
