package com.bea.adaptive.mbean.typing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;

public final class MBeanRegistrationQueue {
   private static final int DEFAULT_CAPACITY = 256;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugMBeanTypeUtilQueue");
   private static DebugLogger debugPriorityLogger = DebugLogger.getDebugLogger("DebugMBeanTypeUtilQueuePriority");
   private static final String LO_PRIORITY = "LO";
   private static final String HI_PRIORITY = "HI";
   private HashMap highPriorityQueue;
   private HashMap lowPriorityQueue;
   private ArrayList objectNamePatterns;
   private ArrayList regexPatterns;
   private String name;
   private String msgPrefix;
   private long totalTransientsHandled;
   private long totalEventsReceived;

   public MBeanRegistrationQueue(String queueName, String[] patterns) {
      this.name = queueName;
      this.msgPrefix = this.name == null ? "" : this.name + ": ";
      if (patterns != null) {
         this.buildPatternSets(patterns);
      }

   }

   public void enqueue(ObjectName name, String eventType) {
      this.enqueueEvent(name, InstanceRegistrationEvent.EventType.createEventType(eventType));
   }

   public void enqueue(ObjectName name, InstanceRegistrationEvent.EventType eventType) {
      this.enqueueEvent(name, eventType);
   }

   public void enqueueNewInstance(ObjectName name) {
      this.enqueueEvent(name, InstanceRegistrationEvent.EventType.ADD);
   }

   public void enqueueDeleteInstance(ObjectName name) {
      this.enqueueEvent(name, InstanceRegistrationEvent.EventType.DELETE);
   }

   public void addAll(Set onSet, String eventType) {
      Iterator var3 = onSet.iterator();

      while(var3.hasNext()) {
         ObjectName oname = (ObjectName)var3.next();
         this.enqueue(oname, eventType);
      }

   }

   public int size() {
      int hiSize = this.highPriorityQueue == null ? 0 : this.highPriorityQueue.size();
      int loSize = this.lowPriorityQueue == null ? 0 : this.lowPriorityQueue.size();
      return hiSize + loSize;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public void clear() {
      if (this.highPriorityQueue != null) {
         this.highPriorityQueue.clear();
      }

      if (this.lowPriorityQueue != null) {
         this.lowPriorityQueue.clear();
      }

   }

   public InstanceRegistrationEvent dequeue() {
      InstanceRegistrationEvent event = null;
      if (this.highPriorityQueue != null && this.highPriorityQueue.size() > 0) {
         event = this.dequeueFrom("HI", this.highPriorityQueue);
      } else if (this.lowPriorityQueue != null && this.lowPriorityQueue.size() > 0) {
         event = this.dequeueFrom("LO", this.lowPriorityQueue);
      }

      return event;
   }

   public long getTotalTransientsHandled() {
      return this.totalTransientsHandled;
   }

   public long getTotalEventsReceived() {
      return this.totalEventsReceived;
   }

   private InstanceRegistrationEvent dequeueFrom(String priority, HashMap queue) {
      Iterator it = queue.values().iterator();
      InstanceRegistrationEvent event = (InstanceRegistrationEvent)it.next();
      it.remove();
      if (debugPriorityLogger.isDebugEnabled()) {
         this.debugPriority(priority + " - dequeue " + event.getEvent() + ":" + event.getObjectName());
      }

      return event;
   }

   private boolean isHighPriority(ObjectName name) {
      return this.objectNamePatterns != null && this.matchesObjectNamePattern(name) || this.regexPatterns != null && this.matchesRegexPattern(name);
   }

   private boolean matchesRegexPattern(ObjectName name) {
      Iterator var2 = this.regexPatterns.iterator();

      Pattern p;
      Matcher m;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         p = (Pattern)var2.next();
         m = p.matcher(name.getCanonicalName());
      } while(!m.matches());

      if (debugLogger.isDebugEnabled()) {
         this.debug(name.getCanonicalName() + " matches regex pattern " + p.pattern());
      }

      return true;
   }

   private boolean matchesObjectNamePattern(ObjectName name) {
      Iterator var2 = this.objectNamePatterns.iterator();

      ObjectName onPattern;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         onPattern = (ObjectName)var2.next();
      } while((!onPattern.isPattern() || !onPattern.apply(name)) && !onPattern.equals(name));

      if (debugLogger.isDebugEnabled()) {
         this.debug(name.getCanonicalName() + " matches ObjectName pattern " + onPattern.getCanonicalName());
      }

      return true;
   }

   private void buildPatternSets(String[] patterns) {
      String[] var2 = patterns;
      int var3 = patterns.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String pattern = var2[var4];
         if (pattern != null) {
            try {
               this.addPattern(pattern);
            } catch (Exception var7) {
               throw new RuntimeException(var7);
            }
         }
      }

      if (debugLogger.isDebugEnabled()) {
         if (this.objectNamePatterns != null && this.objectNamePatterns.size() > 0) {
            this.debug("ObjectName patterns: " + this.objectNamePatterns.toString());
         } else {
            this.debug("No ObjectName patterns configured");
         }

         if (this.regexPatterns != null && this.regexPatterns.size() > 0) {
            this.debug("Regex patterns: " + this.regexPatterns.toString());
         } else {
            this.debug("No regex patterns configured");
         }
      }

   }

   private void addPattern(String pattern) throws Exception {
      try {
         ObjectName onPattern = new ObjectName(pattern);
         if (this.objectNamePatterns == null) {
            this.objectNamePatterns = new ArrayList(8);
         }

         this.objectNamePatterns.add(onPattern);
      } catch (MalformedObjectNameException var3) {
         Pattern p = Pattern.compile(pattern);
         if (this.regexPatterns == null) {
            this.regexPatterns = new ArrayList(8);
         }

         this.regexPatterns.add(p);
      }
   }

   private String getMessagePrefix() {
      return this.msgPrefix;
   }

   private void debug(String msg) {
      debugLogger.debug(this.getMessagePrefix() + msg);
   }

   private void debugPriority(String msg) {
      debugPriorityLogger.debug(this.getMessagePrefix() + msg);
   }

   private void enqueueEvent(ObjectName instance, InstanceRegistrationEvent.EventType type) {
      if (this.isHighPriority(instance)) {
         if (this.highPriorityQueue == null) {
            this.highPriorityQueue = new HashMap(64);
         }

         this.enqueueEvent("HI", instance, type, this.highPriorityQueue);
      } else {
         if (this.lowPriorityQueue == null) {
            this.lowPriorityQueue = new HashMap(256);
         }

         this.enqueueEvent("LO", instance, type, this.lowPriorityQueue);
      }

   }

   private void enqueueEvent(String queueType, ObjectName name, InstanceRegistrationEvent.EventType newEventType, HashMap eventSet) {
      ++this.totalEventsReceived;
      InstanceRegistrationEvent removedEvent = (InstanceRegistrationEvent)eventSet.remove(name);
      boolean removedTransientInstance = removedEvent == null ? false : newEventType == InstanceRegistrationEvent.EventType.DELETE && removedEvent.isAdd();
      if (!removedTransientInstance) {
         if (debugPriorityLogger.isDebugEnabled()) {
            this.debugPriority(queueType + " - ENQUEUE event for " + name + ": " + newEventType);
         }

         eventSet.put(name, new InstanceRegistrationEvent(name, newEventType));
      } else {
         ++this.totalTransientsHandled;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Removed entry for transient instance: " + removedEvent.getEvent() + " event for object " + removedEvent.getObjectName());
         }
      }

   }
}
