package com.sun.faces.application.annotation;

import com.sun.faces.application.ApplicationAssociate;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.faces.application.Application;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

public class ComponentConfigHandler implements ConfigAnnotationHandler {
   private static final Collection HANDLES;
   private Map components;

   public Collection getHandledAnnotations() {
      return HANDLES;
   }

   public void collect(Class target, Annotation annotation) {
      if (this.components == null) {
         this.components = new HashMap();
      }

      String value = ((FacesComponent)annotation).value();
      if (null == value || 0 == value.length()) {
         value = target.getSimpleName();
         value = Character.toLowerCase(value.charAt(0)) + value.substring(1);
      }

      this.components.put(value, new FacesComponentUsage(target, (FacesComponent)annotation));
   }

   public void push(FacesContext ctx) {
      if (this.components != null) {
         Application app = ctx.getApplication();
         ApplicationAssociate appAss = ApplicationAssociate.getCurrentInstance();

         Map.Entry entry;
         for(Iterator var4 = this.components.entrySet().iterator(); var4.hasNext(); app.addComponent((String)entry.getKey(), ((FacesComponentUsage)entry.getValue()).getTarget().getName())) {
            entry = (Map.Entry)var4.next();
            if (((FacesComponentUsage)entry.getValue()).getAnnotation().createTag()) {
               appAss.addFacesComponent((FacesComponentUsage)entry.getValue());
            }
         }
      }

   }

   static {
      Collection handles = new ArrayList(1);
      handles.add(FacesComponent.class);
      HANDLES = Collections.unmodifiableCollection(handles);
   }
}
