package org.python.tests;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.TooManyListenersException;

public class Listenable {
   ComponentListener listener;

   public void addComponentListener(ComponentListener l) throws TooManyListenersException {
      if (this.listener != null) {
         throw new TooManyListenersException();
      } else {
         this.listener = l;
      }
   }

   public void removeComponentListener(ComponentListener l) {
      this.listener = null;
   }

   public void fireComponentMoved(ComponentEvent evt) {
      if (this.listener != null) {
         this.listener.componentMoved(evt);
      }

   }

   public void fireComponentHidden(ComponentEvent evt) {
      if (this.listener != null) {
         this.listener.componentHidden(evt);
      }

   }

   public void fireComponentShown(ComponentEvent evt) {
      if (this.listener != null) {
         this.listener.componentShown(evt);
      }

   }
}
