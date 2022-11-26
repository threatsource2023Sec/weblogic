package com.sun.faces.application.applicationimpl;

import com.sun.faces.application.applicationimpl.events.ComponentSystemEventHelper;
import com.sun.faces.application.applicationimpl.events.EventInfo;
import com.sun.faces.application.applicationimpl.events.ReentrantLisneterInvocationGuard;
import com.sun.faces.application.applicationimpl.events.SystemEventHelper;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.event.SystemEventListenerHolder;

public class Events {
   private static final Logger LOGGER;
   private static final String CONTEXT = "context";
   private static final String LISTENER = "listener";
   private static final String SOURCE = "source";
   private static final String SYSTEM_EVENT_CLASS = "systemEventClass";
   private final SystemEventHelper systemEventHelper = new SystemEventHelper();
   private final ComponentSystemEventHelper compSysEventHelper = new ComponentSystemEventHelper();
   private ReentrantLisneterInvocationGuard listenerInvocationGuard = new ReentrantLisneterInvocationGuard();

   public void publishEvent(FacesContext context, Class systemEventClass, Object source, ProjectStage projectStage) {
      this.publishEvent(context, systemEventClass, (Class)null, source, projectStage);
   }

   public void publishEvent(FacesContext context, Class systemEventClass, Class sourceBaseType, Object source, ProjectStage projectStage) {
      Util.notNull("context", context);
      Util.notNull("systemEventClass", systemEventClass);
      Util.notNull("source", source);
      if (this.needsProcessing(context, systemEventClass)) {
         if (projectStage == ProjectStage.Development && sourceBaseType != null && !sourceBaseType.isInstance(source)) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "jsf.application.publish.event.base_type_mismatch", new Object[]{source.getClass().getName(), sourceBaseType.getName()});
            }

         } else {
            try {
               SystemEvent event = this.invokeComponentListenersFor(systemEventClass, source);
               event = this.invokeViewListenersFor(context, systemEventClass, event, source);
               event = this.invokeListenersFor(systemEventClass, event, source, sourceBaseType, true);
               this.invokeListenersFor(systemEventClass, event, source, (Class)null, false);
            } catch (AbortProcessingException var7) {
               context.getApplication().publishEvent(context, ExceptionQueuedEvent.class, new ExceptionQueuedEventContext(context, var7));
            }

         }
      }
   }

   public void subscribeToEvent(Class systemEventClass, SystemEventListener listener) {
      this.subscribeToEvent(systemEventClass, (Class)null, listener);
   }

   public void subscribeToEvent(Class systemEventClass, Class sourceClass, SystemEventListener listener) {
      Util.notNull("systemEventClass", systemEventClass);
      Util.notNull("listener", listener);
      this.getListeners(systemEventClass, sourceClass).add(listener);
   }

   public void unsubscribeFromEvent(Class systemEventClass, Class sourceClass, SystemEventListener listener) {
      Util.notNull("systemEventClass", systemEventClass);
      Util.notNull("listener", listener);
      Set listeners = this.getListeners(systemEventClass, sourceClass);
      if (listeners != null) {
         listeners.remove(listener);
      }

   }

   private Set getListeners(Class systemEvent, Class sourceClass) {
      Set listeners = null;
      EventInfo sourceInfo = this.systemEventHelper.getEventInfo(systemEvent, sourceClass);
      if (sourceInfo != null) {
         listeners = sourceInfo.getListeners();
      }

      return listeners;
   }

   private boolean needsProcessing(FacesContext context, Class systemEventClass) {
      return context.isProcessingEvents() || ExceptionQueuedEvent.class.isAssignableFrom(systemEventClass);
   }

   private SystemEvent invokeComponentListenersFor(Class systemEventClass, Object source) {
      if (source instanceof SystemEventListenerHolder) {
         List listeners = ((SystemEventListenerHolder)source).getListenersForEventClass(systemEventClass);
         if (null == listeners) {
            return null;
         } else {
            EventInfo eventInfo = this.compSysEventHelper.getEventInfo(systemEventClass, source.getClass());
            return this.processListeners(listeners, (SystemEvent)null, source, eventInfo);
         }
      } else {
         return null;
      }
   }

   private SystemEvent invokeViewListenersFor(FacesContext ctx, Class systemEventClass, SystemEvent event, Object source) {
      SystemEvent result = event;
      if (this.listenerInvocationGuard.isGuardSet(ctx, systemEventClass)) {
         return event;
      } else {
         this.listenerInvocationGuard.setGuard(ctx, systemEventClass);
         UIViewRoot root = ctx.getViewRoot();

         try {
            if (root != null) {
               List listeners = root.getViewListenersForEventClass(systemEventClass);
               EventInfo rootEventInfo;
               if (null == listeners) {
                  rootEventInfo = null;
                  return rootEventInfo;
               }

               rootEventInfo = this.systemEventHelper.getEventInfo(systemEventClass, UIViewRoot.class);
               result = this.processListenersAccountingForAdds(listeners, event, source, rootEventInfo);
            }
         } finally {
            this.listenerInvocationGuard.clearGuard(ctx, systemEventClass);
         }

         return result;
      }
   }

   private SystemEvent invokeListenersFor(Class systemEventClass, SystemEvent event, Object source, Class sourceBaseType, boolean useSourceLookup) throws AbortProcessingException {
      EventInfo eventInfo = this.systemEventHelper.getEventInfo(systemEventClass, source, sourceBaseType, useSourceLookup);
      if (eventInfo != null) {
         Set listeners = eventInfo.getListeners();
         event = this.processListeners(listeners, event, source, eventInfo);
      }

      return event;
   }

   private SystemEvent processListeners(Collection listeners, SystemEvent event, Object source, EventInfo eventInfo) {
      if (listeners != null && !listeners.isEmpty()) {
         ArrayList list = new ArrayList(listeners);
         Iterator var6 = list.iterator();

         while(var6.hasNext()) {
            SystemEventListener curListener = (SystemEventListener)var6.next();
            if (curListener != null && curListener.isListenerForSource(source)) {
               if (event == null) {
                  event = eventInfo.createSystemEvent(source);
               }

               assert event != null;

               if (event.isAppropriateListener(curListener)) {
                  event.processListener(curListener);
               }
            }
         }
      }

      return event;
   }

   private SystemEvent processListenersAccountingForAdds(List listeners, SystemEvent event, Object source, EventInfo eventInfo) {
      if (listeners != null && !listeners.isEmpty()) {
         SystemEventListener[] listenersCopy = new SystemEventListener[listeners.size()];
         int i = false;

         int i;
         for(i = 0; i < listenersCopy.length; ++i) {
            listenersCopy[i] = (SystemEventListener)listeners.get(i);
         }

         Map processedListeners = new HashMap(listeners.size());
         boolean processedSomeEvents = false;
         boolean originalDiffersFromCopy = false;

         do {
            i = false;
            originalDiffersFromCopy = false;
            if (0 < listenersCopy.length) {
               for(i = 0; i < listenersCopy.length; ++i) {
                  SystemEventListener curListener = listenersCopy[i];
                  if (curListener != null && curListener.isListenerForSource(source)) {
                     if (event == null) {
                        event = eventInfo.createSystemEvent(source);
                     }

                     assert event != null;

                     if (!processedListeners.containsKey(curListener) && event.isAppropriateListener(curListener)) {
                        processedSomeEvents = true;
                        event.processListener(curListener);
                        processedListeners.put(curListener, Boolean.TRUE);
                     }
                  }
               }

               if (this.originalDiffersFromCopy(listeners, listenersCopy)) {
                  originalDiffersFromCopy = true;
                  listenersCopy = this.copyListWithExclusions(listeners, processedListeners);
               }
            }
         } while(originalDiffersFromCopy && processedSomeEvents);
      }

      return event;
   }

   private boolean originalDiffersFromCopy(Collection original, SystemEventListener[] copy) {
      boolean foundDifference = false;
      int i = 0;
      int originalLen = original.size();
      int copyLen = copy.length;
      SystemEventListener originalItem;
      SystemEventListener copyItem;
      if (originalLen == copyLen) {
         for(Iterator iter = original.iterator(); iter.hasNext() && !foundDifference; foundDifference = originalItem != copyItem) {
            originalItem = (SystemEventListener)iter.next();
            copyItem = copy[i++];
         }
      } else {
         foundDifference = true;
      }

      return foundDifference;
   }

   private SystemEventListener[] copyListWithExclusions(Collection original, Map excludes) {
      SystemEventListener[] result = null;
      SystemEventListener[] temp = new SystemEventListener[original.size()];
      int i = 0;
      Iterator var6 = original.iterator();

      while(var6.hasNext()) {
         SystemEventListener cur = (SystemEventListener)var6.next();
         if (!excludes.containsKey(cur)) {
            temp[i++] = cur;
         }
      }

      result = new SystemEventListener[i];
      System.arraycopy(temp, 0, result, 0, i);
      return result;
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
