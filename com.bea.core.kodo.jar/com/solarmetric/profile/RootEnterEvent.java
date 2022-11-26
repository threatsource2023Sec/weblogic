package com.solarmetric.profile;

public class RootEnterEvent extends ProfilingEvent {
   private RootInfo _info;

   public RootEnterEvent(ProfilingEnvironment pe, RootInfo info) {
      super(pe);
      this._info = info;
   }

   public RootInfo getRootInfo() {
      return this._info;
   }
}
