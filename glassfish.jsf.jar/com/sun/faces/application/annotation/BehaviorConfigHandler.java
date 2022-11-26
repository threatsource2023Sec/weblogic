package com.sun.faces.application.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.faces.application.Application;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.context.FacesContext;

public class BehaviorConfigHandler implements ConfigAnnotationHandler {
   private static final Collection HANDLES;
   private Map behaviors;

   public Collection getHandledAnnotations() {
      return HANDLES;
   }

   public void collect(Class target, Annotation annotation) {
      if (this.behaviors == null) {
         this.behaviors = new HashMap();
      }

      this.behaviors.put(((FacesBehavior)annotation).value(), target.getName());
   }

   public void push(FacesContext ctx) {
      if (this.behaviors != null) {
         Application app = ctx.getApplication();
         Iterator var3 = this.behaviors.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            app.addBehavior((String)entry.getKey(), (String)entry.getValue());
         }
      }

   }

   static {
      Collection handles = new ArrayList(1);
      handles.add(FacesBehavior.class);
      HANDLES = Collections.unmodifiableCollection(handles);
   }
}
