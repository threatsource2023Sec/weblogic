package weblogic.management.scripting;

import javax.management.ObjectName;

class WLSTWatchListener {
   ObjectName objName;
   WatchListener wl;
   String watchName;
   String description;

   public WLSTWatchListener(ObjectName on, WatchListener wlis, String watchName, String description) {
      this.objName = on;
      this.wl = wlis;
      this.watchName = watchName;
      this.description = description;
   }

   ObjectName getObjectName() {
      return this.objName;
   }

   WatchListener getWL() {
      return this.wl;
   }

   String getWN() {
      return this.watchName;
   }

   String getDescription() {
      return this.description;
   }
}
