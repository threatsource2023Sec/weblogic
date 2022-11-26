package com.solarmetric.profile;

public class RootExitEvent extends ProfilingEvent {
   private RootInfo _info;

   public RootExitEvent(ProfilingEnvironment pe, RootInfo info) {
      super(pe);
      this._info = info;
   }

   public RootInfo getRootInfo() {
      return this._info;
   }
}
