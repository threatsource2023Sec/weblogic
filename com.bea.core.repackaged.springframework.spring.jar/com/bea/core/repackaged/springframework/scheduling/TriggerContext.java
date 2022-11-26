package com.bea.core.repackaged.springframework.scheduling;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Date;

public interface TriggerContext {
   @Nullable
   Date lastScheduledExecutionTime();

   @Nullable
   Date lastActualExecutionTime();

   @Nullable
   Date lastCompletionTime();
}
