package com.sun.faces.application.applicationimpl.events;

import com.sun.faces.util.Cache;

public class SystemEventHelper {
   private final Cache systemEventInfoCache = new Cache(new Cache.Factory() {
      public SystemEventInfo newInstance(Class arg) throws InterruptedException {
         return new SystemEventInfo(arg);
      }
   });

   public EventInfo getEventInfo(Class systemEventClass, Class sourceClass) {
      EventInfo info = null;
      SystemEventInfo systemEventInfo = (SystemEventInfo)this.systemEventInfoCache.get(systemEventClass);
      if (systemEventInfo != null) {
         info = systemEventInfo.getEventInfo(sourceClass);
      }

      return info;
   }

   public EventInfo getEventInfo(Class systemEventClass, Object source, Class sourceBaseType, boolean useSourceForLookup) {
      Class sourceClass = useSourceForLookup ? (sourceBaseType != null ? sourceBaseType : source.getClass()) : Void.class;
      return this.getEventInfo(systemEventClass, sourceClass);
   }
}
