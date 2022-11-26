package kodo.profile;

import com.solarmetric.profile.ProfilingEnvironment;
import com.solarmetric.profile.ProfilingEvent;

public class InitialLoadEvent extends ProfilingEvent {
   private InitialLoadInfo _info;

   public InitialLoadEvent(ProfilingEnvironment pe, InitialLoadInfo info) {
      super(pe);
      this._info = info;
   }

   public InitialLoadInfo getInitialLoadInfo() {
      return this._info;
   }
}
