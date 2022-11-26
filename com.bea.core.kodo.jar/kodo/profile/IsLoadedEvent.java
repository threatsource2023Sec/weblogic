package kodo.profile;

import com.solarmetric.profile.ProfilingEnvironment;
import com.solarmetric.profile.ProfilingEvent;

public class IsLoadedEvent extends ProfilingEvent {
   private IsLoadedInfo _info;

   public IsLoadedEvent(ProfilingEnvironment pe, IsLoadedInfo info) {
      super(pe);
      this._info = info;
   }

   public IsLoadedInfo getIsLoadedInfo() {
      return this._info;
   }

   public String toString() {
      return "IsLoaded: " + this._info.toString();
   }
}
