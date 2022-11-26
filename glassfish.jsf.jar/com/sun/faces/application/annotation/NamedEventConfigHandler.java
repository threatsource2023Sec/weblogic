package com.sun.faces.application.annotation;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.NamedEventManager;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.NamedEvent;

public class NamedEventConfigHandler implements ConfigAnnotationHandler {
   private Map namedEvents;
   private static final Collection HANDLES;

   public Collection getHandledAnnotations() {
      return HANDLES;
   }

   public void collect(Class target, Annotation annotation) {
      if (this.namedEvents == null) {
         this.namedEvents = new HashMap();
      }

      this.namedEvents.put(target, annotation);
   }

   public void push(FacesContext ctx) {
      if (this.namedEvents != null) {
         ApplicationAssociate associate = ApplicationAssociate.getInstance(ctx.getExternalContext());
         if (associate != null) {
            NamedEventManager nem = associate.getNamedEventManager();
            Iterator var4 = this.namedEvents.entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry entry = (Map.Entry)var4.next();
               this.process(nem, (Class)entry.getKey(), (Annotation)entry.getValue());
            }
         }
      }

   }

   private void process(NamedEventManager nem, Class annotatedClass, Annotation annotation) {
      String name = annotatedClass.getSimpleName();
      int index = name.lastIndexOf("Event");
      if (index > -1) {
         name = name.substring(0, index);
      }

      name = annotatedClass.getPackage().getName() + ("." + name.charAt(0)).toLowerCase() + name.substring(1);
      nem.addNamedEvent(name, annotatedClass);
      String shortName = ((NamedEvent)annotation).shortName();
      if (!"".equals(shortName)) {
         if (nem.isDuplicateNamedEvent(shortName)) {
            nem.addDuplicateName(shortName, annotatedClass);
         } else {
            nem.addNamedEvent(shortName, annotatedClass);
         }
      }

   }

   static {
      Collection handles = new ArrayList(2);
      handles.add(NamedEvent.class);
      HANDLES = Collections.unmodifiableCollection(handles);
   }
}
