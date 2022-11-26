package com.sun.faces.application.annotation;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.SystemEventListener;

class ListenerForHandler implements RuntimeAnnotationHandler {
   private ListenerFor[] listenersFor;

   public ListenerForHandler(ListenerFor[] listenersFor) {
      this.listenersFor = listenersFor;
   }

   public void apply(FacesContext ctx, Object... params) {
      Object listener;
      UIComponent target;
      if (params.length == 2) {
         listener = params[0];
         target = (UIComponent)params[1];
      } else {
         listener = params[0];
         target = (UIComponent)params[0];
      }

      if (listener instanceof ComponentSystemEventListener) {
         int i = 0;

         for(int len = this.listenersFor.length; i < len; ++i) {
            target.subscribeToEvent(this.listenersFor[i].systemEventClass(), (ComponentSystemEventListener)listener);
         }
      } else if (listener instanceof SystemEventListener) {
         Class sourceClassValue = null;
         Application app = ctx.getApplication();
         int i = 0;

         for(int len = this.listenersFor.length; i < len; ++i) {
            sourceClassValue = this.listenersFor[i].sourceClass();
            if (sourceClassValue == Void.class) {
               app.subscribeToEvent(this.listenersFor[i].systemEventClass(), (SystemEventListener)listener);
            } else {
               app.subscribeToEvent(this.listenersFor[i].systemEventClass(), this.listenersFor[i].sourceClass(), (SystemEventListener)listener);
            }
         }
      }

   }
}
