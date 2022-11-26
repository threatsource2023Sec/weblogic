package com.sun.faces.facelets.tag.jsf.core;

import java.io.Serializable;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodNotFoundException;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;

class DeclarativeSystemEventListener implements ComponentSystemEventListener, Serializable {
   private static final long serialVersionUID = 8945415935164238908L;
   private MethodExpression oneArgListener;
   private MethodExpression noArgListener;

   public DeclarativeSystemEventListener() {
   }

   public DeclarativeSystemEventListener(MethodExpression oneArg, MethodExpression noArg) {
      this.oneArgListener = oneArg;
      this.noArgListener = noArg;
   }

   public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
      ELContext elContext = FacesContext.getCurrentInstance().getELContext();

      try {
         this.noArgListener.invoke(elContext, new Object[0]);
      } catch (IllegalArgumentException | MethodNotFoundException var4) {
         this.oneArgListener.invoke(elContext, new Object[]{event});
      }

   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         DeclarativeSystemEventListener that = (DeclarativeSystemEventListener)o;
         if (this.noArgListener != null) {
            if (!this.noArgListener.equals(that.noArgListener)) {
               return false;
            }
         } else if (that.noArgListener != null) {
            return false;
         }

         if (this.oneArgListener != null) {
            if (this.oneArgListener.equals(that.oneArgListener)) {
               return true;
            }
         } else if (that.oneArgListener == null) {
            return true;
         }

         return false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.oneArgListener != null ? this.oneArgListener.hashCode() : 0;
      result = 31 * result + (this.noArgListener != null ? this.noArgListener.hashCode() : 0);
      return result;
   }
}
