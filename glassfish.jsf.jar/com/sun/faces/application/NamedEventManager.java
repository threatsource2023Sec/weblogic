package com.sun.faces.application;

import com.sun.faces.util.Util;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.faces.FacesException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PostRenderViewEvent;
import javax.faces.event.PostValidateEvent;
import javax.faces.event.PreRenderComponentEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.faces.event.PreValidateEvent;

public class NamedEventManager {
   private Map namedEvents = new ConcurrentHashMap();
   private Map duplicateNames = new ConcurrentHashMap();

   public NamedEventManager() {
      this.namedEvents.put("javax.faces.event.PreRenderComponent", PreRenderComponentEvent.class);
      this.namedEvents.put("javax.faces.event.PreRenderView", PreRenderViewEvent.class);
      this.namedEvents.put("javax.faces.event.PostRenderView", PostRenderViewEvent.class);
      this.namedEvents.put("javax.faces.event.PostAddToView", PostAddToViewEvent.class);
      this.namedEvents.put("javax.faces.event.PreValidate", PreValidateEvent.class);
      this.namedEvents.put("javax.faces.event.PostValidate", PostValidateEvent.class);
      this.namedEvents.put("preRenderComponent", PreRenderComponentEvent.class);
      this.namedEvents.put("preRenderView", PreRenderViewEvent.class);
      this.namedEvents.put("postRenderView", PostRenderViewEvent.class);
      this.namedEvents.put("postAddToView", PostAddToViewEvent.class);
      this.namedEvents.put("preValidate", PreValidateEvent.class);
      this.namedEvents.put("postValidate", PostValidateEvent.class);
   }

   public void addNamedEvent(String name, Class event) {
      this.namedEvents.put(name, event);
   }

   public Class getNamedEvent(String name) {
      Class namedEvent = (Class)this.namedEvents.get(name);
      if (namedEvent == null) {
         try {
            namedEvent = Util.loadClass(name, this);
         } catch (ClassNotFoundException var4) {
            throw new FacesException("An unknown event type was specified:  " + name, var4);
         }
      }

      if (!ComponentSystemEvent.class.isAssignableFrom(namedEvent)) {
         throw new ClassCastException();
      } else {
         return namedEvent;
      }
   }

   public void addDuplicateName(String name, Class event) {
      Class registeredEvent = (Class)this.namedEvents.remove(name);
      Set events = (Set)this.duplicateNames.get(name);
      if (events == null) {
         events = new HashSet();
         this.duplicateNames.put(name, events);
      }

      ((Set)events).add(event);
      if (registeredEvent != null) {
         ((Set)events).add(registeredEvent);
      }

   }

   public boolean isDuplicateNamedEvent(String name) {
      return this.namedEvents.get(name) != null || this.duplicateNames.get(name) != null;
   }
}
