package com.oracle.cmm.lowertier.jfr;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.ValueDefinition;

@EventDefinition(
   name = "Resource Pressure Change",
   description = "Resource pressure change detected",
   path = "oracle/cmm/lowertier/Resource_Pressure_Change",
   thread = false,
   stacktrace = false
)
public class ResourcePressureChangeEvent extends InstantEvent {
   @ValueDefinition(
      name = "EvaluatedMemoryPressure",
      description = "The current evaluation of the memory pressure"
   )
   public int evaluatedMemoryPressure;
   @ValueDefinition(
      name = "CurrentMemoryPressure",
      description = "The current memory pressure being set, this may have been adjusted and not match the current evaluated pressure to prevent thrashing"
   )
   public int currentMemoryPressure;
   @ValueDefinition(
      name = "PreviousMemoryPressure",
      description = "The previous memory pressure"
   )
   public int previousMemoryPressure;
   @ValueDefinition(
      name = "IntervalsSinceLastChange",
      description = "The number of intervals since the pressure was last changed"
   )
   public long intervalsSinceLastChange;
   @ValueDefinition(
      name = "TimerInterval",
      description = "The timer interval in milliseconds"
   )
   public long timerInterval;
}
