package com.sun.faces.application.applicationimpl.events;

import com.sun.faces.util.Cache;
import com.sun.faces.util.Util;

public class SystemEventInfo {
   private Cache cache = new Cache(new Cache.Factory() {
      public EventInfo newInstance(Class arg) throws InterruptedException {
         return new EventInfo(SystemEventInfo.this.systemEvent, arg);
      }
   });
   private Class systemEvent;

   public SystemEventInfo(Class systemEvent) {
      this.systemEvent = systemEvent;
   }

   public EventInfo getEventInfo(Class source) {
      return (EventInfo)this.cache.get(Util.coalesce(source, Void.class));
   }
}
