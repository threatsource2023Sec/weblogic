package com.solarmetric.profile;

public class MethodExitEvent extends ProfilingEvent {
   private MethodInfo _info;

   public MethodExitEvent(ProfilingEnvironment pe, MethodInfo info) {
      super(pe);
      this._info = info;
   }

   public MethodInfo getMethodInfo() {
      return this._info;
   }
}
