package com.bea.core.repackaged.springframework.scheduling;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Date;

public interface Trigger {
   @Nullable
   Date nextExecutionTime(TriggerContext var1);
}
