package com.sun.faces.facelets.tag.composite;

import javax.faces.application.Resource;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.view.Location;

abstract class RelocateListener implements ComponentSystemEventListener, StateHolder {
   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         return null;
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      }
   }

   public boolean isTransient() {
      return true;
   }

   public void setTransient(boolean newTransientValue) {
   }

   protected Resource getBackingResource(UIComponent component) {
      assert UIComponent.isCompositeComponent(component);

      Resource resource = (Resource)component.getAttributes().get("javax.faces.application.Resource.ComponentResource");
      if (resource == null) {
         throw new IllegalStateException("Backing resource information not found in composite component attribute map");
      } else {
         return resource;
      }
   }

   protected boolean resourcesMatch(Resource compositeResource, Location handlerLocation) {
      String resName = compositeResource.getResourceName();
      return handlerLocation.getPath().contains(resName);
   }
}
