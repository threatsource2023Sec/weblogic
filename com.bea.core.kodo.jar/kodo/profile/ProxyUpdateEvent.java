package kodo.profile;

import com.solarmetric.profile.ProfilingEnvironment;
import com.solarmetric.profile.ProfilingEvent;

public class ProxyUpdateEvent extends ProfilingEvent {
   private ProxyUpdateInfo _info;

   public ProxyUpdateEvent(ProfilingEnvironment pe, ProxyUpdateInfo info) {
      super(pe);
      this._info = info;
   }

   public ProxyUpdateInfo getProxyUpdateInfo() {
      return this._info;
   }

   public String toString() {
      return "ProxyUpdate: " + this._info.toString();
   }
}
