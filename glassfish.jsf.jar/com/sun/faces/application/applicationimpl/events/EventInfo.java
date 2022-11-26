package com.sun.faces.application.applicationimpl.events;

import com.sun.faces.util.FacesLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.event.SystemEvent;

public class EventInfo {
   private static final Logger LOGGER;
   private Class systemEvent;
   private Class sourceClass;
   private Set listeners;
   private Constructor eventConstructor;
   private Map constructorMap;

   public EventInfo(Class systemEvent, Class sourceClass) {
      this.systemEvent = systemEvent;
      this.sourceClass = sourceClass;
      this.listeners = new CopyOnWriteArraySet();
      this.constructorMap = new HashMap();
      if (!sourceClass.equals(Void.class)) {
         this.eventConstructor = this.getEventConstructor(sourceClass);
      }

   }

   public Set getListeners() {
      return this.listeners;
   }

   public SystemEvent createSystemEvent(Object source) {
      Constructor toInvoke = this.getCachedConstructor(source.getClass());
      if (toInvoke != null) {
         try {
            return (SystemEvent)toInvoke.newInstance(source);
         } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException var4) {
            throw new FacesException(var4);
         }
      } else {
         return null;
      }
   }

   private Constructor getCachedConstructor(Class source) {
      if (this.eventConstructor != null) {
         return this.eventConstructor;
      } else {
         Constructor c = (Constructor)this.constructorMap.get(source);
         if (c == null) {
            c = this.getEventConstructor(source);
            if (c != null) {
               this.constructorMap.put(source, c);
            }
         }

         return c;
      }
   }

   private Constructor getEventConstructor(Class source) {
      Constructor ctor = null;

      try {
         return this.systemEvent.getDeclaredConstructor(source);
      } catch (NoSuchMethodException var10) {
         Constructor[] ctors = this.systemEvent.getConstructors();
         if (ctors != null) {
            Constructor[] var5 = ctors;
            int var6 = ctors.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Constructor c = var5[var7];
               Class[] params = c.getParameterTypes();
               if (params.length == 1 && params[0].isAssignableFrom(source)) {
                  return c;
               }
            }
         }

         if (this.eventConstructor == null && LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Unable to find Constructor within {0} that accepts {1} instances.", new Object[]{this.systemEvent.getName(), this.sourceClass.getName()});
         }

         return (Constructor)ctor;
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
