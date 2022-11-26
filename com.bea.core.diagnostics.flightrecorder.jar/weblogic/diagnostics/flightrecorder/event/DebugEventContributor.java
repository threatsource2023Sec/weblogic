package weblogic.diagnostics.flightrecorder.event;

import weblogic.diagnostics.flightrecorder.FlightRecorderDebugEvent;

public interface DebugEventContributor {
   void contribute(FlightRecorderDebugEvent var1);

   void contributeBefore(FlightRecorderDebugEvent var1);

   void contributeAfter(FlightRecorderDebugEvent var1);
}
