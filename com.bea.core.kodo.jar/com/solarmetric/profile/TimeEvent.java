package com.solarmetric.profile;

public class TimeEvent extends ProfilingEvent {
   private EventInfo _info;
   private long _time;

   public TimeEvent(ProfilingEnvironment pe, long time, EventInfo info) {
      super(pe);
      this._info = info;
      this._time = time;
   }

   public EventInfo getEventInfo() {
      return this._info;
   }

   public long getTime() {
      return this._time;
   }
}
