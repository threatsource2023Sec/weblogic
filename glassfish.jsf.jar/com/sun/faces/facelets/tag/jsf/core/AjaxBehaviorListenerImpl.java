package com.sun.faces.facelets.tag.jsf.core;

import java.io.Serializable;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodNotFoundException;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;

class AjaxBehaviorListenerImpl implements AjaxBehaviorListener, Serializable {
   private static final long serialVersionUID = -6056525197409773897L;
   private MethodExpression oneArgListener;
   private MethodExpression noArgListener;

   public AjaxBehaviorListenerImpl() {
   }

   public AjaxBehaviorListenerImpl(MethodExpression oneArg, MethodExpression noArg) {
      this.oneArgListener = oneArg;
      this.noArgListener = noArg;
   }

   public void processAjaxBehavior(AjaxBehaviorEvent event) throws AbortProcessingException {
      ELContext elContext = FacesContext.getCurrentInstance().getELContext();

      try {
         this.noArgListener.invoke(elContext, new Object[0]);
      } catch (IllegalArgumentException | MethodNotFoundException var4) {
         this.oneArgListener.invoke(elContext, new Object[]{event});
      }

   }
}
