package com.solarmetric.profile;

public class MethodEnterEvent extends ProfilingEvent {
   private MethodInfo _info;

   public MethodEnterEvent(ProfilingEnvironment pe, MethodInfo info) {
      super(pe);
      this._info = info;
   }

   public MethodInfo getMethodInfo() {
      return this._info;
   }
}
