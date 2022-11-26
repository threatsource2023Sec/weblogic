package com.sun.faces.application.applicationimpl.events;

import com.sun.faces.util.Cache;

public class ComponentSystemEventHelper {
   private Cache sourceCache;

   public ComponentSystemEventHelper() {
      Cache.Factory eventCacheFactory = new Cache.Factory() {
         public Cache newInstance(final Class sourceClass) throws InterruptedException {
            Cache.Factory eventInfoFactory = new Cache.Factory() {
               public EventInfo newInstance(Class systemEventClass) throws InterruptedException {
                  return new EventInfo(systemEventClass, sourceClass);
               }
            };
            return new Cache(eventInfoFactory);
         }
      };
      this.sourceCache = new Cache(eventCacheFactory);
   }

   public EventInfo getEventInfo(Class systemEvent, Class sourceClass) {
      Cache eventsCache = (Cache)this.sourceCache.get(sourceClass);
      return (EventInfo)eventsCache.get(systemEvent);
   }
}
