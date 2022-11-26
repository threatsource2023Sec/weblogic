package com.oracle.jrockit.jfr;

import java.net.URI;
import oracle.jrockit.jfr.events.EventHandler;

public class EventToken implements EventInfo {
   private final EventHandler eventInfo;
   private final RequestDelegate requestDelegate;

   EventToken(EventHandler eventInfo) {
      this(eventInfo, (RequestDelegate)null);
   }

   EventToken(EventHandler eventInfo, RequestDelegate requestDelegate) {
      this.eventInfo = eventInfo;
      this.requestDelegate = requestDelegate;
   }

   EventHandler getEventInfo() {
      return this.eventInfo;
   }

   RequestDelegate getRequestDelegate() {
      return this.requestDelegate;
   }

   Object receiverFor(InstantEvent event) {
      return event;
   }

   public int getId() {
      return this.eventInfo.getId();
   }

   public URI getURI() {
      return this.eventInfo.getURI();
   }

   public String getPath() {
      return this.eventInfo.getPath();
   }

   public boolean hasThread() {
      return this.eventInfo.hasThread();
   }

   public String getName() {
      return this.eventInfo.getName();
   }

   public String getDescription() {
      return this.eventInfo.getDescription();
   }

   public long getThreshold() {
      return this.eventInfo.getThreshold();
   }

   public boolean isEnabled() {
      return this.eventInfo.isEnabled();
   }

   public boolean isRequestable() {
      return this.eventInfo.isRequestable();
   }

   public boolean hasStackTrace() {
      return this.eventInfo.hasStackTrace();
   }

   public boolean isStackTraceEnabled() {
      return this.eventInfo.isStackTraceEnabled();
   }

   public boolean isTimed() {
      return this.eventInfo.isTimed();
   }

   public boolean hasStartTime() {
      return this.eventInfo.hasStartTime();
   }

   public long getPeriod() {
      return this.eventInfo.getPeriod();
   }

   public boolean equals(Object obj) {
      if (obj instanceof EventToken) {
         return this.eventInfo == ((EventToken)obj).eventInfo;
      } else {
         return false;
      }
   }

   public String toString() {
      return this.eventInfo.toString();
   }
}
