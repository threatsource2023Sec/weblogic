package org.hibernate.validator.internal.engine;

import java.time.Clock;
import javax.validation.ClockProvider;

public class DefaultClockProvider implements ClockProvider {
   public static final DefaultClockProvider INSTANCE = new DefaultClockProvider();

   private DefaultClockProvider() {
   }

   public Clock getClock() {
      return Clock.systemDefaultZone();
   }
}
