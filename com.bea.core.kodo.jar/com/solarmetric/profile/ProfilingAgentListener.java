package com.solarmetric.profile;

import java.util.EventListener;

public interface ProfilingAgentListener extends EventListener {
   void rootAdded(ProfilingAgentEvent var1);

   void nodeAdded(ProfilingAgentEvent var1);
}
