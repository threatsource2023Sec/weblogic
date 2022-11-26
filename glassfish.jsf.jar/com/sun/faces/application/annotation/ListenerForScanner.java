package com.sun.faces.application.annotation;

import com.sun.faces.util.Util;
import javax.faces.event.ListenerFor;
import javax.faces.event.ListenersFor;

class ListenerForScanner implements Scanner {
   public Class getAnnotation() {
      return ListenerFor.class;
   }

   public RuntimeAnnotationHandler scan(Class clazz) {
      Util.notNull("clazz", clazz);
      ListenerForHandler handler = null;
      ListenerFor listenerFor = (ListenerFor)clazz.getAnnotation(ListenerFor.class);
      if (listenerFor != null) {
         handler = new ListenerForHandler(new ListenerFor[]{listenerFor});
      } else {
         ListenersFor listenersFor = (ListenersFor)clazz.getAnnotation(ListenersFor.class);
         if (listenersFor != null) {
            handler = new ListenerForHandler(listenersFor.value());
         }
      }

      return handler;
   }
}
