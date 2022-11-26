package com.oracle.cmm.lowertier.jfr;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.ValueDefinition;

@EventDefinition(
   name = "Resource Pressure Evaluation",
   description = "Resource pressure evaluation details",
   path = "oracle/cmm/lowertier/Resource_Pressure_Evaluation",
   thread = false,
   stacktrace = false
)
public class ResourcePressureEvaluationEvent extends InstantEvent {
   @ValueDefinition(
      name = "MemoryPressureBefore",
      description = "The memory pressure before evaluation"
   )
   public int memoryPressureBefore;
   @ValueDefinition(
      name = "EvaluatedMemoryPressure",
      description = "The current evaluation of the memory pressure"
   )
   public int evaluatedMemoryPressure;
   @ValueDefinition(
      name = "AdjustedMemoryPressure",
      description = "The adjusted memory pressure, this may have been adjusted and not match the current evaluated pressure to prevent thrashing"
   )
   public int adjustedMemoryPressure;
   @ValueDefinition(
      name = "TimerInterval",
      description = "The timer interval in milliseconds"
   )
   public long timerInterval;
}
